package com.nandaliyan.mylibapi.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nandaliyan.mylibapi.exception.GenreNotFoundException;
import com.nandaliyan.mylibapi.model.entity.Genre;
import com.nandaliyan.mylibapi.model.request.GenreRequest;
import com.nandaliyan.mylibapi.model.response.GenreResponse;
import com.nandaliyan.mylibapi.repository.GenreRepository;
import com.nandaliyan.mylibapi.service.GenreService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Override
    public Genre create(Genre genre) {
        return genreRepository.save(genre);
    }

    @Override
    public Genre getById(Long id) {
        return genreRepository.findById(id).orElseThrow(() -> new GenreNotFoundException());
    }

    @Override
    public Genre getByName(String name) {
        return genreRepository.findByName(name).orElseThrow(() -> new GenreNotFoundException());
    }

    @Override
    public GenreResponse createWithDto(GenreRequest request) {
        Genre genre = Genre.builder()
                .name(request.getName())
                .isActive(true)
                .build();
        genreRepository.save(genre);

        return convertToGenreResponse(genre);
    }

    @Override
    public List<GenreResponse> getAllWithDto() {
        List<Genre> genres = genreRepository.findAll();

        return genres.stream()
                .map(this::convertToGenreResponse)
                .toList();
    }

    @Override
    public GenreResponse getByIdWithDto(Long id) {
        Genre genre = getById(id);

        return convertToGenreResponse(genre);
    }

    @Override
    public GenreResponse updateWithDto(Long id, GenreRequest request) {
        Genre existingGenre = getById(id);

        existingGenre = existingGenre.toBuilder()
                .id(existingGenre.getId())
                .name(request.getName())
                .isActive(existingGenre.getIsActive())
                .build();
        genreRepository.save(existingGenre);

        return convertToGenreResponse(existingGenre);
    }

    @Override
    public void deleteById(Long id) {
        Genre genre = getById(id);
        genre.setIsActive(false);
        genreRepository.save(genre);        
    }

    private GenreResponse convertToGenreResponse(Genre genre) {
        return GenreResponse.builder()
                .id(genre.getId())
                .name(genre.getName())
                .isActive(genre.getIsActive())
                .build();
    }
    
}
