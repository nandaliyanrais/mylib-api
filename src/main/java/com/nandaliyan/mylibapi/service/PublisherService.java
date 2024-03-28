package com.nandaliyan.mylibapi.service;

import org.springframework.data.domain.Page;

import com.nandaliyan.mylibapi.model.entity.Publisher;
import com.nandaliyan.mylibapi.model.request.PublisherRequest;
import com.nandaliyan.mylibapi.model.response.PublisherResponse;
import com.nandaliyan.mylibapi.model.response.PublisherWithListBookResponse;

public interface PublisherService {

    Publisher save(Publisher publisher);

    Publisher getById(Long id);    

    Publisher getByUrlName(String urlName);

    Publisher getOrCreateByName(String name);

    PublisherResponse createWithDto(PublisherRequest request);

    PublisherResponse getByIdWithDto(Long id);

    Page<PublisherResponse> getAllWithDto(Integer page, Integer size);

    PublisherWithListBookResponse getListBookByUrlName(String urlName, int page, int size);

    PublisherResponse updateWithDto(Long id, PublisherRequest request);

    void deleteById(Long id);
    
}
