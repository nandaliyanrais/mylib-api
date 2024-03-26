package com.nandaliyan.mylibapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Borrowing Record not found.")
public class BorrowingRecordNotFoundException extends RuntimeException {

    public BorrowingRecordNotFoundException() {
        
    }

    public BorrowingRecordNotFoundException(String message) {
        super(message);
    }
    
}
