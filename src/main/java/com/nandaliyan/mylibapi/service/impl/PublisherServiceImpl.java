package com.nandaliyan.mylibapi.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nandaliyan.mylibapi.exception.PublisherNotFoundException;
import com.nandaliyan.mylibapi.model.entity.Publisher;
import com.nandaliyan.mylibapi.model.request.PublisherRequest;
import com.nandaliyan.mylibapi.model.response.PublisherResponse;
import com.nandaliyan.mylibapi.repository.PublisherRepository;
import com.nandaliyan.mylibapi.service.PublisherService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PublisherServiceImpl implements PublisherService {

    private final PublisherRepository publisherRepository;

    @Override
    public Publisher create(Publisher publisher) {
        return publisherRepository.save(publisher);
    }

    @Override
    public Publisher getById(Long id) {
        return publisherRepository.findById(id).orElseThrow(() -> new PublisherNotFoundException());
    }

    @Override
    public Publisher getByName(String name) {
        return publisherRepository.findByName(name).orElseThrow(() -> new PublisherNotFoundException());
    }

    @Override
    public PublisherResponse createWithDto(PublisherRequest request) {
        Publisher publisher = Publisher.builder()
                .name(request.getName())
                .isActive(true)
                .build();
        publisherRepository.save(publisher);

        return convertToPublisherResponse(publisher);
    }

    @Override
    public List<PublisherResponse> getAllWithDto() {
        List<Publisher> publishers = publisherRepository.findAll();

        return publishers.stream()
                .map(this::convertToPublisherResponse)
                .toList();
    }

    @Override
    public PublisherResponse getByIdWithDto(Long id) {
        Publisher publisher = getById(id);

        return convertToPublisherResponse(publisher);
    }

    @Override
    public PublisherResponse updateWithDto(Long id, PublisherRequest request) {
        Publisher existingPublisher = getById(id);

        existingPublisher = existingPublisher.toBuilder()
                .id(existingPublisher.getId())
                .name(request.getName())
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
    
}
