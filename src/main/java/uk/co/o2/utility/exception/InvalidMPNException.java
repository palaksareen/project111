package uk.co.o2.utility.exception;


import uk.co.o2.utility.ErrorCode;

public class InvalidMPNException extends Exception {

	private static final long serialVersionUID = 1;
	private ErrorCode errorCode;

	public InvalidMPNException(String message, ErrorCode errorCode) {
		super(message);
		this.errorCode = errorCode;
	}
	
	public ErrorCode getErrorCode() {
		return this.errorCode;
	}
}