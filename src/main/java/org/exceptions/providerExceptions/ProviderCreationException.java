package org.exceptions.providerExceptions;

public class ProviderCreationException extends RuntimeException{
    public ProviderCreationException(String message){
        super(message);
    }

    public ProviderCreationException(String message, Throwable cause){
        super(message, cause);
    }
}
