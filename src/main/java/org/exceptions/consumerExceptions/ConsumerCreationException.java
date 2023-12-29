package org.exceptions.consumerExceptions;

public class ConsumerCreationException extends RuntimeException {

    public ConsumerCreationException (String message){
        super(message);
    }

    public ConsumerCreationException(String message, Throwable cause){
        super(message, cause);
    }
}
