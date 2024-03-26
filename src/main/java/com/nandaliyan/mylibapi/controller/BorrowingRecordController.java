package com.nandaliyan.mylibapi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nandaliyan.mylibapi.constant.AppPath;
import com.nandaliyan.mylibapi.model.response.BorrowingRecordResponse;
import com.nandaliyan.mylibapi.model.response.CommonResponse;
import com.nandaliyan.mylibapi.service.BorrowingRecordService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.BORROWING_RECORD_PATH)
public class BorrowingRecordController {
    
    private final BorrowingRecordService borrowingRecordService;

    @GetMapping
    public ResponseEntity<?> getAllBorrowingRecords() {
        List<BorrowingRecordResponse> borrowingRecordResponses = borrowingRecordService.getAllWithDto();
        CommonResponse<List<BorrowingRecordResponse>> response = CommonResponse.<List<BorrowingRecordResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Borrowing records retrieved successfully.")
                .data(borrowingRecordResponses)
                .build();
        
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(AppPath.GET_BY_ID)
    public ResponseEntity<?> getBorrowingRecordById(@PathVariable String id) {
        BorrowingRecordResponse borrowingRecordResponse = borrowingRecordService.getByIdWithDto(id);
        CommonResponse<BorrowingRecordResponse> response = CommonResponse.<BorrowingRecordResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Borrowing record retrieved successfully.")
                .data(borrowingRecordResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);        
    }

}
