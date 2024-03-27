package com.nandaliyan.mylibapi.service;

import java.util.List;

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

    List<PublisherResponse> getAllWithDto();

    PublisherWithListBookResponse getListBookByUrlName(String urlName);

    PublisherResponse updateWithDto(Long id, PublisherRequest request);

    void deleteById(Long id);
    
}
