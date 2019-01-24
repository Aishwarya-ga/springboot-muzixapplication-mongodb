/**
 * Custom Exception  for muzix not found exception created here
 */
package com.stackroute.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class MuzixNotFoundException extends Exception{

    //Message variable
    private String message;

    //Parametrized constructor
    public MuzixNotFoundException(String message) {
        super(message);
        this.message = message;
    }

    //Default Constructor
    public MuzixNotFoundException() {
    }
}
