package com.nandaliyan.mylibapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nandaliyan.mylibapi.model.entity.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {

    @Query(value = "SELECT * FROM m_genre WHERE name = ?1", nativeQuery = true)
    Optional<Genre> findByName(String name);

    @Query(value = "SELECT * FROM m_genre WHERE url_name = ?1", nativeQuery = true)
    Optional<Genre> findByUrlName(String urlName);
    
}
