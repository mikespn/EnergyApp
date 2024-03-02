package org.energyapp.exceptions.consumerExceptions;

public class ConsumerUpdateException extends RuntimeException {

    public ConsumerUpdateException(String message){
        super(message);
    }

    public ConsumerUpdateException(String message, Throwable cause){
        super(message, cause);
    }
}
