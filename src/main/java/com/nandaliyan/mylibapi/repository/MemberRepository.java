package com.nandaliyan.mylibapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nandaliyan.mylibapi.model.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {

    @Query(value = "SELECT MAX(member_id) FROM m_member WHERE EXTRACT(YEAR FROM created_at) = ?1", nativeQuery = true)
    String findLastMemberIdByYear(int year);

    @Query(value = "SELECT * FROM m_member WHERE email = ?1", nativeQuery = true)
    Optional<Member> findByEmail(String email);
    
}
