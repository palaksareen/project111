package uk.co.o2.log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import uk.co.o2.utility.ErrorCode;

public class SOAPLogger implements SOAPHandler<SOAPMessageContext>{
	
	public final Log appLogger = LogFactory.getLog("application_log");

	@Override
	public boolean handleMessage(SOAPMessageContext context) {
		SOAPMessage soapMessage = context.getMessage();
		boolean isOutboundMessage = (boolean) context.get(SOAPMessageContext.MESSAGE_OUTBOUND_PROPERTY);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			soapMessage.writeTo(baos);
			String message = baos.toString().replaceAll("[\r\n]+", "");
			if(isOutboundMessage){
				appLogger.info("SOAP Request is : " + message);
			
			}else{
				appLogger.info("SOAP Response is : " + message);
			}
		} catch (SOAPException | IOException e) {
			appLogger.error(ErrorCode.MSG_HANDLER_ERROR.getMessage() , e.getCause());
		}
		
		return true;
	}

	@Override
	public boolean handleFault(SOAPMessageContext context) {
		return false;
	}

	@Override
	public void close(MessageContext context) {
		// TODO Auto-generated method stub
	}

	@Override
	public Set<QName> getHeaders() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
