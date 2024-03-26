package com.nandaliyan.mylibapi.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class BorrowRequest {

    @NotBlank(message = "Book code is required.")
    private String bookCode;
    
}
