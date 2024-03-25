package com.nandaliyan.mylibapi.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.nandaliyan.mylibapi.constant.ERole;
import com.nandaliyan.mylibapi.model.entity.Role;
import com.nandaliyan.mylibapi.repository.RoleRepository;
import com.nandaliyan.mylibapi.service.RoleService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role getOrCreate(ERole role) {
        Optional<Role> optionalRole = roleRepository.findByName(role);

        if(optionalRole.isPresent()) {
            return optionalRole.get();
        }

        Role newRole = Role.builder()
                .name(role)
                .build();
        
		return roleRepository.saveAndFlush(newRole);
    }
    
}
