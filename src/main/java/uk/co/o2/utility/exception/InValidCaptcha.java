package uk.co.o2.utility.exception;

public class InValidCaptcha extends Exception {

	private static final long serialVersionUID = 1;
	private String mpn;

	public InValidCaptcha(String message, String mpn) {
		super(message);
		this.mpn = mpn;
	}

	public String getMPN() {
		return this.mpn;
	}

}