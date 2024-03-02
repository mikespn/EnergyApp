package org.energyapp.exceptions;

import javax.persistence.criteria.CriteriaBuilder;

public class UnauthorizedAccessException extends RuntimeException{

    // Used when an action is performed by a user who doesn't have the necessary permissions.
    public UnauthorizedAccessException(String message){
        super(message);
    }
}
