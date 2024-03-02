package org.energyapp.exceptions;

public class ResourceNotFoundException extends RuntimeException{

    //Sends a message when specific resource (exp Consumer) cannot be found
    public ResourceNotFoundException(String message){
        super(message);
    }
}
