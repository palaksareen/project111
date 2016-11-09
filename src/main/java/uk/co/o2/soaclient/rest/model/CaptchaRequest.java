package uk.co.o2.soaclient.rest.model;

public class CaptchaRequest {

    private String response;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

	public CaptchaRequest(String response) {
		super();
		this.response = response;
	}
    
    
}
