package com.nandaliyan.mylibapi.model.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class BookResponse {
    
    private Long id;
    private String bookCode;
    private String title;
    private BookAuthorResponse author;
    private BookPublisherResponse publisher;
    private List<BookGenreResponse> genre;
    private String year;
    private String description;
    private Integer stock;
    private Boolean isAvailable;

}
