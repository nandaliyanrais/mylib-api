package com.nandaliyan.mylibapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Member not found.")
public class MemberNotFoundException extends RuntimeException {

    public MemberNotFoundException() {
        
    }
    
    public MemberNotFoundException(String message) {
        super(message);
    }
    
}
