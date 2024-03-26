package com.nandaliyan.mylibapi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nandaliyan.mylibapi.constant.AppPath;
import com.nandaliyan.mylibapi.constant.ERole;
import com.nandaliyan.mylibapi.model.entity.AppUser;
import com.nandaliyan.mylibapi.model.entity.Member;
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
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
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MEMBER')")
    public ResponseEntity<?> getBorrowingRecordById(@PathVariable String id, Authentication authentication) {
        String userId = ((AppUser) authentication.getPrincipal()).getId();
        ERole role = ((AppUser) authentication.getPrincipal()).getRole();
        Member member = borrowingRecordService.getById(id).getMember();

        if((role.equals(ERole.ROLE_MEMBER)) && !member.getUserCredential().getId().equals(userId)) {
            CommonResponse<String> errorResponse = CommonResponse.<String>builder()
                    .statusCode(HttpStatus.UNAUTHORIZED.value())
                    .message("You are not allowed to access this resource.")
                    .build();

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }

        BorrowingRecordResponse borrowingRecordResponse = borrowingRecordService.getByIdWithDto(id);
        CommonResponse<BorrowingRecordResponse> response = CommonResponse.<BorrowingRecordResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Borrowing record retrieved successfully.")
                .data(borrowingRecordResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);        
    }

}
