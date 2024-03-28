package com.nandaliyan.mylibapi.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nandaliyan.mylibapi.constant.AppPath;
import com.nandaliyan.mylibapi.model.request.PublisherRequest;
import com.nandaliyan.mylibapi.model.response.CommonResponse;
import com.nandaliyan.mylibapi.model.response.CommonResponseWithPage;
import com.nandaliyan.mylibapi.model.response.PagingResponse;
import com.nandaliyan.mylibapi.model.response.PublisherResponse;
import com.nandaliyan.mylibapi.model.response.PublisherWithListBookResponse;
import com.nandaliyan.mylibapi.service.PublisherService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.PUBLISHER_PATH)
@SecurityRequirement(name = AppPath.BEARER_AUTH)
public class PublisherController {

    private final PublisherService publisherService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAllPublishers(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        Page<PublisherResponse> publisherResponses = publisherService.getAllWithDto(page - 1, size);
        PagingResponse pagingResponse = PagingResponse.builder()
                .currentPage(page)
                .totalPage(publisherResponses.getTotalPages())
                .size(size)
                .build();
        CommonResponseWithPage<Page<PublisherResponse>> response = CommonResponseWithPage.<Page<PublisherResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Publishers retrieved successfully.")
                .data(publisherResponses)
                .paging(pagingResponse)
                .build();
        
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // @GetMapping(AppPath.GET_BY_ID)
    // @PreAuthorize("hasRole('ROLE_ADMIN')")
    // public ResponseEntity<?> getPublisherById(@PathVariable Long id) {
    //     PublisherResponse publisherResponse = publisherService.getByIdWithDto(id);
    //     CommonResponse<PublisherResponse> response = CommonResponse.<PublisherResponse>builder()
    //             .statusCode(HttpStatus.OK.value())
    //             .message("Publisher retrieved successfully.")
    //             .data(publisherResponse)
    //             .build();
        
    //     return ResponseEntity.status(HttpStatus.OK).body(response);
    // }

    @GetMapping(AppPath.GET_BY_URL_NAME)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MEMBER')")
    public ResponseEntity<?> getPublisherByName(@PathVariable String urlName,
            @RequestParam(defaultValue = "1") Integer page, 
            @RequestParam(defaultValue = "5") Integer size) {
        PublisherWithListBookResponse publisherWithListBookResponse = publisherService.getListBookByUrlName(urlName, page - 1, size);
        int totalPage = (int) Math.ceil((double) publisherWithListBookResponse.getTotalBooks() / size);
        
        PagingResponse pagingResponse = PagingResponse.builder()
                .currentPage(page)
                .totalPage(totalPage)
                .size(size)
                .build();
        
        CommonResponseWithPage<PublisherWithListBookResponse> response = CommonResponseWithPage.<PublisherWithListBookResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Publisher retrieved successfully.")
                .data(publisherWithListBookResponse)
                .paging(pagingResponse)
                .build();
        
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping(AppPath.UPDATE_BY_ID)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deletePublisherById(@PathVariable Long id) {
        publisherService.deleteById(id);
        CommonResponse<PublisherResponse> response = CommonResponse.<PublisherResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Publisher deleted successfully.")
                .build();
        
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
}
