package com.nandaliyan.mylibapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nandaliyan.mylibapi.model.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

    @Query(value = "SELECT * FROM m_book WHERE title = ?1", nativeQuery = true)
    Optional<Book> findByTitle(String title);

    @Query(value = "SELECT * FROM m_book WHERE book_code = ?1", nativeQuery = true)
    Optional<Book> findByBookCode(String bookCode);

    @Query(value = "SELECT * FROM m_book WHERE url_name = ?1", nativeQuery = true)
    Optional<Book> findByUrlName(String urlName);

    @Query(value = "SELECT * FROM m_book WHERE is_available = true", nativeQuery = true)
    List<Book> findAllByIsAvailableTrue();
    
}
