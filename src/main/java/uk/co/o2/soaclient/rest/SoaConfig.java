package uk.co.o2.soaclient.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SoaConfig {
	
	@Value("${soaKeyStoreCertificateLocation}")
	String certificateLocation;
	
	@Value("${soaKeyStorePassword}")
	String keyStorePassword;
	
	@Value("${googlecaptcha.username}")
	String username;
	
	@Value("${googlecaptcha.password}")
	String password;
	
	@Value("${certificateEnabled}")
	boolean certificateEnabled;
    
	SoaConfig() {
    }

    public String getUsername() {
		return null;
    }

    public String getPassword() {
    	return null;
    }

    public String getCertificateLocation() {
    	return null;
    }

    public String getKeyStorePassword() {
    	return null;
    }

    public boolean isCertificateEnabled() {
    	return false;
    }
}
