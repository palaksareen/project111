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
			System.setProperty("javax.net.debug","all");

			System.setProperty("javax.net.ssl.keyStore", soaConfig.getSoaKeyStoreCertificateLocation());
			System.setProperty("javax.net.ssl.keyStorePassword", soaConfig.getKeyStorePassword());


			System.setProperty("javax.net.ssl.trustStore", soaConfig.getSoaTrustedStoreCertificateLocation());
			System.setProperty("javax.net.ssl.trustStorePassword", soaConfig.getSoaTrustedStorePassword());

			System.setProperty("https.protocols", soaConfig.getHttps_protocols());
		}catch (Exception e) {
			throw new Exception();
		}
	}

	
	@Override
	public void afterPropertiesSet() throws Exception {
		setSSLProperties();
	}
}
