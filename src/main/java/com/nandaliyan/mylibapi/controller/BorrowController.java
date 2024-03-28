package com.nandaliyan.mylibapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nandaliyan.mylibapi.constant.AppPath;
import com.nandaliyan.mylibapi.model.request.BorrowRequest;
import com.nandaliyan.mylibapi.model.request.ReturnRequest;
import com.nandaliyan.mylibapi.model.response.BorrowResponse;
import com.nandaliyan.mylibapi.model.response.CommonResponse;
import com.nandaliyan.mylibapi.model.response.ReturnResponse;
import com.nandaliyan.mylibapi.service.BorrowingRecordService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = AppPath.BEARER_AUTH)
public class BorrowController {

    private final BorrowingRecordService borrowingRecordService;

    @PostMapping(AppPath.BORROW_PATH)
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    public ResponseEntity<?> borrowBook(@RequestBody BorrowRequest request, Authentication authentication) {
        BorrowResponse borrowResponse = borrowingRecordService.borrowBook(request, authentication);
        CommonResponse<BorrowResponse> response = CommonResponse.<BorrowResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Book borrowed successfully.")
                .data(borrowResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping(AppPath.RETURN_PATH)
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    public ResponseEntity<?> returnBook(@RequestBody ReturnRequest request, Authentication authentication) {
        ReturnResponse returnResponse = borrowingRecordService.returnBook(request, authentication);
        CommonResponse<ReturnResponse> response = CommonResponse.<ReturnResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Book returned successfully.")
                .data(returnResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
}
