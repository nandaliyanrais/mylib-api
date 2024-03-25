package com.nandaliyan.mylibapi.service;

import java.util.List;

import com.nandaliyan.mylibapi.model.entity.Genre;
import com.nandaliyan.mylibapi.model.request.GenreRequest;
import com.nandaliyan.mylibapi.model.response.GenreResponse;

public interface GenreService {
    
    Genre create(Genre genre);

    Genre getById(Long id);

    Genre getByName(String name);

    GenreResponse createWithDto(GenreRequest request);
    
    List<GenreResponse> getAllWithDto();

    GenreResponse getByIdWithDto(Long id);

    GenreResponse updateWithDto(Long id, GenreRequest request);

    void deleteById(Long id);

}
