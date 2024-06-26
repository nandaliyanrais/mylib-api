package com.nandaliyan.mylibapi.model.entity;

import java.util.ArrayList;
import java.util.List;

import com.nandaliyan.mylibapi.constant.DbTableSchema;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = DbTableSchema.BOOK_SCHEMA)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String bookCode;

    @Column(unique = true)
    private String title;

    @Column(unique = true)
    private String urlName;

    @ManyToOne
    private Author author;

    @ManyToOne
    private Publisher publisher;

    @ManyToMany
    @JoinTable(
        name = DbTableSchema.BOOK_GENRE_SCHEMA,
        joinColumns = @JoinColumn(name = "book_id"),
        inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    @Builder.Default
    private List<Genre> genre = new ArrayList<>();
    
    private String year;

    private String description;

    private Integer stock;

    private Boolean isAvailable;

}
