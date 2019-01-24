package com.stackroute.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalHandlerException {
    @ExceptionHandler(MuzixAlreadyExistsException.class)
    public ResponseEntity muzixAlreadyExistsException(final MuzixAlreadyExistsException e){
        return  new ResponseEntity(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MuzixNotFoundException.class)
    public ResponseEntity muzixNotFoundException(final MuzixNotFoundException e){
        return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
    }
}
