package com.nandaliyan.mylibapi.service;

import com.nandaliyan.mylibapi.constant.ERole;
import com.nandaliyan.mylibapi.model.entity.Role;

public interface RoleService {
    
    Role getOrCreate(ERole role);

}
