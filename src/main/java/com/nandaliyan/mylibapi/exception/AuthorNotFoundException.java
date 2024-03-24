package com.nandaliyan.mylibapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Author not found.")
public class AuthorNotFoundException extends RuntimeException {

    public AuthorNotFoundException() {
        
    }

    public AuthorNotFoundException(String message) {
        super(message);
    }
    
}
