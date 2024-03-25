package com.nandaliyan.mylibapi.service;

import java.util.List;

import com.nandaliyan.mylibapi.model.entity.Book;
import com.nandaliyan.mylibapi.model.request.BookRequest;
import com.nandaliyan.mylibapi.model.response.BookResponse;

public interface BookService {
    
    Book create(Book book);
    
    Book getById(Long id);
    
    Book getByTitle(String title);
    
    BookResponse createWithDto(BookRequest request);
    
    List<BookResponse> getAllWithDto();

    BookResponse getByIdWithDto(Long id);
    
    BookResponse updateWithDto(Long id, BookRequest request);
    
    void deleteById(Long id);

    boolean isBookAvailable(Long id);

    void decreaseStock(Long id, int quantity);
    
}
