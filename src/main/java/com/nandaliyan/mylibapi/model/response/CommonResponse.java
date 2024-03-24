package com.nandaliyan.mylibapi.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class CommonResponse<T> {
    
    private Integer statusCode;
    private String message;
    private T data;

}
