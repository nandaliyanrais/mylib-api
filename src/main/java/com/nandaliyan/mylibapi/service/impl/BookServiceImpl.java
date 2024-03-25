package com.nandaliyan.mylibapi.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.nandaliyan.mylibapi.exception.BookNotAvailableException;
import com.nandaliyan.mylibapi.exception.BookNotFoundException;
import com.nandaliyan.mylibapi.model.entity.Author;
import com.nandaliyan.mylibapi.model.entity.Book;
import com.nandaliyan.mylibapi.model.entity.Genre;
import com.nandaliyan.mylibapi.model.entity.Publisher;
import com.nandaliyan.mylibapi.model.request.BookRequest;
import com.nandaliyan.mylibapi.model.response.BookAuthorResponse;
import com.nandaliyan.mylibapi.model.response.BookGenreResponse;
import com.nandaliyan.mylibapi.model.response.BookPublisherResponse;
import com.nandaliyan.mylibapi.model.response.BookResponse;
import com.nandaliyan.mylibapi.repository.BookRepository;
import com.nandaliyan.mylibapi.service.AuthorService;
import com.nandaliyan.mylibapi.service.BookService;
import com.nandaliyan.mylibapi.service.GenreService;
import com.nandaliyan.mylibapi.service.PublisherService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final PublisherService publisherService;
    private final GenreService genreService;

    @Override
    public String generateBookCode(String author, String title) {
        String randomDigits = getRandomNumber();
        String lastNameInitial = getLastNameInitial(author);
        String titleInitial = getTitleInitial(title);
        
        return randomDigits + lastNameInitial + titleInitial;
    }

    @Override
    public Book create(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book getById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException());
    }

    @Override
    public Book getByTitle(String title) {
        return bookRepository.findByTitle(title).orElseThrow(() -> new BookNotFoundException());
    }

    @Override
    public BookResponse createWithDto(BookRequest request) {
        Author author = authorService.getOrCreateByName(request.getAuthor());
        Publisher publisher = publisherService.getOrCreateByName(request.getPublisher());
        
        List<Genre> genres = new ArrayList<>();
        for (String genreName : request.getGenres()) {
            Genre genre = genreService.getOrCreateByName(genreName);
            genres.add(genre);
        }

        Book book = Book.builder()
                .bookCode(generateBookCode(request.getAuthor(), request.getTitle()))
                .title(request.getTitle())
                .author(author)
                .publisher(publisher)
                .genre(genres)
                .year(request.getYear())
                .description(request.getDescription())
                .stock(request.getStock())
                .isAvailable(true)
                .build();
        bookRepository.save(book);

        return convertToBookResponse(author, publisher, genres, book);
    }

    @Override
    public List<BookResponse> getAllWithDto() {
        List<BookResponse> bookResponses = bookRepository.findAll().stream()
                .map(book -> convertToBookResponse(book.getAuthor(), book.getPublisher(), book.getGenre(), book))
                .toList();
                
        return bookResponses;
    }

    @Override
    public BookResponse getByIdWithDto(Long id) {
        Book book = getById(id);

        return convertToBookResponse(book.getAuthor(), book.getPublisher(), book.getGenre(), book);
    }

    @Override
    public BookResponse updateWithDto(Long id, BookRequest request) {
        Book existingBook = getById(id);
        Author author = authorService.getOrCreateByName(request.getAuthor());
        Publisher publisher = publisherService.getOrCreateByName(request.getPublisher());
        
        List<Genre> genres = request.getGenres().stream()
                .map(genreService::getOrCreateByName)
                .collect(Collectors.toList());

        existingBook = existingBook.toBuilder()
                .id(existingBook.getId())
                .bookCode(existingBook.getBookCode())
                .title(request.getTitle())
                .author(author)
                .publisher(publisher)
                .genre(genres)
                .year(request.getYear())
                .description(request.getDescription())
                .stock(request.getStock())
                .isAvailable(existingBook.getIsAvailable())
                .build();   
        bookRepository.save(existingBook);

        return convertToBookResponse(existingBook.getAuthor(), existingBook.getPublisher(), existingBook.getGenre(), existingBook);
    }

    @Override
    public void deleteById(Long id) {
        Book book = getById(id);
        book.setIsAvailable(false);
        bookRepository.save(book);
    }

    @Override
    public boolean isBookAvailable(Long id) {
        Book book = getById(id);

        return book.getIsAvailable() && book.getStock() > 0;
    }

    @Override
    public void decreaseStock(Long id, int quantity) {
        Book book = getById(id);

        if (isBookAvailable(id)) {
            int updatedStock = book.getStock() - quantity;
            book.setStock(updatedStock);

            if (updatedStock == 0) {
                book.setIsAvailable(false);
            }
            bookRepository.save(book);
        } else {
            throw new BookNotAvailableException();
        }
    }

    private String getRandomNumber() {
        int numberLength = 4;
        Random random = new Random();
        StringBuilder randomDigits = new StringBuilder();

        for (int i = 0; i < numberLength; i++) {
            randomDigits.append(random.nextInt(10));
        }

        return randomDigits.toString();
    }

    private String getLastNameInitial(String author) {
        String[] names = author.split(" ");
        String lastName = names[names.length - 1];
        int lastNameLength = 3;

        StringBuilder lastNameAbbreviation = new StringBuilder();
        for (char ch : lastName.toCharArray()) {
            if (Character.isLetter(ch)) {
                lastNameAbbreviation.append(ch);
            }
        }

        if (lastNameAbbreviation.length() >= lastNameLength) {
            return lastNameAbbreviation.substring(0, lastNameLength).toUpperCase();
        } else {
            return lastNameAbbreviation.toString().toUpperCase();
        }
    }

    private String getTitleInitial(String title) {
        String[] words = title.split(" ");
        StringBuilder titleInitial = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                titleInitial.append(word.charAt(0));
            }
        }

        return titleInitial.toString().toUpperCase();
    }

    private BookResponse convertToBookResponse(Author author, Publisher publisher, List<Genre> genres, Book book) {
        BookAuthorResponse authorResponse = BookAuthorResponse.builder()
                .id(author.getId())
                .name(author.getName())
                .build();

        BookPublisherResponse publisherResponse = BookPublisherResponse.builder()
                .id(publisher.getId())
                .name(publisher.getName())
                .build();

        List<BookGenreResponse> genreResponses = genres.stream()
                .map(genre -> BookGenreResponse.builder()
                        .id(genre.getId())
                        .name(genre.getName())
                        .build())
                .toList();

        return BookResponse.builder()
                .id(book.getId())
                .bookCode(book.getBookCode())
                .title(book.getTitle())
                .author(authorResponse)
                .publisher(publisherResponse)
                .genre(genreResponses)
                .year(book.getYear())
                .description(book.getDescription())
                .stock(book.getStock())
                .isAvailable(book.getIsAvailable())
                .build();
    }

}
