package com.nandaliyan.mylibapi.service;

import java.util.List;

import com.nandaliyan.mylibapi.model.entity.Author;
import com.nandaliyan.mylibapi.model.request.AuthorRequest;
import com.nandaliyan.mylibapi.model.response.AuthorResponse;

public interface AuthorService {
    
    // Author create(Author author);

    Author getById(Long id);

    // Author getByName(String name);

    Author getOrCreateByName(String name);

    AuthorResponse createWithDto(AuthorRequest request);

    List<AuthorResponse> getAllWithDto();

    AuthorResponse getByIdWithDto(Long id);

    AuthorResponse updateWithDto(Long id, AuthorRequest request);

    void deleteById(Long id);

}
