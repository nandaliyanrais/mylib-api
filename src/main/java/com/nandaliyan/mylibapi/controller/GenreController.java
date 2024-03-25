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

import com.nandaliyan.mylibapi.model.request.GenreRequest;
import com.nandaliyan.mylibapi.model.response.CommonResponse;
import com.nandaliyan.mylibapi.model.response.GenreResponse;
import com.nandaliyan.mylibapi.service.GenreService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/genres")
public class GenreController {
    
    private final GenreService genreService;

    @PostMapping
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
    public ResponseEntity<?> getAllGenres() {
        List<GenreResponse> genreResponses = genreService.getAllWithDto();
        CommonResponse<List<GenreResponse>> response = CommonResponse.<List<GenreResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Genres retrieved successfully.")
                .data(genreResponses)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getGenreById(@PathVariable Long id) {
        GenreResponse genreResponse = genreService.getByIdWithDto(id);
        CommonResponse<GenreResponse> response = CommonResponse.<GenreResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Genre retrieved successfully.")
                .data(genreResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateGenreById(@PathVariable Long id, @Valid @RequestBody GenreRequest request) {
        GenreResponse genreResponse = genreService.updateWithDto(id, request);
        CommonResponse<GenreResponse> response = CommonResponse.<GenreResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Genre updated successfully.")
                .data(genreResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGenreById(@PathVariable Long id) {
        genreService.deleteById(id);
        CommonResponse<GenreResponse> response = CommonResponse.<GenreResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Genre deleted successfully.")
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
