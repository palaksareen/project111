package uk.co.o2.utility.exception;

public class MissingPropertyException extends Exception {
	public MissingPropertyException(String message) {
		super(message);
	}

	public	MissingPropertyException(String message, Throwable cause) {
		super(message, cause);
	}
}
