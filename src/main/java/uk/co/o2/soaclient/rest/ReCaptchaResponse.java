package uk.co.o2.soaclient.rest;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ReCaptchaResponse {
	@JsonProperty("success")
	public Boolean success;
    @JsonProperty("error-codes")
    List<String> errorCodes;
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public List<String> getErrorCodes() {
		return errorCodes;
	}
	public void setErrorCodes(List<String> errorCodes) {
		this.errorCodes = errorCodes;
	}
    
    

}
