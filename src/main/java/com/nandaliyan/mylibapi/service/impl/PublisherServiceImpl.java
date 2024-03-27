package com.nandaliyan.mylibapi.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.nandaliyan.mylibapi.exception.PublisherNotFoundException;
import com.nandaliyan.mylibapi.model.entity.Book;
import com.nandaliyan.mylibapi.model.entity.Publisher;
import com.nandaliyan.mylibapi.model.request.PublisherRequest;
import com.nandaliyan.mylibapi.model.response.ListBookResponse;
import com.nandaliyan.mylibapi.model.response.PublisherResponse;
import com.nandaliyan.mylibapi.model.response.PublisherWithListBookResponse;
import com.nandaliyan.mylibapi.repository.PublisherRepository;
import com.nandaliyan.mylibapi.service.PublisherService;
import com.nandaliyan.mylibapi.util.StringUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PublisherServiceImpl implements PublisherService {

    private final PublisherRepository publisherRepository;

    @Override
    public Publisher save(Publisher publisher) {
        return publisherRepository.save(publisher);
    }

    @Override
    public Publisher getById(Long id) {
        return publisherRepository.findById(id).orElseThrow(() -> new PublisherNotFoundException());
    }

    @Override
    public Publisher getByUrlName(String urlName) {
        return publisherRepository.findByUrlName(urlName).orElseThrow(() -> new PublisherNotFoundException());
    }

    @Override
    public Publisher getOrCreateByName(String name) {
        Optional<Publisher> existingPublisher = publisherRepository.findByName(name);
        
        if (existingPublisher.isPresent()) {
            Publisher publisher = existingPublisher.get();
            if(!publisher.getIsActive()) {
                publisher.setIsActive(true);
                publisherRepository.save(publisher);
            }
            
            return publisher;
        } else {
            Publisher newPublisher = Publisher.builder()
                    .name(name)
                    .urlName(StringUtil.formatNameForUrl(name))
                    .isActive(true)
                    .build();
                    
            return publisherRepository.save(newPublisher);
        }
    }

    @Override
    public PublisherResponse createWithDto(PublisherRequest request) {
        Publisher publisher = Publisher.builder()
                .name(request.getName())
                .urlName(StringUtil.formatNameForUrl(request.getName()))
                .isActive(true)
                .build();
        publisherRepository.save(publisher);

        return convertToPublisherResponse(publisher);
    }

    @Override
    public Page<PublisherResponse> getAllWithDto(Integer page, Integer size) {
        Page<Publisher> publishers = publisherRepository.findAll(PageRequest.of(page, size));

        return publishers.map(this::convertToPublisherResponse);
    }

    @Override
    public PublisherResponse getByIdWithDto(Long id) {
        Publisher publisher = getById(id);

        return convertToPublisherResponse(publisher);
    }

    @Override
    public PublisherWithListBookResponse getListBookByUrlName(String urlName, int page, int size) {
        Publisher publisher = getByUrlName(urlName);

        return convertToListBookResponse(publisher, page, size);
    }

    @Override
    public PublisherResponse updateWithDto(Long id, PublisherRequest request) {
        Publisher existingPublisher = getById(id);

        existingPublisher = existingPublisher.toBuilder()
                .id(existingPublisher.getId())
                .name(request.getName())
                .urlName(StringUtil.formatNameForUrl(request.getName()))
                .isActive(existingPublisher.getIsActive())
                .build();
        publisherRepository.save(existingPublisher);

        return convertToPublisherResponse(existingPublisher);
    }

    @Override
    public void deleteById(Long id) {
        Publisher publisher = getById(id);
        publisher.setIsActive(false);
        publisherRepository.save(publisher);
    }

    private PublisherResponse convertToPublisherResponse(Publisher publisher) {
        return PublisherResponse.builder()
                .id(publisher.getId())
                .name(publisher.getName())
                .isActive(publisher.getIsActive())
                .build();
    }
    
    private PublisherWithListBookResponse convertToListBookResponse(Publisher publisher, int page, int size) {
        List<Book> filteredBooks = publisher.getBook().stream()
                .filter(Book::getIsAvailable)
                .collect(Collectors.toList());

        int startIndex = page * size;
        int endIndex = Math.min(startIndex + size, filteredBooks.size());

        Page<Book> bookPage = new PageImpl<>(filteredBooks.subList(startIndex, endIndex), PageRequest.of(page, size), filteredBooks.size());

        List<ListBookResponse> listBookResponses = bookPage.getContent().stream()
                .map(book -> ListBookResponse.builder()
                        .title(book.getTitle())
                        .year(book.getYear())
                        .build())
                .toList();

        Integer totalBooks = filteredBooks.size();

        return PublisherWithListBookResponse.builder()
                .name(publisher.getName())
                .book(listBookResponses)
                .totalBooks(totalBooks)
                .build();
    }
    
}
