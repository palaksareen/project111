package uk.co.o2.soaclient.rest;

import org.springframework.stereotype.Service;

import uk.co.o2.DynamicProperties;

@Service
public class SoaConfig {
//	@Value("${reCaptchaServiceUrl}")
    private String reCaptchaServiceUrl=DynamicProperties.getProperty("reCaptchaServiceUrl");
    
    private String https_protocols=DynamicProperties.getProperty("httpsProtocols");
	
    private Integer soaSoapReadTimeout=DynamicProperties.getIntegerProperty("soaSoapReadTimeoutMS");;

	private Integer soaSoapConnectionTimeout=DynamicProperties.getIntegerProperty("soaSoapConnectionTimeoutMS");;

    private Integer soaRestReadTimeout=DynamicProperties.getIntegerProperty("soaRestReadTimeoutMS");;
    
    private Integer soaRestConnectionTimeout=DynamicProperties.getIntegerProperty("soaRestConnectionTimeoutMS");;
    
	public String getHttps_protocols() {
		return https_protocols;
	}


	public void setHttps_protocols(String https_protocols) {
		this.https_protocols = https_protocols;
	}


	private String subscriberServiceUrl=DynamicProperties.getProperty("subscriberServiceUrl");;
    // key store
	public
	String keyStorePassword=DynamicProperties.getProperty("soaKeyStorePassword");;
	
	String username=DynamicProperties.getProperty("soaUsername");;
	
	String password=DynamicProperties.getProperty("soaPassword");;
	
	//boolean certificateEnabled=DynamicProperties.getBooleanProperty("certificateEnabled");;
	//String soaEnableCertificate=DynamicProperties.getProperty("soaEnableCertificate");;

	// Trust store
	String soaTrustedStoreCertificateLocation=DynamicProperties.getProperty("soaTrustedStoreCertificateLocation");;

	String soaTrustedStorePassword=DynamicProperties.getProperty("soaTrustedStorePassword");;

	String soaCertificateLocation=DynamicProperties.getProperty("soaCertificateLocation");;

	String soaKeyStorePassword=DynamicProperties.getProperty("soaKeyStorePassword");;

	SoaConfig() {
    }

	  
    public Integer getSoaSoapReadTimeout() {
		return soaSoapReadTimeout;
	}


	public String getSubscriberServiceUrl() {
		return subscriberServiceUrl;
	}


	public void setSubscriberServiceUrl(String subscriberServiceUrl) {
		this.subscriberServiceUrl = subscriberServiceUrl;
	}


	public void setSoaSoapReadTimeout(Integer soaSoapReadTimeout) {
		this.soaSoapReadTimeout = soaSoapReadTimeout;
	}


	public Integer getSoaSoapConnectionTimeout() {
		return soaSoapConnectionTimeout;
	}


	public void setSoaSoapConnectionTimeout(Integer soaSoapConnectionTimeout) {
		this.soaSoapConnectionTimeout = soaSoapConnectionTimeout;
	}


	public Integer getSoaRestReadTimeout() {
		return soaRestReadTimeout;
	}


	public void setSoaRestReadTimeout(Integer soaRestReadTimeout) {
		this.soaRestReadTimeout = soaRestReadTimeout;
	}


	public Integer getSoaRestConnectionTimeout() {
		return soaRestConnectionTimeout;
	}


	public void setSoaRestConnectionTimeout(Integer soaRestConnectionTimeout) {
		this.soaRestConnectionTimeout = soaRestConnectionTimeout;
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

	
	public String getSoaKeyStorePassword() {
		return soaKeyStorePassword;
	}


	public String getSoaCertificateLocation() {
		return soaCertificateLocation;
	}


	public void setSoaCertificateLocation(String soaCertificateLocation) {
		this.soaCertificateLocation = soaCertificateLocation;
	}


	public void setSoaKeyStorePassword(String soaKeyStorePassword) {
		this.soaKeyStorePassword = soaKeyStorePassword;
	}

}
