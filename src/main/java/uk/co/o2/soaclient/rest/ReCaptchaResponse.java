package uk.co.o2.soaclient.rest;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ReCaptchaResponse {
	@Override
	public String toString() {
		return "ReCaptchaResponse [success=" + success + ", errorCodes=" + errorCodes + "]";
	}
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
