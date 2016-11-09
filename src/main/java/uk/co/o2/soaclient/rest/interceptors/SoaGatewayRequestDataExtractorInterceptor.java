package uk.co.o2.soaclient.rest.interceptors;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.message.Message;
import org.slf4j.MDC;

public class SoaGatewayRequestDataExtractorInterceptor extends LoggingOutInterceptor {

    public static final String SOA_GW_TIMESTAMP_KEY = "SOAGatewayRequestTime";
    public static final String SOA_GW_REQUEST_METHOD = "SOAGatewayRequestMethod";

    @Override
    public void handleMessage(Message message) throws Fault {
        MDC.put(SOA_GW_TIMESTAMP_KEY,String.valueOf(System.currentTimeMillis()));
        MDC.put(SOA_GW_REQUEST_METHOD, String.valueOf(message.get(Message.HTTP_REQUEST_METHOD)));
    }
}
