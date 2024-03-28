package com.nandaliyan.mylibapi.service.impl;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.nandaliyan.mylibapi.exception.MemberNotFoundException;
import com.nandaliyan.mylibapi.model.entity.Member;
import com.nandaliyan.mylibapi.model.request.MemberUpdateRequest;
import com.nandaliyan.mylibapi.model.response.MemberResponse;
import com.nandaliyan.mylibapi.repository.MemberRepository;
import com.nandaliyan.mylibapi.service.MemberService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public String generateMemberId() {
        int currentYear = LocalDateTime.now().getYear();
        String lastMemberId = memberRepository.findLastMemberIdByYear(currentYear);
        int sequence = 1;

        if (lastMemberId != null) {
            sequence = Integer.parseInt(lastMemberId.substring(5)) + 1;
        }
        
        return String.format("%d%04d", currentYear, sequence);
    }

    @Override
    public Member create(Member member) {
        return memberRepository.save(member);
    }

    @Override
    public Member getById(String id) {
        return memberRepository.findById(id).orElseThrow(() -> new MemberNotFoundException());
    }

    @Override
    public Member getByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(() -> new MemberNotFoundException());
    }

    @Override
    public Page<MemberResponse> getAllWithDto(Integer page, Integer size) {
        Page<Member> members = memberRepository.findAll(PageRequest.of(page, size));

        return members.map(this::convertToMemberResponse);
    }

    @Override
    public MemberResponse getByIdWithDto(String id) {
        Member member = getById(id);

        return convertToMemberResponse(member);
    }

    @Override
    public MemberResponse updateWithDto(String id, MemberUpdateRequest request) {
        Member existingMember = getById(id);

        existingMember = existingMember.toBuilder()
                .id(existingMember.getId())
                .memberId(existingMember.getMemberId())
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .address(request.getAddress())
                .createdAt(existingMember.getCreatedAt())
                .expiredAt(existingMember.getExpiredAt())
                .updatedAt(LocalDateTime.now())
                .isActive(existingMember.getIsActive())
                .build();
        memberRepository.save(existingMember);

        return convertToMemberResponse(existingMember);
    }

    @Override
    public void deleteById(String id) {
        Member member = getById(id);
        member.setIsActive(false);
        memberRepository.save(member);        
    }

    private MemberResponse convertToMemberResponse(Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .memberId(member.getMemberId())
                .name(member.getName())
                .email(member.getEmail())
                .phone(member.getPhone())
                .address(member.getAddress())
                .isActive(member.getIsActive())
                .createdAt(member.getCreatedAt())
                .expiredAt(member.getExpiredAt())
                .updatedAt(member.getUpdatedAt())
                .build();
    }
    
}
