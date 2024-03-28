package com.nandaliyan.mylibapi.service;

import com.nandaliyan.mylibapi.model.entity.Admin;

public interface AdminService {

    Admin create(Admin admin);

    Admin getById(String id);
    
}
