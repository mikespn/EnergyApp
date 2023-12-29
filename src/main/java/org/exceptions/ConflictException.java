package org.exceptions;

public class ConflictException extends RuntimeException{

    //Used in situations where a request conflicts with the state of the server.
    public ConflictException(String message){
        super(message);
    }
}
