package com.nandaliyan.mylibapi.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.nandaliyan.mylibapi.exception.AuthorNotFoundException;
import com.nandaliyan.mylibapi.model.entity.Author;
import com.nandaliyan.mylibapi.model.request.AuthorRequest;
import com.nandaliyan.mylibapi.model.response.AuthorResponse;
import com.nandaliyan.mylibapi.model.response.AuthorWithListBookResponse;
import com.nandaliyan.mylibapi.model.response.ListBookResponse;
import com.nandaliyan.mylibapi.repository.AuthorRepository;
import com.nandaliyan.mylibapi.service.AuthorService;
import com.nandaliyan.mylibapi.util.StringUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public Author save(Author author) {
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
    public Author getByUrlName(String urlName) {
        return authorRepository.findByUrlName(urlName).orElseThrow(() -> new AuthorNotFoundException());
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
                    .urlName(StringUtil.formatNameForUrl(name))
                    .isActive(true)
                    .build();
                    
            return authorRepository.save(newAuthor);
        }
    }

    @Override
    public AuthorResponse createWithDto(AuthorRequest request) {
        Author author = Author.builder()
                .name(request.getName())
                .urlName(StringUtil.formatNameForUrl(request.getName()))
                .isActive(true)
                .build();
        authorRepository.save(author);

        return convertToAuthorResponse(author);
    }

    @Override
    public Page<AuthorResponse> getAllWithDto(Integer page, Integer size) {
        Page<Author> authors = authorRepository.findAll(PageRequest.of(page, size));

        return authors.map(this::convertToAuthorResponse);
    }

    @Override
    public AuthorResponse getByIdWithDto(Long id) {
        Author author = getById(id);

        return convertToAuthorResponse(author);
    }

    @Override
    public AuthorWithListBookResponse getListBookByUrlName(String urlName) {
        Author author = getByUrlName(urlName);

        return convertToListBookResponse(author);
    }

    @Override
    public AuthorResponse updateWithDto(Long id, AuthorRequest request) {
        Author existingAuthor = getById(id);

        existingAuthor = existingAuthor.toBuilder()
                .id(existingAuthor.getId())
                .name(request.getName())
                .urlName(StringUtil.formatNameForUrl(request.getName()))
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

    private AuthorWithListBookResponse convertToListBookResponse(Author author) {
        List<ListBookResponse> listBookResponses = author.getBook().stream()
                .map(book -> ListBookResponse.builder()
                        .title(book.getTitle())
                        .year(book.getYear())
                        .build())
                .toList();

        return AuthorWithListBookResponse.builder()
                .name(author.getName())
                .book(listBookResponses)
                .build();
    }
    
}
