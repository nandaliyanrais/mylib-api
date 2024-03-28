package com.nandaliyan.mylibapi.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nandaliyan.mylibapi.constant.AppPath;
import com.nandaliyan.mylibapi.constant.ERole;
import com.nandaliyan.mylibapi.model.entity.AppUser;
import com.nandaliyan.mylibapi.model.entity.Member;
import com.nandaliyan.mylibapi.model.request.MemberUpdateRequest;
import com.nandaliyan.mylibapi.model.response.CommonResponse;
import com.nandaliyan.mylibapi.model.response.CommonResponseWithPage;
import com.nandaliyan.mylibapi.model.response.MemberResponse;
import com.nandaliyan.mylibapi.model.response.PagingResponse;
import com.nandaliyan.mylibapi.service.MemberService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.MEMBER_PATH)
@SecurityRequirement(name = AppPath.BEARER_AUTH)
public class MemberController {
    
    private final MemberService memberService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAllMembers(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        Page<MemberResponse> memberResponses = memberService.getAllWithDto(page - 1, size);
        PagingResponse pagingResponse = PagingResponse.builder()
                .currentPage(page)
                .totalPage(memberResponses.getTotalPages())
                .size(size)
                .build();
        CommonResponseWithPage<Page<MemberResponse>> response = CommonResponseWithPage.<Page<MemberResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Members retrieved successfully.")
                .data(memberResponses)
                .paging(pagingResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(AppPath.GET_BY_ID)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MEMBER')")
    public ResponseEntity<?> getMemberById(@PathVariable String id, Authentication authentication) {
        String userId = ((AppUser) authentication.getPrincipal()).getId();
        ERole role = ((AppUser) authentication.getPrincipal()).getRole();
        Member member = memberService.getById(id);

        if((role.equals(ERole.ROLE_MEMBER)) && !member.getUserCredential().getId().equals(userId)) {
            CommonResponse<String> errorResponse = CommonResponse.<String>builder()
                    .statusCode(HttpStatus.UNAUTHORIZED.value())
                    .message("You are not allowed to access this resource.")
                    .build();

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }

        MemberResponse memberResponse = memberService.getByIdWithDto(id);
        CommonResponse<MemberResponse> response = CommonResponse.<MemberResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Member retrieved successfully.")
                .data(memberResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping(AppPath.UPDATE_BY_ID)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MEMBER')")
    public ResponseEntity<?> updateMemberById(@PathVariable String id, @Valid @RequestBody MemberUpdateRequest request, Authentication authentication) {
        String userId = ((AppUser) authentication.getPrincipal()).getId();
        ERole role = ((AppUser) authentication.getPrincipal()).getRole();
        Member member = memberService.getById(id);

        if((role.equals(ERole.ROLE_MEMBER)) && !member.getUserCredential().getId().equals(userId)) {
            CommonResponse<String> errorResponse = CommonResponse.<String>builder()
                    .statusCode(HttpStatus.UNAUTHORIZED.value())
                    .message("You are not allowed to access this resource.")
                    .build();

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }

        MemberResponse memberResponse = memberService.updateWithDto(id, request);
        CommonResponse<MemberResponse> response = CommonResponse.<MemberResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Member updated successfully.")
                .data(memberResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping(AppPath.DELETE_BY_ID)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteMemberById(@PathVariable String id) {
        memberService.deleteById(id);
        CommonResponse<MemberResponse> response = CommonResponse.<MemberResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Member deleted successfully.")
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
}
