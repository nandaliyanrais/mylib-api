package com.nandaliyan.mylibapi.service;

import com.nandaliyan.mylibapi.model.request.LoginRequest;
import com.nandaliyan.mylibapi.model.request.RegisterRequest;
import com.nandaliyan.mylibapi.model.response.LoginResponse;
import com.nandaliyan.mylibapi.model.response.RegisterResponse;

public interface AuthService {
    
    RegisterResponse registerAdmin(RegisterRequest request);

    RegisterResponse registerMember(RegisterRequest request);

    LoginResponse login(LoginRequest request);

}
