package com.nandaliyan.mylibapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Book not available.")
public class BookNotAvailableException extends RuntimeException {

    public BookNotAvailableException() {
        
    }
    
    public BookNotAvailableException(String message) {
        super(message);
    }
    
}
