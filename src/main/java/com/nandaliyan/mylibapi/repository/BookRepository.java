package com.nandaliyan.mylibapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nandaliyan.mylibapi.model.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    
    
}
