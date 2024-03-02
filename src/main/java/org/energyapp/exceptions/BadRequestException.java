package org.energyapp.exceptions;

public class BadRequestException extends RuntimeException{

    // Used for scenarios where the request made to the server is malformed or invalid.
    public BadRequestException(String message){
        super(message);
    }
}
