package uk.co.o2.utility.exception;


public class GoogleServiceException
extends Exception {
    private static final long serialVersionUID = 1;

    public GoogleServiceException(String message) {
        super(message);
    }

    GoogleServiceException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public GoogleServiceException(Throwable cause) {
		super(cause);
	}
}