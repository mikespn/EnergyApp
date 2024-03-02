package org.energyapp.exceptions.providerExceptions;

public class ProviderDeletionException extends RuntimeException{
    public ProviderDeletionException(String message){
        super(message);
    }

    public ProviderDeletionException(String message, Throwable cause){
        super(message, cause);
    }
}
