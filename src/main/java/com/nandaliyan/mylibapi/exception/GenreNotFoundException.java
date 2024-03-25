package com.nandaliyan.mylibapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Genre not found.")
public class GenreNotFoundException extends RuntimeException {

    public GenreNotFoundException() {

    }

    public GenreNotFoundException(String message) {
        super(message);
    }
    
}
