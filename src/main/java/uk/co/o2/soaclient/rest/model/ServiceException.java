package uk.co.o2.soaclient.rest.model;

public class ServiceException extends RuntimeException {

    public ServiceException(String message){
        super(message);
    }

    public ServiceException(String message, Throwable cause){
        super(message, cause);

    }
}
