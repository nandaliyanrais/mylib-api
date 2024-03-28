package com.nandaliyan.mylibapi.model.response;

import com.nandaliyan.mylibapi.constant.ERole;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class LoginResponse {

    private String email;
    private ERole role;
    private String token;
    
}
