package uk.co.o2.soaclient;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.o2.soaclient.rest.SoaConfig;
@Service
public class SslCertBean implements InitializingBean{


	@Autowired
	SoaConfig soaConfig;

	private void setSSLProperties() throws Exception {
		try{
			System.out.println("Storing Certificate\nKeystor :: "+soaConfig.getSoaKeyStoreCertificateLocation()+"\nTrust ::"+soaConfig.getSoaTrustedStoreCertificateLocation());
			System.setProperty("javax.net.debug","ssl");

			System.setProperty("javax.net.ssl.keyStore", soaConfig.getSoaKeyStoreCertificateLocation());
			System.setProperty("javax.net.ssl.keyStorePassword", soaConfig.getKeyStorePassword());


			System.setProperty("javax.net.ssl.trustStore", soaConfig.getSoaTrustedStoreCertificateLocation());
			System.setProperty("javax.net.ssl.trustStorePassword", soaConfig.getSoaTrustedStorePassword());

			System.setProperty("https.protocols", "TLSv1");
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
		System.out.println("Storing Certificate completed....");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		setSSLProperties();
	}
}
