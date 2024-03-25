package com.nandaliyan.mylibapi.model.request;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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

    @NotEmpty(message = "At least one genre is required.")
    private List<String> genres;

    @NotNull
    private Integer year;
    
    private String description;

    @Positive(message = "Quantity must be greater than 0.")
    private Integer stock;

}
