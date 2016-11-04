package uk.co.o2.utility;

public class RecaptchaValidationBean {
	String ip;
	
	public RecaptchaValidationBean(String ip, String recaptchaCode) {
		super();
		this.ip = ip;
		this.recaptchaCode = recaptchaCode;
		this.secret="6LfN-AkUAAAAAEdR1QnSWnx_HkKWTtzDPmnF77W7";
	}
	String secret;
	String recaptchaCode;
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	public String getRecaptchaCode() {
		return recaptchaCode;
	}
	public void setRecaptchaCode(String recaptchaCode) {
		this.recaptchaCode = recaptchaCode;
	}
	
	
}
