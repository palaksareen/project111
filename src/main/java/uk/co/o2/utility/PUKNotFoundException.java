package uk.co.o2.utility;


public class PUKNotFoundException
extends Exception {
    private static final long serialVersionUID = 1;

    public PUKNotFoundException(String message) {
        super(message);
    }

    PUKNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}