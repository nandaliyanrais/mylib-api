package com.nandaliyan.mylibapi.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class ListBookResponse {

    private String title;
    private String year;
    
}
