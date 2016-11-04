package uk.co.o2.utility;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RecaptchaBean {
	boolean success;
	String challenge_ts;// timestamp of the challenge load (ISO format yyyy-MM-dd'T'HH:mm:ssZZ)
	String hostname;         // the hostname of the site where the reCAPTCHA was solved
	List<String> errorCodes;
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getChallenge_ts() {
		return challenge_ts;
	}
	public void setChallenge_ts(String challenge_ts) {
		this.challenge_ts = challenge_ts;
	}
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	public List<String> getErrorCodes() {
		return errorCodes;
	}
	public void setErrorCodes(List<String> errorCodes) {
		this.errorCodes = errorCodes;
	}
	@Override
	public String toString() {
		return "RecaptchaBean [success=" + success + ", challenge_ts=" + challenge_ts + ", hostname=" + hostname
				+ ", errorCodes=" + errorCodes + "]";
	}
		
}
