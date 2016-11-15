package uk.co.o2.soaclient.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SoaConfig {
	
	@Value("${soaKeyStoreCertificateLocation}")
	public
	String certificateLocation;
	
	@Value("${soaKeyStorePassword}")
	public
	String keyStorePassword;
	
	@Value("${soaUsername}")
	String username;
	
	@Value("${soaPassword}")
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
