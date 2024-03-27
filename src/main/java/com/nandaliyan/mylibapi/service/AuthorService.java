package com.nandaliyan.mylibapi.service;

import org.springframework.data.domain.Page;

import com.nandaliyan.mylibapi.model.entity.Author;
import com.nandaliyan.mylibapi.model.request.AuthorRequest;
import com.nandaliyan.mylibapi.model.response.AuthorResponse;
import com.nandaliyan.mylibapi.model.response.AuthorWithListBookResponse;

public interface AuthorService {

    Author save(Author author);
    
    Author getById(Long id);

    Author getByName(String name);

    Author getByUrlName(String urlName);

    Author getOrCreateByName(String name);

    AuthorResponse createWithDto(AuthorRequest request);

    Page<AuthorResponse> getAllWithDto(Integer page, Integer size);

    AuthorResponse getByIdWithDto(Long id);

    AuthorWithListBookResponse getListBookByUrlName(String urlName);

    AuthorResponse updateWithDto(Long id, AuthorRequest request);

    void deleteById(Long id);

}
