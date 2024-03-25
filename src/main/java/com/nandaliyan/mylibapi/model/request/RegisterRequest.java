package com.nandaliyan.mylibapi.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String name;
    private String email;
    private String password;
    private String phone;
    private String address;
    
}
