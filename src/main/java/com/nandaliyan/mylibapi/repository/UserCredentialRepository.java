package com.nandaliyan.mylibapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nandaliyan.mylibapi.model.entity.UserCredential;

@Repository
public interface UserCredentialRepository extends JpaRepository<UserCredential, String> {
    
    Optional<UserCredential> findByEmail(String email);
    
}
