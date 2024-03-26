package com.nandaliyan.mylibapi.service;

import java.util.List;

import org.springframework.security.core.Authentication;

import com.nandaliyan.mylibapi.model.entity.BorrowingRecord;
import com.nandaliyan.mylibapi.model.request.BorrowRequest;
import com.nandaliyan.mylibapi.model.request.ReturnRequest;
import com.nandaliyan.mylibapi.model.response.BorrowResponse;
import com.nandaliyan.mylibapi.model.response.BorrowingRecordResponse;
import com.nandaliyan.mylibapi.model.response.ReturnResponse;

public interface BorrowingRecordService {
    
    BorrowingRecord getById(String id);

    BorrowResponse borrowBook(BorrowRequest request, Authentication authentication);

    ReturnResponse returnBook(ReturnRequest request, Authentication authentication);

    List<BorrowingRecordResponse> getAllWithDto();

    BorrowingRecordResponse getByIdWithDto(String id);

}
