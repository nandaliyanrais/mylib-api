package com.nandaliyan.mylibapi.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.nandaliyan.mylibapi.model.entity.AppUser;

public interface UserCredentialService extends UserDetailsService {
    
    AppUser loadUserByUserId(String id);

}
