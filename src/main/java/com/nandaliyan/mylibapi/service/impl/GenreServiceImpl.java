package com.nandaliyan.mylibapi.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.nandaliyan.mylibapi.exception.GenreNotFoundException;
import com.nandaliyan.mylibapi.model.entity.Genre;
import com.nandaliyan.mylibapi.model.request.GenreRequest;
import com.nandaliyan.mylibapi.model.response.GenreResponse;
import com.nandaliyan.mylibapi.model.response.GenreWithListBookResponse;
import com.nandaliyan.mylibapi.model.response.ListBookResponse;
import com.nandaliyan.mylibapi.repository.GenreRepository;
import com.nandaliyan.mylibapi.service.GenreService;
import com.nandaliyan.mylibapi.util.StringUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;    

    // @Override
    // public List<Genre> saveAll(List<Genre> genres) {
    //     return genreRepository.saveAll(genres);
    // }

    @Override
    public Genre getById(Long id) {
        return genreRepository.findById(id).orElseThrow(() -> new GenreNotFoundException());
    }    

    @Override
    public Genre getByUrlName(String urlName) {
        return genreRepository.findByUrlName(urlName).orElseThrow(() -> new GenreNotFoundException());
    }

    @Override
    public Genre getOrCreateByName(String name) {
        Optional<Genre> existingGenre = genreRepository.findByName(name);
        
        if (existingGenre.isPresent()) {
            Genre genre = existingGenre.get();
            if(!genre.getIsActive()) {
                genre.setIsActive(true);
                genreRepository.save(genre);
            }

            return genre;
        } else {
            Genre newGenre = Genre.builder()
                    .name(name)
                    .urlName(StringUtil.formatNameForUrl(name))
                    .isActive(true)
                    .build();
                    
            return genreRepository.save(newGenre);
        }
    }

    @Override
    public GenreResponse createWithDto(GenreRequest request) {
        Genre genre = Genre.builder()
                .name(request.getName())
                .urlName(StringUtil.formatNameForUrl(request.getName()))
                .isActive(true)
                .build();
        genreRepository.save(genre);

        return convertToGenreResponse(genre);
    }

    @Override
    public Page<GenreResponse> getAllWithDto(Integer page, Integer size) {
        Page<Genre> genres = genreRepository.findAll(PageRequest.of(page, size));

        return genres.map(this::convertToGenreResponse);
    }

    @Override
    public GenreResponse getByIdWithDto(Long id) {
        Genre genre = getById(id);

        return convertToGenreResponse(genre);
    }

    @Override
    public GenreWithListBookResponse getListBookByUrlName(String urlName) {
        Genre genre = getByUrlName(urlName);

        return convertToListBookResponse(genre);
    }

    @Override
    public GenreResponse updateWithDto(Long id, GenreRequest request) {
        Genre existingGenre = getById(id);

        existingGenre = existingGenre.toBuilder()
                .id(existingGenre.getId())
                .name(request.getName())
                .urlName(StringUtil.formatNameForUrl(request.getName()))
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

    private GenreWithListBookResponse convertToListBookResponse(Genre genre) {
        List<ListBookResponse> listBookResponses = genre.getBook().stream()
                .map(book -> ListBookResponse.builder()
                        .title(book.getTitle())
                        .year(book.getYear())
                        .build())
                .toList();

        return GenreWithListBookResponse.builder()
                .name(genre.getName())
                .book(listBookResponses)
                .build();
    }
    
}
