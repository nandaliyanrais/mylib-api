package com.nandaliyan.mylibapi.service.impl;

import java.util.List;
import java.util.Optional;

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
    public Author getById(Long id) {
        return authorRepository.findById(id).orElseThrow(() -> new AuthorNotFoundException());
    }

    @Override
    public Author getOrCreateByName(String name) {
        Optional<Author> existingAuthor = authorRepository.findByName(name);
        
        if (existingAuthor.isPresent()) {
            Author author = existingAuthor.get();
            if (!author.getIsActive()) {
                author.setIsActive(true);
                authorRepository.save(author);
            }

            return author;
        } else {
            Author newAuthor = Author.builder()
                    .name(name)
                    .isActive(true)
                    .build();
                    
            return authorRepository.save(newAuthor);
        }
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
