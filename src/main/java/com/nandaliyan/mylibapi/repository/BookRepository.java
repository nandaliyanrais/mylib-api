package com.nandaliyan.mylibapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nandaliyan.mylibapi.model.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query(value = "SELECT * FROM m_book WHERE title = ?1", nativeQuery = true)
    Optional<Book> findByTitle(String title);
    
}
