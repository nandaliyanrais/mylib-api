package com.nandaliyan.mylibapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nandaliyan.mylibapi.model.entity.Publisher;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {
    
    @Query(value = "SELECT * FROM m_publisher WHERE name = ?1", nativeQuery = true)
    Optional<Publisher> findByName(String name);

    @Query(value = "SELECT * FROM m_publisher WHERE url_name = ?1", nativeQuery = true)
    Optional<Publisher> findByUrlName(String urlName);
    
}
