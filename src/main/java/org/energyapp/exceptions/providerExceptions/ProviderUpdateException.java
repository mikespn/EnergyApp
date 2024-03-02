package org.energyapp.exceptions.providerExceptions;

public class ProviderUpdateException extends RuntimeException{
    public ProviderUpdateException(String message){
        super(message);
    }

    public ProviderUpdateException(String message, Throwable cause){
        super(message, cause);
    }
}
