package org.exceptions.providerExceptions;

public class ProviderMappingException extends RuntimeException{
    public ProviderMappingException(String message){
        super(message);
    }

    public ProviderMappingException(String message, Throwable cause){
        super(message, cause);
    }
}
