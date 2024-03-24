package com.nandaliyan.mylibapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Publisher not found.")
public class PublisherNotFoundException extends RuntimeException {

    public PublisherNotFoundException() {
        
    }
    
    public PublisherNotFoundException(String message) {
        super(message);
    }
    
}
