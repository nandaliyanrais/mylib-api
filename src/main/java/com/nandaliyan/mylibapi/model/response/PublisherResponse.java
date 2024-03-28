package com.nandaliyan.mylibapi.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class PublisherResponse {

    private Long id;
    private String name;
    private Boolean isActive;
    
}
