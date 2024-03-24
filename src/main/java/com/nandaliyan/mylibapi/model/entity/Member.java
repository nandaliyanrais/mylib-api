package com.nandaliyan.mylibapi.model.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "m_member")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String memberId;

    private String name;

    private String email;

    private String phone;

    private String address;

    private LocalDateTime createdAt;

    private LocalDateTime expiredAt;

    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "member")
    private List<BorrowingRecord> borrowingRecord;
    
}