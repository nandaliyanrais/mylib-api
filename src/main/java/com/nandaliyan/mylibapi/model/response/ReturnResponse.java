package com.nandaliyan.mylibapi.model.response;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class ReturnResponse {

    private String borrowId;
    private String bookCode;
    private String title;
    private String returnedBy;
    private LocalDateTime returnedAt;
    
}
