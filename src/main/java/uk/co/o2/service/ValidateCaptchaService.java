package uk.co.o2.service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import uk.co.o2.soaclient.rest.model.CaptchaRequest;
import uk.co.o2.soaclient.rest.model.CaptchaResponse;
import uk.co.o2.soaclient.rest.model.CaptchaServiceResponse;
import uk.co.o2.soaclient.rest.model.ServiceException;

/**
 * Created by cts1 on 25/11/15.
 */
@Service
public class ValidateCaptchaService {


    private static Logger logger = Logger.getLogger("errorLog");
    private static Logger errorLogger = Logger.getLogger("errorLog");

    public static final String CONTENTTYPEKEY = "Content-Type";

    public static final String APPLICATION_JSON = "application/json";

    public static final String AUTHORIZATION = "Authorization";

    public static final String BASIC = "Basic ";

    private static Map<String, String> googleErrorMap;


    static{
        googleErrorMap = new HashMap<String, String>();

        googleErrorMap.put("missing-input-secret","The secret parameter is missing");
        googleErrorMap.put("invalid-input-secret","The secret parameter is invalid or malformed");
        googleErrorMap.put("missing-input-response","The response parameter is missing");
        googleErrorMap.put("invalid-input-response","The response parameter is invalid or malformed");
    }

    @Value("${soaUsername}")
    private String username="online_puk_2457";

   @Value("${soaPassword}")
    private String password="sIoTsbdv";

    @Value("${reCaptchaServiceUrl}")
    private String posturl="https://rstapi.ref.o2.co.uk/recaptcha/api/siteverify";

    public CaptchaServiceResponse validateCaptcha(CaptchaRequest captchaRequest) {

        logger.info("Validate Captcha Service : Started");

        HttpClient client = HttpClientBuilder.create().build();

        HttpPost httpPost = new HttpPost(posturl);

        httpPost.setHeader(CONTENTTYPEKEY,APPLICATION_JSON);
        httpPost.setHeader(AUTHORIZATION, getAuthorizationHeader());
        ObjectMapper mapper = new ObjectMapper();
        StringWriter writer = new StringWriter();
        try {
            mapper.writeValue(writer, captchaRequest);
            StringEntity input = new StringEntity(writer.toString());
            httpPost.setEntity(input);
        } catch (JsonMappingException jsonMappingException) {
            errorLogger.error("Error occured while mapping Json Request");
            throw new ServiceException(jsonMappingException.getMessage());
        } catch (IOException ioException) {
            errorLogger.error("IO Exception occured while mapping Json Request"+ioException.getMessage());
        }

        CaptchaResponse captchaResponse = null;
        try {
            HttpResponse response = client.execute(httpPost);
            captchaResponse = mapper.readValue(response.getEntity().getContent(),
                    CaptchaResponse.class);
            if(response.getStatusLine().getStatusCode() == 200){

                CaptchaServiceResponse serviceResponse = new CaptchaServiceResponse();
                serviceResponse.setSuccess(captchaResponse.isSuccess());

                if(!serviceResponse.isSuccess()){
                    //serviceResponse.setMessage(googleErrorMap.get(captchaResponse.getErrorCodes()));
                    errorLogger.info("Google reCaptcha Response failed: "+captchaResponse.isSuccess());
                    errorLogger.info("Google Captcha Response Message:"+captchaResponse.getErrorCodes());
                }

                return serviceResponse;
            }
            errorLogger.error("Google Captcha SOA Service Response : Status -"+response.getStatusLine());
            errorLogger.error("Google Captcha SOA Service Response "+mapper.writeValueAsString(captchaResponse));
            throw new ServiceException("SOA ERROR "+mapper.writeValueAsString(captchaResponse));
        } catch (ClientProtocolException clientException) {
            errorLogger.error("ClientProtocolException occured while Calling Google Captcha SOA Service Request :"+clientException.getMessage());
            throw new ServiceException(clientException.getMessage());
        } catch (IOException ioException) {
            errorLogger.error("IO Exception occured while mapping Json Response : "+ioException.getMessage());
            throw new ServiceException(ioException.getMessage());
        }
    }

    private String getAuthorizationHeader(){
        String header = username+":"+ password;
        byte[] unencodedByteArray = header.getBytes();
        byte[] encodedByteArray = Base64.encodeBase64(unencodedByteArray);
        String encodedString = new String(encodedByteArray);
        return BASIC + " " + encodedString;
    }
}
