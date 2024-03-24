package com.nandaliyan.mylibapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nandaliyan.mylibapi.model.entity.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query(value = "SELECT * FROM m_author WHERE name = ?1", nativeQuery = true)
    Optional<Author> findByName(String name);
    
}
