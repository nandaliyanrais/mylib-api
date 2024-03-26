package com.nandaliyan.mylibapi.model.response;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class BorrowingRecordResponse {
    
    private String borrowId;
    private BorrowingRecordMemberResponse borrowedBy;
    private BorrowingRecordBookResponse book;
    private LocalDateTime borrowedAt;
    private LocalDateTime returnedAt;
    private String returnedBy;
    private Long fineAmount;

}
