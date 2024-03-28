package com.nandaliyan.mylibapi.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class BorrowingRecordBookResponse {

    private String bookCode;
    private String title;
    private String author;
    private String publisher;
    
}
