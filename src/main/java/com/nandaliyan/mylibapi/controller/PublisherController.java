package com.nandaliyan.mylibapi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nandaliyan.mylibapi.constant.AppPath;
import com.nandaliyan.mylibapi.model.request.PublisherRequest;
import com.nandaliyan.mylibapi.model.response.CommonResponse;
import com.nandaliyan.mylibapi.model.response.PublisherResponse;
import com.nandaliyan.mylibapi.service.PublisherService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.PUBLISHER_PATH)
public class PublisherController {

    private final PublisherService publisherService;

    @PostMapping
    public ResponseEntity<?> createPublisher(@Valid @RequestBody PublisherRequest request) {
        PublisherResponse publisherResponse = publisherService.createWithDto(request);
        CommonResponse<PublisherResponse> response = CommonResponse.<PublisherResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Publisher created successfully.")
                .data(publisherResponse)
                .build();
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<?> getAllPublishers() {
        List<PublisherResponse> publisherResponses = publisherService.getAllWithDto();
        CommonResponse<List<PublisherResponse>> response = CommonResponse.<List<PublisherResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Publishers retrieved successfully.")
                .data(publisherResponses)
                .build();
        
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(AppPath.GET_BY_ID)
    public ResponseEntity<?> getPublisherById(@PathVariable Long id) {
        PublisherResponse publisherResponse = publisherService.getByIdWithDto(id);
        CommonResponse<PublisherResponse> response = CommonResponse.<PublisherResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Publisher retrieved successfully.")
                .data(publisherResponse)
                .build();
        
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping(AppPath.UPDATE_BY_ID)
    public ResponseEntity<?> updatePublisherById(@PathVariable Long id, @Valid @RequestBody PublisherRequest request) {
        PublisherResponse publisherResponse = publisherService.updateWithDto(id, request);
        CommonResponse<PublisherResponse> response = CommonResponse.<PublisherResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Publisher updated successfully.")
                .data(publisherResponse)
                .build();
        
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping(AppPath.DELETE_BY_ID)
    public ResponseEntity<?> deletePublisherById(@PathVariable Long id) {
        publisherService.deleteById(id);
        CommonResponse<PublisherResponse> response = CommonResponse.<PublisherResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Publisher deleted successfully.")
                .build();
        
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
}
