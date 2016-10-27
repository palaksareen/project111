package uk.co.o2.soaclient;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
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
		//}
		System.out.println("\n\nCertificate storing completed...");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		setSSLProperties();
	}
}
