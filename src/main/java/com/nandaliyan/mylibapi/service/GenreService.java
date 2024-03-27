package com.nandaliyan.mylibapi.service;

import org.springframework.data.domain.Page;

import com.nandaliyan.mylibapi.model.entity.Genre;
import com.nandaliyan.mylibapi.model.request.GenreRequest;
import com.nandaliyan.mylibapi.model.response.GenreResponse;
import com.nandaliyan.mylibapi.model.response.GenreWithListBookResponse;

public interface GenreService {

    // List<Genre> saveAll(List<Genre> genres);
    
    Genre getById(Long id);

    Genre getByUrlName(String urlName);

    Genre getOrCreateByName(String name);

    GenreResponse createWithDto(GenreRequest request);
    
    Page<GenreResponse> getAllWithDto(Integer page, Integer size);

    GenreResponse getByIdWithDto(Long id);

    GenreWithListBookResponse getListBookByUrlName(String urlName);

    GenreResponse updateWithDto(Long id, GenreRequest request);

    void deleteById(Long id);

}
