package com.nandaliyan.mylibapi.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class BookAuthorResponse {

    private Long id;
    private String name;
    
}
