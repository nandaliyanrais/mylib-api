package com.nandaliyan.mylibapi.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class BorrowingRecordMemberResponse {
    
    private String memberId;
    private String name;

}
