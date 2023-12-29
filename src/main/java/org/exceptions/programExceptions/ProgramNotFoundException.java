package org.exceptions.programExceptions;

public class ProgramNotFoundException extends RuntimeException{
    public ProgramNotFoundException(String message){
        super(message);
    }

    public ProgramNotFoundException(String message, Throwable cause){
        super(message, cause);
    }
}
