package com.nandaliyan.mylibapi.service;

import java.util.List;

import com.nandaliyan.mylibapi.model.entity.Publisher;
import com.nandaliyan.mylibapi.model.request.PublisherRequest;
import com.nandaliyan.mylibapi.model.response.PublisherResponse;

public interface PublisherService {

    // Publisher create(Publisher publisher);

    Publisher getById(Long id);
    
    // Publisher getByName(String name);

    Publisher getOrCreateByName(String name);

    PublisherResponse createWithDto(PublisherRequest request);

    PublisherResponse getByIdWithDto(Long id);

    List<PublisherResponse> getAllWithDto();

    PublisherResponse updateWithDto(Long id, PublisherRequest request);

    void deleteById(Long id);
    
}
