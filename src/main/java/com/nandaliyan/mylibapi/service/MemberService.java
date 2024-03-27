package com.nandaliyan.mylibapi.service;

import org.springframework.data.domain.Page;

import com.nandaliyan.mylibapi.model.entity.Member;
import com.nandaliyan.mylibapi.model.request.MemberUpdateRequest;
import com.nandaliyan.mylibapi.model.response.MemberResponse;

public interface MemberService {
    
    String generateMemberId();

    Member create(Member member);

    Member getById(String id);

    Member getByEmail(String email);

    Page<MemberResponse> getAllWithDto(Integer page, Integer size);

    MemberResponse getByIdWithDto(String id);

    MemberResponse updateWithDto(String id, MemberUpdateRequest request);

    void deleteById(String id);
    
}
