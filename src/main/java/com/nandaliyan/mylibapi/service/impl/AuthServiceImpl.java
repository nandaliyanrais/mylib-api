package com.nandaliyan.mylibapi.service.impl;

import java.time.LocalDateTime;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.nandaliyan.mylibapi.constant.ERole;
import com.nandaliyan.mylibapi.model.entity.Admin;
import com.nandaliyan.mylibapi.model.entity.AppUser;
import com.nandaliyan.mylibapi.model.entity.Member;
import com.nandaliyan.mylibapi.model.entity.Role;
import com.nandaliyan.mylibapi.model.entity.UserCredential;
import com.nandaliyan.mylibapi.model.request.LoginRequest;
import com.nandaliyan.mylibapi.model.request.RegisterRequest;
import com.nandaliyan.mylibapi.model.response.LoginResponse;
import com.nandaliyan.mylibapi.model.response.RegisterResponse;
import com.nandaliyan.mylibapi.repository.UserCredentialRepository;
import com.nandaliyan.mylibapi.security.JwtUtil;
import com.nandaliyan.mylibapi.service.AdminService;
import com.nandaliyan.mylibapi.service.AuthService;
import com.nandaliyan.mylibapi.service.MemberService;
import com.nandaliyan.mylibapi.service.RoleService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserCredentialRepository userCredentialRepository;
    private final AdminService adminService;
    private final MemberService memberService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    
    @Transactional(rollbackOn = Exception.class)
    @Override
    public RegisterResponse registerAdmin(RegisterRequest request) {
        try {
            Role role = roleService.getOrCreate(ERole.ROLE_ADMIN);

            UserCredential userCredential = UserCredential.builder()
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(role)
                    .build();
            userCredentialRepository.saveAndFlush(userCredential);

            Admin admin = Admin.builder()
                    .name(request.getName())
                    .email(request.getEmail())
                    .phone(request.getPhone())
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .userCredential(userCredential)
                    .build();
            adminService.create(admin);

            return RegisterResponse.builder()
                    .email(userCredential.getEmail())
                    .role(userCredential.getRole().getName())
                    .build();
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists");
        }
    }
    
    @Transactional(rollbackOn = Exception.class)
    @Override
    public RegisterResponse registerMember(RegisterRequest request) {
        try {
            Role role = roleService.getOrCreate(ERole.ROLE_MEMBER);

            UserCredential userCredential = UserCredential.builder()
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(role)
                    .build();
            userCredentialRepository.saveAndFlush(userCredential);

            Member member = Member.builder()
                    .memberId(memberService.generateMemberId())
                    .name(request.getName())
                    .email(request.getEmail())
                    .phone(request.getPhone())
                    .address(request.getAddress())
                    .createdAt(LocalDateTime.now())
                    .expiredAt(LocalDateTime.now().plusYears(5))
                    .updatedAt(LocalDateTime.now())
                    .userCredential(userCredential)
                    .isActive(true)
                    .build();
            memberService.create(member);

            return RegisterResponse.builder()
                    .email(userCredential.getEmail())
                    .role(userCredential.getRole().getName())
                    .build();
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists");
        }
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail().toLowerCase(),
                request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        AppUser appUser = (AppUser) authentication.getPrincipal();
        String token = jwtUtil.generateToken(appUser);

        return LoginResponse.builder()
                .email(appUser.getEmail())
                .role(appUser.getRole())
                .token(token)
                .build();
    }

}
