package org.exceptions.consumerExceptions;

import net.bytebuddy.implementation.bytecode.Throw;

public class ConsumerServiceException extends RuntimeException{
    public ConsumerServiceException(String message){
        super(message);
    }

    public ConsumerServiceException(String message, Throwable cause){
        super(message, cause);
    }
}
