package org.exceptions.programEnrollmentException;

public class ProgramEnrollmentException extends RuntimeException{

    public ProgramEnrollmentException(String message){
        super(message);
    }

    public ProgramEnrollmentException(String message, Throwable cause){
        super(message, cause);
    }
}
