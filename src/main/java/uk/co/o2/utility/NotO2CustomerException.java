package uk.co.o2.utility;


public class NotO2CustomerException
extends Exception {
    private static final long serialVersionUID = 1;

    public NotO2CustomerException(String message) {
        super(message);
    }

    NotO2CustomerException(String message, Throwable cause) {
        super(message, cause);
    }
}