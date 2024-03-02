package org.energyapp.exceptions.consumerExceptions;

public class ConsumerDeletionException extends RuntimeException{
    public ConsumerDeletionException(String message){
        super(message);
    }

    public ConsumerDeletionException(String message, Throwable cause){
        super(message, cause);
    }
}
