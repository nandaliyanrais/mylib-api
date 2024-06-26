package com.nandaliyan.mylibapi.model.entity;

import java.time.LocalDateTime;

import com.nandaliyan.mylibapi.constant.DbTableSchema;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = DbTableSchema.BORROWING_RECORD_SCHEMA)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class BorrowingRecord {
    
    @Id
    @Column(unique = true)
    private String id;

    @ManyToOne
    private Member member;

    @ManyToOne
    private Book book;

    private LocalDateTime borrowedAt;
    
    private LocalDateTime returnedAt;
    
    private String returnedBy;

    private Long fineAmount;

}
