package org.exceptions.counterExceptions;

public class CounterNotFoundException extends RuntimeException{
    public CounterNotFoundException(String message){
        super(message);
    }


    public CounterNotFoundException(String message, Throwable cause){
        super(message, cause);
    }
}
