package com.nandaliyan.mylibapi.model.response;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class AuthorWithListBookResponse {

    private String name;
    List<ListBookResponse> book;
    private Integer totalBooks;
    
}
