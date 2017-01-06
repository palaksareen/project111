package uk.co.o2.soaclient;

import java.net.URL;
import java.util.Arrays;
import java.util.UUID;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uk.co.o2.DynamicProperties;
import uk.co.o2.log.SOAPLogger;
import uk.co.o2.service.SoaService;
import uk.co.o2.soa.subscriberdata_2.SubscriberProfileType;
import uk.co.o2.soa.subscriberservice_2.GetSubscriberProfileFault;
import uk.co.o2.soa.subscriberservice_2.SubscriberPort;
import uk.co.o2.soa.subscriberservice_2.SubscriberService;
import uk.co.o2.soaclient.rest.SoaConfig;
import uk.co.o2.utility.ErrorCode;
import uk.co.o2.utility.exception.NotO2CustomerException;
import uk.co.o2.utility.exception.PUKNotFoundException;
import uk.co.o2.utility.exception.SOAException;

import com.codahale.metrics.annotation.Timed;
import com.sun.xml.ws.api.message.Headers;
import com.sun.xml.ws.client.BindingProviderProperties;
import com.sun.xml.ws.developer.WSBindingProvider;

@Component
public class SoaServiceImpl implements SoaService {

	private static final String WSDL_LOCATION = "../../../../../wsdl/subscriberservice_2_0.wsdl";

	private final Log log = LogFactory.getLog("application_log");

	Cache cache;
	@Autowired
	SoaConfig soaConfig;
	
	private SubscriberPort port;
	
	public SoaServiceImpl() {
		
		CacheManager cm = CacheManager.getInstance();
		this.cache = cm.getCache("pukCache");
		System.setProperty("com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump", "true");
		System.setProperty("com.sun.xml.internal.ws.transport.http.client.HttpTransportPipe.dump", "true");
		System.setProperty("com.sun.xml.ws.transport.http.HttpAdapter.dump", "true");
		System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dump", "true");

		URL baseUrl = SubscriberService.class.getResource(WSDL_LOCATION);
		SubscriberService ss = new SubscriberService(baseUrl, new QName(
				"http://soa.o2.co.uk/subscriberservice_2", "SubscriberService"));
		port = ss.getSubscriberPort();
		BindingProvider bindingProvider = (BindingProvider) port;
		java.util.List<Handler> handlers = bindingProvider.getBinding().getHandlerChain();
		handlers.add(new SOAPLogger());
		bindingProvider.getBinding().setHandlerChain(handlers);
	}
	
	@Timed
	public String getPukWithId(String mpn,String soaTranId)throws PUKNotFoundException,NotO2CustomerException, SOAException{
		String puk =null;
		try{
			setHeaders(port,soaTranId);
			
			log.info("Calling SOA endpoint " + soaConfig.getSubscriberServiceUrl() + " for MPN : "+ mpn);
			Long soaRequestTime = System.currentTimeMillis();
			
			SubscriberProfileType subscriberProfile = port.getSubscriberProfile(mpn);
			
			Long soaOutputTime = System.currentTimeMillis();
			log.info("SOA call Completed..");
			
			String responseTime = String.valueOf(soaOutputTime - soaRequestTime);
			log.info("SOA response Time: " + responseTime);
			
			if(subscriberProfile.getOperator().equals("nonO2")){
				throw new NotO2CustomerException(ErrorCode.NOTO2CUSTOMER.getMessage());
			}
			puk = subscriberProfile.getPuk();
			if(puk == null || puk.isEmpty()){
				throw new PUKNotFoundException(ErrorCode.PUKNOTFOUND.getMessage());
			}
		}catch (GetSubscriberProfileFault | SOAPException e) {
			throw new SOAException(e.getCause());
		}
		return puk;
	}

	//@Cacheable(cacheNames="pukCache")
	public String getPuk(String mpn) throws PUKNotFoundException,NotO2CustomerException, SOAException{
		String puk="";
		
		if(!DynamicProperties.getBooleanProperty("cacheEnabled"))
			return getPukWithId(mpn,UUID.randomUUID().toString()+":"+"puk");
		
		if(!cache.isKeyInCache(mpn)){
			log.debug("Cache missed for "+mpn+" reading from soa call");
			puk = getPukWithId(mpn,UUID.randomUUID().toString()+":"+"puk");
			cache.put(new Element(mpn, puk));
		}else{
			log.debug("Cache entry found for "+mpn+" reading from cache");
			puk= (cache.get(mpn) == null ? null : cache.get(mpn).getObjectValue().toString());
		}
		return puk;
	}

	private void setHeaders(SubscriberPort port,String soaTranId) throws SOAPException {
		WSBindingProvider provider = (WSBindingProvider)port;
		provider.getRequestContext().put(BindingProviderProperties.CONNECT_TIMEOUT, soaConfig.getSoaSoapConnectionTimeout() * 1000);
		provider.getRequestContext().put(BindingProviderProperties.REQUEST_TIMEOUT, soaConfig.getSoaSoapReadTimeout()* 1000);
		provider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, soaConfig.getSubscriberServiceUrl());
		SOAPElement security,soaTransactionId=null;
		soaTransactionId = getSoaTransationIDHeader(soaTranId);
		security = getSecurityHeader();
		provider.setOutboundHeaders(Arrays.asList(Headers.create(soaTransactionId),Headers.create(security)));
	}

	public SOAPElement getSecurityHeader() throws SOAPException {
		
		SOAPElement security;
		security = SOAPFactory.newInstance().createElement("Security", "wsse", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
		SOAPElement usernameToken = security.addChildElement("UsernameToken", "wsse", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
		usernameToken.addAttribute(new QName("xmlns:wsu"), "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
		SOAPElement usernameElement = usernameToken.addChildElement("Username", "wsse", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
		usernameElement.addTextNode(soaConfig.getUsername());
		
		SOAPElement passwordElement = usernameToken.addChildElement("Password", "wsse", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
		passwordElement.setAttribute("Type", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText");
		passwordElement.addTextNode(soaConfig.getPassword());
		return security;
	}

	public SOAPElement getSoaTransationIDHeader(String soaTranId) throws SOAPException {
		SOAPElement soaTransactionId;
		soaTransactionId = SOAPFactory.newInstance().createElement("SOAConsumerTransactionID", "cor", "http://soa.o2.co.uk/coredata_1.xsd");
		soaTransactionId.addTextNode(soaTranId);
		return soaTransactionId;
	}
}
