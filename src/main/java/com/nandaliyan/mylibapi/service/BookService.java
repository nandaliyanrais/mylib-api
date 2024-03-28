package com.nandaliyan.mylibapi.service;

import org.springframework.data.domain.Page;

import com.nandaliyan.mylibapi.model.entity.Book;
import com.nandaliyan.mylibapi.model.request.BookRequest;
import com.nandaliyan.mylibapi.model.response.BookResponse;

public interface BookService {
    
    Book getById(Long id);
    
    Book getByTitle(String title);

    Book getByBookCode(String bookCode);

    Book getByUrlName(String urlName);
    
    BookResponse createWithDto(BookRequest request);
    
    Page<BookResponse> getAllWithDto(Integer page, Integer size);

    Page<BookResponse> getAllAvailableBook(Integer page, Integer size);

    Page<BookResponse> search(String title, Integer page, Integer size);

    BookResponse getByIdWithDto(Long id);
    
    BookResponse updateWithDto(Long id, BookRequest request);
    
    void deleteById(Long id);

    void checkBookAvailability(String bookCode);

    void borrowBook(String bookCode);

    void returnBook(String bookCode);

}
