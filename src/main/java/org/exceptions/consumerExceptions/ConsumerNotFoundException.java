package org.exceptions.consumerExceptions;

public class ConsumerNotFoundException extends RuntimeException{

    public ConsumerNotFoundException(String message){
        super(message);
    }

    public ConsumerNotFoundException(String message, Throwable cause){
        super(message, cause);
    }
}
