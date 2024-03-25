package com.nandaliyan.mylibapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nandaliyan.mylibapi.model.request.LoginRequest;
import com.nandaliyan.mylibapi.model.request.RegisterRequest;
import com.nandaliyan.mylibapi.model.response.CommonResponse;
import com.nandaliyan.mylibapi.model.response.LoginResponse;
import com.nandaliyan.mylibapi.model.response.RegisterResponse;
import com.nandaliyan.mylibapi.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/admin/register")
    public ResponseEntity<?> registerAdmin(@Valid @RequestBody RegisterRequest request) {
        RegisterResponse registerResponse = authService.registerAdmin(request);
        CommonResponse<RegisterResponse> response = CommonResponse.<RegisterResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Admin created successfully.")
                .data(registerResponse)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerMember(@Valid @RequestBody RegisterRequest request) {
        RegisterResponse registerResponse = authService.registerMember(request);
        CommonResponse<RegisterResponse> response = CommonResponse.<RegisterResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Member created successfully.")
                .data(registerResponse)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);        
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse loginResponse = authService.login(request);
        CommonResponse<LoginResponse> response = CommonResponse.<LoginResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Login successful.")
                .data(loginResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
}
