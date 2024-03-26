package com.nandaliyan.mylibapi.model.response;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class BorrowResponse {

    private String id;
    private String bookCode;
    private String title;
    private String author;
    private String borrowedBy;
    private LocalDateTime borrowedAt;
    private LocalDateTime returnBefore;
    
}
