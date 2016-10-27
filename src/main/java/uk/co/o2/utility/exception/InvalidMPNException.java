package uk.co.o2.utility.exception;

import java.util.List;

public class InvalidMPNException extends Exception {

	private static final long serialVersionUID = 1;
	private String mpn;
	private List errorList;

	public InvalidMPNException(String message, String mpn, List errorList) {
		super(message);
		this.mpn = mpn;
		this.errorList = errorList;
	}

	public String getMPN() {
		return this.mpn;
	}

	public List getErrorList() {
		return this.errorList;
	}
}