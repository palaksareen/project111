package uk.co.o2.utility.exception;

public class SOAException extends Exception {
	private static final long serialVersionUID = 1;

    public SOAException(String message) {
        super(message);
    }

    SOAException(String message, Throwable cause) {
        super(message, cause);
    }

	public SOAException(Throwable cause) {
		super(cause);
	}
}
