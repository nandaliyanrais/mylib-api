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

import com.nandaliyan.mylibapi.model.request.BookRequest;
import com.nandaliyan.mylibapi.model.response.BookResponse;
import com.nandaliyan.mylibapi.model.response.CommonResponse;
import com.nandaliyan.mylibapi.service.BookService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<?> createBook(@Valid @RequestBody BookRequest request) {
        BookResponse bookResponse = bookService.createWithDto(request);
        CommonResponse<BookResponse> response = CommonResponse.<BookResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Book created successfully.")
                .data(bookResponse)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<?> getAllBooks() {
        List<BookResponse> bookResponses = bookService.getAllWithDto();
        CommonResponse<List<BookResponse>> response = CommonResponse.<List<BookResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Books retrieved successfully.")
                .data(bookResponses)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBookById(@PathVariable Long id) {
        BookResponse bookResponse = bookService.getByIdWithDto(id);
        CommonResponse<BookResponse> response = CommonResponse.<BookResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Book retrieved successfully.")
                .data(bookResponse)
                .build();
        
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBookById(@PathVariable Long id, @Valid @RequestBody BookRequest request) {
        BookResponse bookResponse = bookService.updateWithDto(id, request);
        CommonResponse<BookResponse> response = CommonResponse.<BookResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Book updated successfully.")
                .data(bookResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBookById(@PathVariable Long id) {
        bookService.deleteById(id);
        CommonResponse<BookResponse> response = CommonResponse.<BookResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Book deleted successfully.")
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
}
