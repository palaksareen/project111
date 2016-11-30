package uk.co.o2.soaclient.rest.interceptors;

import java.util.List;
import java.util.Map;

import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class SoaGatewayLoggingInterceptor extends LoggingInInterceptor {

	private static final Logger SOAGW_LOG = LoggerFactory.getLogger("application_log");
	public static final String SOATransactionIDKey = "SOATransactionID";

	@Override
	public void handleMessage(Message message) throws Fault {

		Map<String, List<String>> headers = CastUtils.cast((Map)message.get(Message.PROTOCOL_HEADERS));
		String transactionId = "";
		if(headers != null && headers.get("SOATransactionID") !=null)
			transactionId =headers.get("SOATransactionID").get(0);
		String requestedURI = (String) message.getExchange().get(Message.REQUEST_URI);
		String responseTime = String.valueOf( System.currentTimeMillis() - Long.parseLong(MDC.get(SoaGatewayRequestDataExtractorInterceptor.SOA_GW_TIMESTAMP_KEY)));
		String requestMethod = MDC.get(SoaGatewayRequestDataExtractorInterceptor.SOA_GW_REQUEST_METHOD);
		String responsStatus = String.valueOf( message.get(Message.RESPONSE_CODE));

		SOAGW_LOG.info(transactionId+"\t"+requestedURI+"\t"+requestMethod+"\t"+responsStatus +"\t"+responseTime);
	}
}
