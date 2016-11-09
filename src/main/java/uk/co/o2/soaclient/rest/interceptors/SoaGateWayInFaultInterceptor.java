package uk.co.o2.soaclient.rest.interceptors;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;


public class SoaGateWayInFaultInterceptor extends LoggingInInterceptor{

	private static final Logger CONSUMER_LOG = LoggerFactory.getLogger("soa-rest");

	@Override
	public
	void handleMessage(Message message) throws Fault {

		//String transactionId = message.get(Message.PROTOCOL_HEADERS).  getAt("SOATransactionID").head();
		String requestedURI = (String) message.getExchange().get(Message.REQUEST_URI);
		String responseTime = String.valueOf( System.currentTimeMillis() - Long.parseLong(MDC.get(SoaGatewayRequestDataExtractorInterceptor.SOA_GW_TIMESTAMP_KEY)));
		String requestMethod = MDC.get(SoaGatewayRequestDataExtractorInterceptor.SOA_GW_REQUEST_METHOD);
		String responsStatus = (String) message.get(Message.RESPONSE_CODE);

        CONSUMER_LOG.error("${requestedURI} ${requestMethod} ${responsStatus} ${transactionId} ${responseTime}");
	}
}