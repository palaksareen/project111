package uk.co.o2.soaclient.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashMap;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;

import org.apache.commons.codec.binary.Base64;
import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.jaxrs.client.ClientConfiguration;
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import uk.co.o2.soaclient.rest.interceptors.SoaGateWayInFaultInterceptor;
import uk.co.o2.soaclient.rest.interceptors.SoaGatewayLoggingInterceptor;
import uk.co.o2.soaclient.rest.interceptors.SoaGatewayRequestDataExtractorInterceptor;

@Service
public class SoaRestResourceClientFactory implements ResourceClientFactory  {
	@Value("${soa.readTimeoutMS}")
    private Integer readTimeout;
    
    @Value("${soa.connectionTimeoutMS}")
    private Integer connectionTimeout;
    
    
    private final JacksonJsonProvider jacksonJsonProvider;
    
	@Value("${reCaptchaServiceUrl}")
    private String reCaptchaServiceUrl;
	
	@Autowired
    private SoaConfig soaConfig;

    public SoaRestResourceClientFactory() {
    	jacksonJsonProvider = new JacksonJsonProvider();
    	System.out.println("\n\n\n\n\nSoaRestResourceClientFactory-----------------------------");
    	
    	System.out.println("\n ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^"+this);

    }

    
    @Override
    public CaptchaValidationResource createCaptchaValidationResource() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
    	//headers are null
        return createResource(this.reCaptchaServiceUrl, CaptchaValidationResource.class,null);
    }

    private <T> T createResource(String baseUrl, Class<T> resourceClass, HashMap headers) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
    	
        T proxy = JAXRSClientFactory.create(baseUrl, resourceClass, Arrays.asList(jacksonJsonProvider), false);
        org.apache.cxf.jaxrs.client.Client client = WebClient.client(proxy);
        WebClient httpClient = WebClient.fromClient(client);
        addHeaders(httpClient, headers);
        setTimeouts(httpClient);
        setInterceptors(httpClient);

     /* Guess Its not needed  
      * if(soaConfig.certificateEnabled) {
            configureSSL(httpClient);
        }
*/
        return JAXRSClientFactory.fromClient(httpClient, resourceClass, true);
    }

    private void configureSSL(WebClient client) {
        String certificateLocation = soaConfig.certificateLocation;
        String certificatePassword = soaConfig.keyStorePassword;

        KeyStore keyStore;
		try {
			keyStore = KeyStore.getInstance("PKCS12");
		
        keyStore.load(readCertificate(certificateLocation), certificatePassword.toCharArray());
        KeyManagerFactory  factory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        factory.init(keyStore, certificatePassword.toCharArray());
        KeyManager[] keyManagers = factory.getKeyManagers();
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(keyManagers, null, new SecureRandom());

        TLSClientParameters tlsClientParameters = new TLSClientParameters();
        tlsClientParameters.setSSLSocketFactory(sslContext.getSocketFactory());
        //tlsClientParameters.SSLSocketFactory = sslContext.getSocketFactory();

        ClientConfiguration clientConfiguration = WebClient.getConfig(client);
        HTTPConduit httpConduit = clientConfiguration.getHttpConduit();
        httpConduit.setTlsClientParameters(tlsClientParameters);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    private InputStream readCertificate(String certificateLocation) throws FileNotFoundException {
        File certificateFile = new File(certificateLocation);
        if(certificateFile.exists()) {
            return new FileInputStream(certificateFile);
        }else {
            return getClass().getClassLoader().getResourceAsStream(certificateLocation);
        }
    }

    private void setInterceptors(WebClient httpClient) {
        ClientConfiguration clientConfiguration = WebClient.getConfig(httpClient);
        clientConfiguration.getInInterceptors().add(new SoaGatewayLoggingInterceptor());
        clientConfiguration.getOutInterceptors().add(new SoaGatewayRequestDataExtractorInterceptor());
        clientConfiguration.getInFaultInterceptors().add(new SoaGateWayInFaultInterceptor());
    }

    private void setTimeouts(WebClient client) {
        HTTPClientPolicy policy = new HTTPClientPolicy();
        policy.setConnectionTimeout(this.connectionTimeout);
        policy.setReceiveTimeout(this.readTimeout);
        policy.setConnection(org.apache.cxf.transports.http.configuration.ConnectionType.CLOSE);

        ClientConfiguration config = WebClient.getConfig(client);
        HTTPConduit httpConduit = config.getHttpConduit();
        httpConduit.setClient(policy);
    }

    private void addHeaders(WebClient client, HashMap headers) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
    	String  soaUsername = soaConfig.username;
    	String  soaPassword =soaConfig.password;//decryptPassword(soaConfig.password).toString();
        client.header("Authorization" , "Basic " + new String(Base64.encodeBase64((soaUsername+":"+soaPassword).getBytes()), Charset.forName("US-ASCII")));
        //headers?.each {key, value -> client.header("${key}","${value}")}
        if(headers != null)
        	headers.forEach((k,v) -> client.header(String.valueOf(k),v));

       
    }

    private byte[] decryptPassword(String encryptedPassword) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
    	byte[] keyBytes = {-5, -6, 68, 78, -24, -118, 20, 101, 60, -75, 28, 67, -62, 96, 22, 120};
        SecretKeySpec secretKey = new SecretKeySpec(keyBytes,"AES");
         Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return cipher.doFinal(Base64.decodeBase64(encryptedPassword));
    }
}
