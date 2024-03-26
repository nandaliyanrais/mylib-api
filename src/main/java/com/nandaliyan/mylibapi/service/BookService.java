package com.nandaliyan.mylibapi.service;

import java.util.List;

import com.nandaliyan.mylibapi.model.entity.Book;
import com.nandaliyan.mylibapi.model.request.BookRequest;
import com.nandaliyan.mylibapi.model.response.BookResponse;

public interface BookService {
    
    Book getById(Long id);
    
    Book getByTitle(String title);

    Book getByBookCode(String bookCode);
    
    BookResponse createWithDto(BookRequest request);
    
    List<BookResponse> getAllWithDto();

    BookResponse getByIdWithDto(Long id);
    
    BookResponse updateWithDto(Long id, BookRequest request);
    
    void deleteById(Long id);

    void borrowBook(String bookCode);

    void returnBook(String bookCode);

}
