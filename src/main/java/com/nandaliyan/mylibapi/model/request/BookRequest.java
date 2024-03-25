package com.nandaliyan.mylibapi.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class BookRequest {
    
    @NotBlank(message = "Title is required.")
    private String title;

    @NotBlank(message = "Author is required.")
    private String author;

    @NotBlank(message = "Publisher is required.")
    private String publisher;

    @NotBlank(message = "Genre is required.")
    private String genre;

    @NotNull
    private Integer year;
    
    private String description;

    @Positive(message = "Quantity must be greater than 0.")
    private Integer stock;

}
