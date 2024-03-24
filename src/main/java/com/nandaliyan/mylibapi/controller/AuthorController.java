package com.nandaliyan.mylibapi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nandaliyan.mylibapi.model.request.AuthorRequest;
import com.nandaliyan.mylibapi.model.response.AuthorResponse;
import com.nandaliyan.mylibapi.model.response.CommonResponse;
import com.nandaliyan.mylibapi.service.AuthorService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/authors")
public class AuthorController {
    
    private final AuthorService authorService;

    @PostMapping
    public ResponseEntity<?> createAuthor(@Valid @RequestBody AuthorRequest request) {
        AuthorResponse authorResponse = authorService.createWithDto(request);
        CommonResponse<AuthorResponse> response = CommonResponse.<AuthorResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Author created successfully.")
                .data(authorResponse)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<?> getAllAuthors() {
        List<AuthorResponse> authorResponses = authorService.getAllWithDto();
        CommonResponse<List<AuthorResponse>> response = CommonResponse.<List<AuthorResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Authors retrieved successfully.")
                .data(authorResponses)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAuthorById(@PathVariable Long id) {
        AuthorResponse authorResponse = authorService.getByIdWithDto(id);
        CommonResponse<AuthorResponse> response = CommonResponse.<AuthorResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Author retrieved successfully.")
                .data(authorResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAuthorById(@PathVariable Long id, @Valid @RequestBody AuthorRequest request) {
        AuthorResponse authorResponse = authorService.updateWithDto(id, request);
        CommonResponse<AuthorResponse> response = CommonResponse.<AuthorResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Author updated successfully.")
                .data(authorResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAuthorById(@PathVariable Long id) {
        authorService.deleteById(id);
        CommonResponse<AuthorResponse> response = CommonResponse.<AuthorResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Author deleted successfully.")
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
