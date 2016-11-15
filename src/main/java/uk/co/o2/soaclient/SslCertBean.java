package uk.co.o2.soaclient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;

import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.jaxrs.client.ClientConfiguration;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import uk.co.o2.soaclient.rest.SoaConfig;
@Service
public class SslCertBean implements InitializingBean{

	@Value("${soaEnableCertificate}")
	String soaEnableCertificate;

	@Value("${soaTrustedStoreCertificateLocation}")
	String soaTrustedStoreCertificateLocation;

	@Value("${soaTrustedStorePassword}")
	String soaTrustedStorePassword;

	
	@Value("${soaKeyStoreCertificateLocation}")
	String soaKeyStoreCertificateLocation;

	@Value("${soaKeyStorePassword}")
	String soaKeyStorePassword;

	@Autowired
	SoaConfig soaConfig;

    private InputStream readCertificate(String certificateLocation) throws FileNotFoundException {
        File certificateFile = new File(certificateLocation);
        if(certificateFile.exists()) {
            return new FileInputStream(certificateFile);
        }else {
            return getClass().getClassLoader().getResourceAsStream(certificateLocation);
        }
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

	private void setSSLProperties() throws Exception {
		System.out.println("\n\nCertificate storing started..."	);
		boolean enableCertificate =  Boolean.getBoolean(soaEnableCertificate);//soaConfig.isCertificateEnabled();
		System.out.println("Certficate ::"+enableCertificate);
		//if (enableCertificate) {
			
			System.setProperty("javax.net.ssl.keyStore", soaTrustedStoreCertificateLocation);
			System.setProperty("javax.net.ssl.keyStorePassword", soaKeyStorePassword);
			
			System.setProperty("javax.net.debug","ssl:handshake");
			System.setProperty("javax.net.ssl.trustStore", soaKeyStoreCertificateLocation);
			System.setProperty("javax.net.ssl.trustStorePassword", soaKeyStorePassword);
			
			System.setProperty("https.protocols", "TLSv1");
		//}
		System.out.println("\n\nCertificate storing completed...");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		setSSLProperties();
	}
}
