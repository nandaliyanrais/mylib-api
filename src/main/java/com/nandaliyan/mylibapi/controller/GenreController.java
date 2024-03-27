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
import com.nandaliyan.mylibapi.model.request.GenreRequest;
import com.nandaliyan.mylibapi.model.response.CommonResponse;
import com.nandaliyan.mylibapi.model.response.CommonResponseWithPage;
import com.nandaliyan.mylibapi.model.response.GenreResponse;
import com.nandaliyan.mylibapi.model.response.GenreWithListBookResponse;
import com.nandaliyan.mylibapi.model.response.PagingResponse;
import com.nandaliyan.mylibapi.service.GenreService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.GENRE_PATH)
public class GenreController {
    
    private final GenreService genreService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> createGenre(@Valid @RequestBody GenreRequest request) {
        GenreResponse genreResponse = genreService.createWithDto(request);
        CommonResponse<GenreResponse> response = CommonResponse.<GenreResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Genre created successfully.")
                .data(genreResponse)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAllGenres(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        Page<GenreResponse> genreResponses = genreService.getAllWithDto(page, size);
        PagingResponse pagingResponse = PagingResponse.builder()
                .currentPage(page)
                .totalPage(genreResponses.getTotalPages())
                .size(size)
                .build();
        CommonResponseWithPage<Page<GenreResponse>> response = CommonResponseWithPage.<Page<GenreResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Genres retrieved successfully.")
                .data(genreResponses)
                .paging(pagingResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // @GetMapping(AppPath.GET_BY_ID)
    // @PreAuthorize("hasRole('ROLE_ADMIN')")
    // public ResponseEntity<?> getGenreById(@PathVariable Long id) {
    //     GenreResponse genreResponse = genreService.getByIdWithDto(id);
    //     CommonResponse<GenreResponse> response = CommonResponse.<GenreResponse>builder()
    //             .statusCode(HttpStatus.OK.value())
    //             .message("Genre retrieved successfully.")
    //             .data(genreResponse)
    //             .build();

    //     return ResponseEntity.status(HttpStatus.OK).body(response);
    // }

    @GetMapping(AppPath.GET_BY_URL_NAME)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MEMBER')")
    public ResponseEntity<?> getGenreByName(@PathVariable String urlName) {
        GenreWithListBookResponse genreWithListBookResponse = genreService.getListBookByUrlName(urlName);
        CommonResponse<GenreWithListBookResponse> response = CommonResponse.<GenreWithListBookResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Genre retrieved successfully.")
                .data(genreWithListBookResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping(AppPath.UPDATE_BY_ID)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateGenreById(@PathVariable Long id, @Valid @RequestBody GenreRequest request) {
        GenreResponse genreResponse = genreService.updateWithDto(id, request);
        CommonResponse<GenreResponse> response = CommonResponse.<GenreResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Genre updated successfully.")
                .data(genreResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping(AppPath.DELETE_BY_ID)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteGenreById(@PathVariable Long id) {
        genreService.deleteById(id);
        CommonResponse<GenreResponse> response = CommonResponse.<GenreResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Genre deleted successfully.")
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
