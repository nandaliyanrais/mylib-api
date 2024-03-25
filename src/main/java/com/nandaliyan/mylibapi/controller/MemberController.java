package com.nandaliyan.mylibapi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nandaliyan.mylibapi.model.request.MemberUpdateRequest;
import com.nandaliyan.mylibapi.model.response.CommonResponse;
import com.nandaliyan.mylibapi.model.response.MemberResponse;
import com.nandaliyan.mylibapi.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    
    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<?> getAllMembers() {
        List<MemberResponse> memberResponses = memberService.getAllWithDto();
        CommonResponse<List<MemberResponse>> response = CommonResponse.<List<MemberResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Members retrieved successfully.")
                .data(memberResponses)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMemberById(@PathVariable String id) {
        MemberResponse memberResponse = memberService.getByIdWithDto(id);
        CommonResponse<MemberResponse> response = CommonResponse.<MemberResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Member retrieved successfully.")
                .data(memberResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMemberById(@PathVariable String id, @Valid @RequestBody MemberUpdateRequest request) {
        MemberResponse memberResponse = memberService.updateWithDto(id, request);
        CommonResponse<MemberResponse> response = CommonResponse.<MemberResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Member updated successfully.")
                .data(memberResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMemberById(@PathVariable String id) {
        memberService.deleteById(id);
        CommonResponse<MemberResponse> response = CommonResponse.<MemberResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Member deleted successfully.")
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
}
