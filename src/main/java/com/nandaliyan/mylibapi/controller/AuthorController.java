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
import com.nandaliyan.mylibapi.model.request.AuthorRequest;
import com.nandaliyan.mylibapi.model.response.AuthorResponse;
import com.nandaliyan.mylibapi.model.response.AuthorWithListBookResponse;
import com.nandaliyan.mylibapi.model.response.CommonResponse;
import com.nandaliyan.mylibapi.model.response.CommonResponseWithPage;
import com.nandaliyan.mylibapi.model.response.PagingResponse;
import com.nandaliyan.mylibapi.service.AuthorService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.AUTHOR_PATH)
public class AuthorController {
    
    private final AuthorService authorService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAllAuthors(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        Page<AuthorResponse> authorResponses = authorService.getAllWithDto(page, size);
        PagingResponse pagingResponse = PagingResponse.builder()
                .currentPage(page)
                .totalPage(authorResponses.getTotalPages())
                .size(size)
                .build();
        CommonResponseWithPage<Page<AuthorResponse>> response = CommonResponseWithPage.<Page<AuthorResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Authors retrieved successfully.")
                .data(authorResponses)
                .paging(pagingResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // @GetMapping(AppPath.GET_BY_ID)
    // @PreAuthorize("hasRole('ROLE_ADMIN')")
    // public ResponseEntity<?> getAuthorById(@PathVariable Long id) {
    //     AuthorResponse authorResponse = authorService.getByIdWithDto(id);
    //     CommonResponse<AuthorResponse> response = CommonResponse.<AuthorResponse>builder()
    //             .statusCode(HttpStatus.OK.value())
    //             .message("Author retrieved successfully.")
    //             .data(authorResponse)
    //             .build();

    //     return ResponseEntity.status(HttpStatus.OK).body(response);
    // }

    @GetMapping(AppPath.GET_BY_URL_NAME)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MEMBER')")
    public ResponseEntity<?> getAuthorByName(@PathVariable String urlName) {
        AuthorWithListBookResponse authorWithListBookResponse = authorService.getListBookByUrlName(urlName);
        CommonResponse<AuthorWithListBookResponse> response = CommonResponse.<AuthorWithListBookResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Author retrieved successfully.")
                .data(authorWithListBookResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping(AppPath.UPDATE_BY_ID)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateAuthorById(@PathVariable Long id, @Valid @RequestBody AuthorRequest request) {
        AuthorResponse authorResponse = authorService.updateWithDto(id, request);
        CommonResponse<AuthorResponse> response = CommonResponse.<AuthorResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Author updated successfully.")
                .data(authorResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping(AppPath.DELETE_BY_ID)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteAuthorById(@PathVariable Long id) {
        authorService.deleteById(id);
        CommonResponse<AuthorResponse> response = CommonResponse.<AuthorResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Author deleted successfully.")
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
