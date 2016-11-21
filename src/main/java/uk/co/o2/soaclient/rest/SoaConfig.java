package uk.co.o2.soaclient.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SoaConfig {
	@Value("${reCaptchaServiceUrl}")
    private String reCaptchaServiceUrl;
	
	@Value("${soa.readTimeoutMS}")
    private Integer readTimeout;
    
    @Value("${soa.connectionTimeoutMS}")
    private Integer connectionTimeout;

    
	
    // key store
	@Value("${soaKeyStorePassword}")
	public
	String keyStorePassword;
	
	@Value("${soaUsername}")
	String username;
	
	@Value("${soaPassword}")
	String password;
	
	@Value("${certificateEnabled}")
	boolean certificateEnabled;
    
	
	@Value("${soaEnableCertificate}")
	String soaEnableCertificate;

	// Trust store
	@Value("${soaTrustedStoreCertificateLocation}")
	String soaTrustedStoreCertificateLocation;

	@Value("${soaTrustedStorePassword}")
	String soaTrustedStorePassword;

	@Value("${soaKeyStoreCertificateLocation}")
	String soaKeyStoreCertificateLocation;

	@Value("${soaKeyStorePassword}")
	String soaKeyStorePassword;

	SoaConfig() {
    }


	public Integer getReadTimeout() {
		return readTimeout;
	}

	public void setReadTimeout(Integer readTimeout) {
		this.readTimeout = readTimeout;
	}

	public Integer getConnectionTimeout() {
		return connectionTimeout;
	}

	public void setConnectionTimeout(Integer connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	public String getReCaptchaServiceUrl() {
		return reCaptchaServiceUrl;
	}

	public void setReCaptchaServiceUrl(String reCaptchaServiceUrl) {
		this.reCaptchaServiceUrl = reCaptchaServiceUrl;
	}


	public String getKeyStorePassword() {
		return keyStorePassword;
	}


	public void setKeyStorePassword(String keyStorePassword) {
		this.keyStorePassword = keyStorePassword;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public boolean isCertificateEnabled() {
		return certificateEnabled;
	}


	public void setCertificateEnabled(boolean certificateEnabled) {
		this.certificateEnabled = certificateEnabled;
	}


	public String getSoaEnableCertificate() {
		return soaEnableCertificate;
	}


	public void setSoaEnableCertificate(String soaEnableCertificate) {
		this.soaEnableCertificate = soaEnableCertificate;
	}


	public String getSoaTrustedStoreCertificateLocation() {
		return soaTrustedStoreCertificateLocation;
	}


	public void setSoaTrustedStoreCertificateLocation(String soaTrustedStoreCertificateLocation) {
		this.soaTrustedStoreCertificateLocation = soaTrustedStoreCertificateLocation;
	}


	public String getSoaTrustedStorePassword() {
		return soaTrustedStorePassword;
	}


	public void setSoaTrustedStorePassword(String soaTrustedStorePassword) {
		this.soaTrustedStorePassword = soaTrustedStorePassword;
	}


	public String getSoaKeyStoreCertificateLocation() {
		return soaKeyStoreCertificateLocation;
	}


	public void setSoaKeyStoreCertificateLocation(String soaKeyStoreCertificateLocation) {
		this.soaKeyStoreCertificateLocation = soaKeyStoreCertificateLocation;
	}


	public String getSoaKeyStorePassword() {
		return soaKeyStorePassword;
	}


	public void setSoaKeyStorePassword(String soaKeyStorePassword) {
		this.soaKeyStorePassword = soaKeyStorePassword;
	}

}
