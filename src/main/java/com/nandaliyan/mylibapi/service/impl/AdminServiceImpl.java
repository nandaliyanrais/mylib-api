package com.nandaliyan.mylibapi.service.impl;

import org.springframework.stereotype.Service;

import com.nandaliyan.mylibapi.exception.AdminNotFoundException;
import com.nandaliyan.mylibapi.model.entity.Admin;
import com.nandaliyan.mylibapi.repository.AdminRepository;
import com.nandaliyan.mylibapi.service.AdminService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;

    @Override
    public Admin create(Admin admin) {
        return adminRepository.save(admin);
    }

    @Override
    public Admin getById(String id) {
        return adminRepository.findById(id).orElseThrow(() -> new AdminNotFoundException());
    }

}
