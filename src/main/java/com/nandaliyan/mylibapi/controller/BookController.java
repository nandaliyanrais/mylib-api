package com.nandaliyan.mylibapi.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nandaliyan.mylibapi.constant.AppPath;
import com.nandaliyan.mylibapi.model.request.BookRequest;
import com.nandaliyan.mylibapi.model.response.BookResponse;
import com.nandaliyan.mylibapi.model.response.CommonResponse;
import com.nandaliyan.mylibapi.model.response.CommonResponseWithPage;
import com.nandaliyan.mylibapi.model.response.PagingResponse;
import com.nandaliyan.mylibapi.service.BookService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.BOOK_PATH)
public class BookController {

    private final BookService bookService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> createBook(@Valid @RequestBody BookRequest request) {
        BookResponse bookResponse = bookService.createWithDto(request);
        CommonResponse<BookResponse> response = CommonResponse.<BookResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Book created successfully.")
                .data(bookResponse)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(AppPath.GET_ALL_BOOKS)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAllBooks(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        Page<BookResponse> bookResponses = bookService.getAllWithDto(page, size);
        PagingResponse pagingResponse = PagingResponse.builder()
                .currentPage(page)
                .totalPage(bookResponses.getTotalPages())
                .size(size)
                .build();
        CommonResponseWithPage<Page<BookResponse>> response = CommonResponseWithPage.<Page<BookResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Books retrieved successfully.")
                .data(bookResponses)
                .paging(pagingResponse)            
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MEMBER')")
    public ResponseEntity<?> getAllAvailableBooks(
            @RequestParam(required = false) String title, 
            @RequestParam(defaultValue = "0") Integer page, 
            @RequestParam(defaultValue = "10") Integer size) {
        Page<BookResponse> bookResponses = bookService.getAllAvailableBook(page, size);
        PagingResponse pagingResponse = PagingResponse.builder()
                .currentPage(page)
                .totalPage(bookResponses.getTotalPages())
                .size(size)
                .build();
        CommonResponseWithPage<Page<BookResponse>> response = CommonResponseWithPage.<Page<BookResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Books retrieved successfully.")
                .data(bookResponses)
                .paging(pagingResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(AppPath.GET_BY_ID)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MEMBER')")
    public ResponseEntity<?> getBookById(@PathVariable Long id) {
        BookResponse bookResponse = bookService.getByIdWithDto(id);
        CommonResponse<BookResponse> response = CommonResponse.<BookResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Book retrieved successfully.")
                .data(bookResponse)
                .build();
        
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping(AppPath.UPDATE_BY_ID)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateBookById(@PathVariable Long id, @Valid @RequestBody BookRequest request) {
        BookResponse bookResponse = bookService.updateWithDto(id, request);
        CommonResponse<BookResponse> response = CommonResponse.<BookResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Book updated successfully.")
                .data(bookResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping(AppPath.DELETE_BY_ID)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteBookById(@PathVariable Long id) {
        bookService.deleteById(id);
        CommonResponse<BookResponse> response = CommonResponse.<BookResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Book deleted successfully.")
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
}
