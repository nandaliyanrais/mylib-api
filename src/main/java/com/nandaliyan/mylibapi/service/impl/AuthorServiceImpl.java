package com.nandaliyan.mylibapi.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nandaliyan.mylibapi.exception.AuthorNotFoundException;
import com.nandaliyan.mylibapi.model.entity.Author;
import com.nandaliyan.mylibapi.model.request.AuthorRequest;
import com.nandaliyan.mylibapi.model.response.AuthorResponse;
import com.nandaliyan.mylibapi.repository.AuthorRepository;
import com.nandaliyan.mylibapi.service.AuthorService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public Author create(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public Author getById(Long id) {
        return authorRepository.findById(id).orElseThrow(() -> new AuthorNotFoundException());
    }

    @Override
    public Author getByName(String name) {
        return authorRepository.findByName(name).orElseThrow(() -> new AuthorNotFoundException());
    }

    @Override
    public AuthorResponse createWithDto(AuthorRequest request) {
        Author author = Author.builder()
                .name(request.getName())
                .isActive(true)
                .build();
        authorRepository.save(author);

        return convertToAuthorResponse(author);
    }

    @Override
    public List<AuthorResponse> getAllWithDto() {
        List<Author> authors = authorRepository.findAll();

        return authors.stream()
                .map(this::convertToAuthorResponse)
                .toList();
    }

    @Override
    public AuthorResponse getByIdWithDto(Long id) {
        Author author = getById(id);

        return convertToAuthorResponse(author);
    }

    @Override
    public AuthorResponse updateWithDto(Long id, AuthorRequest request) {
        Author existingAuthor = getById(id);

        existingAuthor = existingAuthor.toBuilder()
                .id(existingAuthor.getId())
                .name(request.getName())
                .isActive(existingAuthor.getIsActive())
                .build();   
        authorRepository.save(existingAuthor);

        return convertToAuthorResponse(existingAuthor);
    }

    @Override
    public void deleteById(Long id) {
        Author author = getById(id);
        author.setIsActive(false);
        authorRepository.save(author);
    }

    private AuthorResponse convertToAuthorResponse(Author author) {
        return AuthorResponse.builder()
                .id(author.getId())
                .name(author.getName())
                .isActive(author.getIsActive())
                .build();
    }
    
}
