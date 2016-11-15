package uk.co.o2.utility;

public class RecaptchaValidationBean {
	String ip;
	
	public RecaptchaValidationBean(String ip, String recaptchaCode) {
		super();
		this.ip = ip;
		this.recaptchaCode = recaptchaCode;
	}
	String recaptchaCode;
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getRecaptchaCode() {
		return recaptchaCode;
	}
	public void setRecaptchaCode(String recaptchaCode) {
		this.recaptchaCode = recaptchaCode;
	}
	
	
}
