package uk.co.o2.soaclient.rest;

import org.springframework.stereotype.Service;

import uk.co.o2.DynamicProperties;

@Service
public class SoaConfig {
//	@Value("${reCaptchaServiceUrl}")
    private String reCaptchaServiceUrl=DynamicProperties.getProperty("reCaptchaServiceUrl");
	
    private Integer readTimeout=DynamicProperties.getIntegerProperty("soa.readTimeoutMS");;
    
    private Integer connectionTimeout=DynamicProperties.getIntegerProperty("soa.connectionTimeoutMS");;

    
	private String serviceEndPoint=DynamicProperties.getProperty("service_end_point");;
    // key store
	public
	String keyStorePassword=DynamicProperties.getProperty("soaKeyStorePassword");;
	
	String username=DynamicProperties.getProperty("soaUsername");;
	
	String password=DynamicProperties.getProperty("soaPassword");;
	
	boolean certificateEnabled=DynamicProperties.getBooleanProperty("certificateEnabled");;
    
	
	String soaEnableCertificate=DynamicProperties.getProperty("soaEnableCertificate");;

	// Trust store
	String soaTrustedStoreCertificateLocation=DynamicProperties.getProperty("soaTrustedStoreCertificateLocation");;

	String soaTrustedStorePassword=DynamicProperties.getProperty("soaTrustedStorePassword");;

	String soaKeyStoreCertificateLocation=DynamicProperties.getProperty("soaKeyStoreCertificateLocation");;

	String soaKeyStorePassword=DynamicProperties.getProperty("soaKeyStorePassword");;

	SoaConfig() {
    }


	public String getServiceEndPoint() {
		return serviceEndPoint;
	}


	public void setServiceEndPoint(String serviceEndPoint) {
		this.serviceEndPoint = serviceEndPoint;
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
