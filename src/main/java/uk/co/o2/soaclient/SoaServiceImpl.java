package uk.co.o2.soaclient;

import java.net.URL;
import java.util.Arrays;
import java.util.UUID;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.ws.BindingProvider;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.sun.xml.ws.api.message.Headers;
import com.sun.xml.ws.developer.WSBindingProvider;

import uk.co.o2.soa.subscriberdata_2.SubscriberProfileType;
import uk.co.o2.soa.subscriberservice_2.GetSubscriberProfileFault;
import uk.co.o2.soa.subscriberservice_2.SubscriberPort;
import uk.co.o2.soa.subscriberservice_2.SubscriberService;
import uk.co.o2.utility.NotO2CustomerException;
import uk.co.o2.utility.PUKNotFoundException;
import uk.co.o2.utility.SOAException;

@Component
public class SoaService {

	private final Log log = LogFactory.getLog("application_log");

	@Value("${soaUsername}")
	String soaUsername;

	@Value("${soaPassword}")
	String soaPassword;


	@Value("${service_end_point}")
	String service_end_point;
	public SoaService() {
		System.setProperty("com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump", "true");
		System.setProperty("com.sun.xml.internal.ws.transport.http.client.HttpTransportPipe.dump", "true");
		System.setProperty("com.sun.xml.ws.transport.http.HttpAdapter.dump", "true");
		System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dump", "true");
	}

	public String getPukWithId(String mpn,String soaTranId)throws PUKNotFoundException,NotO2CustomerException, SOAException{
		String puk =null;
		URL baseUrl = SubscriberService.class.getResource("../../../../../wsdl/subscriberservice_2_0.wsdl");
		SubscriberService ss=new SubscriberService(baseUrl,
				new QName("http://soa.o2.co.uk/subscriberservice_2","SubscriberService"));
		try{
			SubscriberPort port = ss.getSubscriberPort();
			setHeaders(port,soaTranId);
			SubscriberProfileType subscriberProfile = port.getSubscriberProfile(mpn);

			if(subscriberProfile.getOperator().equals("nonO2")){
				throw new NotO2CustomerException("Not a O2 Customer");
			}
			System.out.println("------------");
			log.debug(soaTranId+ " Calling soa service for "+mpn);
			puk = subscriberProfile.getPuk();
			if(puk == null || puk.isEmpty()){
				throw new PUKNotFoundException("Sorry We are unable to find the PUK, Please try later");
			}
		}catch (SOAPException | GetSubscriberProfileFault e) {
			throw new SOAException(e.getMessage());
		}catch (Exception e) {
			throw new SOAException(e.getMessage());
		}
		return puk;
	}


	public String getPuk(String mpn) throws PUKNotFoundException,NotO2CustomerException, SOAException{
		return getPukWithId(mpn,UUID.randomUUID().toString()+":"+"puk");
	}

	private void setHeaders(SubscriberPort port,String soaTranId) throws SOAPException {
		WSBindingProvider provider = (WSBindingProvider)port;

		provider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, service_end_point);

		SOAPElement security,soaTransactionId=null;

		soaTransactionId = SOAPFactory.newInstance().createElement("SOAConsumerTransactionID", "cor", "http://soa.o2.co.uk/coredata_1.xsd");
		soaTransactionId.addTextNode(soaTranId);

		security = SOAPFactory.newInstance().createElement("Security", "wsse", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
		SOAPElement usernameToken = security.addChildElement("UsernameToken", "wsse", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
		usernameToken.addAttribute(new QName("xmlns:wsu"), "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
		SOAPElement usernameElement = usernameToken.addChildElement("Username", "wsse", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
		usernameElement.addTextNode(soaUsername);
		SOAPElement passwordElement = usernameToken.addChildElement("Password", "wsse", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
		passwordElement.setAttribute("Type", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText");
		passwordElement.addTextNode(soaPassword);
		provider.setOutboundHeaders(Arrays.asList(Headers.create(soaTransactionId),Headers.create(security)));
	}
}
