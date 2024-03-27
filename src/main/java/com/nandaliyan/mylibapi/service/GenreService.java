package com.nandaliyan.mylibapi.service;

import java.util.List;

import com.nandaliyan.mylibapi.model.entity.Genre;
import com.nandaliyan.mylibapi.model.request.GenreRequest;
import com.nandaliyan.mylibapi.model.response.GenreResponse;
import com.nandaliyan.mylibapi.model.response.GenreWithListBookResponse;

public interface GenreService {

    List<Genre> saveAll(List<Genre> genres);
    
    Genre getById(Long id);

    Genre getByUrlName(String urlName);

    Genre getOrCreateByName(String name);

    GenreResponse createWithDto(GenreRequest request);
    
    List<GenreResponse> getAllWithDto();

    GenreResponse getByIdWithDto(Long id);

    GenreWithListBookResponse getListBookByUrlName(String urlName);

    GenreResponse updateWithDto(Long id, GenreRequest request);

    void deleteById(Long id);

}
