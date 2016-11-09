package uk.co.o2.soaclient.rest.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class CaptchaResponse {


    private boolean success;

    @JsonIgnore
    @JsonProperty("error-codes")
    private String errorCodes;

    @JsonIgnore
    private SOAError error;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorCodes() {
        return errorCodes;
    }

    public void setErrorCodes(String errorCodes) {
        this.errorCodes = errorCodes;
    }

    public SOAError getError() {
        return error;
    }

    public void setError(SOAError error) {
        this.error = error;
    }
}
