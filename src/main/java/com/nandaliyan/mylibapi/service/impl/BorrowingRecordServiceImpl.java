package com.nandaliyan.mylibapi.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.nandaliyan.mylibapi.exception.BorrowingRecordNotFoundException;
import com.nandaliyan.mylibapi.model.entity.AppUser;
import com.nandaliyan.mylibapi.model.entity.Book;
import com.nandaliyan.mylibapi.model.entity.BorrowingRecord;
import com.nandaliyan.mylibapi.model.entity.Member;
import com.nandaliyan.mylibapi.model.request.BorrowRequest;
import com.nandaliyan.mylibapi.model.request.ReturnRequest;
import com.nandaliyan.mylibapi.model.response.BorrowResponse;
import com.nandaliyan.mylibapi.model.response.BorrowingRecordBookResponse;
import com.nandaliyan.mylibapi.model.response.BorrowingRecordMemberResponse;
import com.nandaliyan.mylibapi.model.response.BorrowingRecordResponse;
import com.nandaliyan.mylibapi.model.response.ReturnResponse;
import com.nandaliyan.mylibapi.repository.BorrowingRecordRepository;
import com.nandaliyan.mylibapi.service.BookService;
import com.nandaliyan.mylibapi.service.BorrowingRecordService;
import com.nandaliyan.mylibapi.service.MemberService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BorrowingRecordServiceImpl implements BorrowingRecordService {

    private final BorrowingRecordRepository borrowingRecordRepository;
    private final MemberService memberService;
    private final BookService bookService;

    @Override
    public BorrowingRecord getById(String id) {
        return borrowingRecordRepository.findById(id).orElseThrow(() -> new BorrowingRecordNotFoundException());
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public BorrowResponse borrowBook(BorrowRequest request, Authentication authentication) {
        String getEmailMember = ((AppUser) authentication.getPrincipal()).getEmail();
        Member member = memberService.getByEmail(getEmailMember);
        Book book = bookService.getByBookCode(request.getBookCode());

        BorrowingRecord borrowingRecord = BorrowingRecord.builder()
                .id(generateId(request.getBookCode()))
                .member(member)
                .book(book)
                .borrowedAt(LocalDateTime.now())
                .build();
        borrowingRecordRepository.save(borrowingRecord);

        bookService.borrowBook(borrowingRecord.getBook().getBookCode());
        
        return convertToBorrowResponse(borrowingRecord);
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public ReturnResponse returnBook(ReturnRequest request, Authentication authentication) {
        String getEmailMember = ((AppUser) authentication.getPrincipal()).getEmail();
        Member member = memberService.getByEmail(getEmailMember);
        BorrowingRecord borrowingRecord = getById(request.getBorrowId());

        LocalDateTime borrowedAt = borrowingRecord.getBorrowedAt();
        LocalDateTime returnedAt = LocalDateTime.now().plusDays(14);

        long daysDifference = ChronoUnit.DAYS.between(borrowedAt, returnedAt);

        if (daysDifference > 7) {
            long daysOverdue = daysDifference - 7; 
            long fineAmount = daysOverdue * 1000; 
            borrowingRecord.setFineAmount(fineAmount);
            borrowingRecordRepository.save(borrowingRecord);
        }

        borrowingRecord.setReturnedAt(returnedAt);
        borrowingRecord.setReturnedBy(member.getName());
        borrowingRecordRepository.save(borrowingRecord);

        bookService.returnBook(borrowingRecord.getBook().getBookCode());
        
        return convertToReturnResponse(borrowingRecord);    
    }

    @Override
    public List<BorrowingRecordResponse> getAllWithDto() {
        List<BorrowingRecordResponse> borrowingRecordResponses = borrowingRecordRepository.findAll().stream()
                .map(borrowingRecord -> convertToBorrowingRecordResponse(borrowingRecord.getMember(), borrowingRecord.getBook(), borrowingRecord))
                .toList();

        return borrowingRecordResponses;
    }

    @Override
    public BorrowingRecordResponse getByIdWithDto(String id) {
        BorrowingRecord borrowingRecord = getById(id);

        return convertToBorrowingRecordResponse(borrowingRecord.getMember(), borrowingRecord.getBook(), borrowingRecord);
    }

    private String generateId(String bookCode) {
        LocalDateTime now = LocalDateTime.now();
        String formattedDateTime = now.format(DateTimeFormatter.ofPattern("yyyyMMddss"));

        return formattedDateTime + bookCode;
    }

    private BorrowResponse convertToBorrowResponse(BorrowingRecord borrowingRecord) {
        return BorrowResponse.builder()
                .id(borrowingRecord.getId())
                .bookCode(borrowingRecord.getBook().getBookCode())
                .title(borrowingRecord.getBook().getTitle())
                .author(borrowingRecord.getBook().getAuthor().getName())
                .borrowedBy(borrowingRecord.getMember().getName())
                .borrowedAt(borrowingRecord.getBorrowedAt())
                .returnBefore(borrowingRecord.getBorrowedAt().plusDays(7))
                .build();
    }
    
    private ReturnResponse convertToReturnResponse(BorrowingRecord borrowingRecord) {
        return ReturnResponse.builder()
                .borrowId(borrowingRecord.getId())
                .bookCode(borrowingRecord.getBook().getBookCode())
                .title(borrowingRecord.getBook().getTitle())
                .returnedAt(borrowingRecord.getReturnedAt())
                .returnedBy(borrowingRecord.getReturnedBy())
                .build();
    }

    private BorrowingRecordResponse convertToBorrowingRecordResponse(Member member, Book book, BorrowingRecord borrowingRecord) {
        BorrowingRecordMemberResponse memberResponse = BorrowingRecordMemberResponse.builder()
                .memberId(member.getMemberId())
                .name(member.getName())
                .build();

        BorrowingRecordBookResponse bookResponse = BorrowingRecordBookResponse.builder()
                .bookCode(book.getBookCode())
                .title(book.getTitle())
                .author(book.getAuthor().getName())
                .publisher(book.getPublisher().getName())
                .build();

        return BorrowingRecordResponse.builder()
                .borrowId(borrowingRecord.getId())
                .borrowedBy(memberResponse)
                .book(bookResponse)
                .borrowedAt(borrowingRecord.getBorrowedAt())
                .returnedAt(borrowingRecord.getReturnedAt())
                .returnedBy(borrowingRecord.getReturnedBy())
                .fineAmount(borrowingRecord.getFineAmount())
                .build();
    }

}
