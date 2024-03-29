package com.example.howmuch.controller;

import com.example.howmuch.dto.event.*;
import com.example.howmuch.dto.event.GetAllMyEventDetailResponseDto.GetAllMyEventDetails;
import com.example.howmuch.dto.notice.GetAllNoticeResponseDto;
import com.example.howmuch.dto.notice.GetNoticeResponseDto;
import com.example.howmuch.service.event.EventService;
import com.example.howmuch.service.notice.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequestMapping("/event")
@RequiredArgsConstructor
@RestController
public class EventController {

    private final EventService eventService;
    private final NoticeService noticeService;

    // 나의 경조사 전체 조회
    @GetMapping("/my")
    public ResponseEntity<GetAllMyEventsResponseDto> getAllMyEvents() {
        SecurityContextHolder.getContext().getAuthentication();
        return new ResponseEntity<>(
                this.eventService.getAllMyEvents(), HttpStatus.OK);
    }

    // 나의 경조사 등록
    @PostMapping("/my")
    public ResponseEntity<Long> createMyEvent(
            @Valid @RequestBody CreateMyEventRequestDto request
    ) {
        return new ResponseEntity<>(
                this.eventService.createMyEvent(request), HttpStatus.CREATED);
    }

    // 나의 경조사 필터링 조회
    @GetMapping("/my/filter")
    public ResponseEntity<GetAllMyEventsResponseDto> getAllMyEventsByFilter(
            @RequestParam String myTypes,
            @RequestParam String eventCategories
    ) {
        return new ResponseEntity<>(
                this.eventService.getAllMyEventsByFilter(myTypes, eventCategories), HttpStatus.OK);
    }

    // 나의 경조사 삭제
    @DeleteMapping("/my/{id}")
    public ResponseEntity<Void> deleteMyEvent(
            @PathVariable Long id
    ) {
        this.eventService.deleteMyEvent(id);
        return ResponseEntity.ok().build();
    }

    // 나의 경조사 비용 낸 모든 사람 이름 조회
    @GetMapping("/my/people")
    public ResponseEntity<List<String>> getAllPeopleFromMyEvents() {
        return new ResponseEntity<>(
                this.eventService.getAllPeopleFromMyEvents(), HttpStatus.OK);
    }

    // 해당 지인이 낸 모든 나의 경조사 조회
    @GetMapping("/my/people/filter")
    public ResponseEntity<GetAllMyEventsResponseDto> getAllMyEventsByAc(
            @RequestParam String name
    ) {
        return new ResponseEntity<>(
                this.eventService.getAllMyEventsByAc(name), HttpStatus.OK);
    }

    // 나의 경조사 아이디 조회
    @GetMapping("/my/{id}/detail")
    public ResponseEntity<GetMyEventsResponseDto> getMyEventDetail(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(eventService.getMyEventDetails(id));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // 나의 경조사 세부사항 조회
    @GetMapping("/my/{id}/details")
    public ResponseEntity<GetAllMyEventDetailResponseDto> getAllMyEventDetails(
            @PathVariable Long id,
            @RequestParam String sort
    ) {
        return new ResponseEntity<>(
                this.eventService.getAllMyEventDetails(id, sort), HttpStatus.OK);
    }

    // 나의 경조사 세부사항 등록
    @PostMapping("/my/{id}/details")
    public ResponseEntity<Long> createMyEventDetail(
            @PathVariable Long id,
            @Valid @RequestBody CreateMyEventDetailRequestDto request
    ) {
        return new ResponseEntity<>(
                this.eventService.createMyEventDetail(id, request), HttpStatus.CREATED);
    }

    // 나의 경조사 세부사항 이름 조회
    @GetMapping("/my/{id}/details/filter")
    public ResponseEntity<List<GetAllMyEventDetails>> getAllMyEventDetailsByName(
            @PathVariable Long id,
            @RequestParam String name
    ) {
        return new ResponseEntity<>(
                this.eventService.getAllMyEventDetailsByName(id, name), HttpStatus.OK);
    }

    // 나의 경조사 세부사항 삭제
    @DeleteMapping("/my/{eventId}/details/{detailId}")
    public ResponseEntity<Void> deleteMyEventDetail(
            @PathVariable Long eventId,
            @PathVariable Long detailId
    ) {
        this.eventService.deleteMyEventDetail(eventId, detailId);
        return ResponseEntity.ok().build();
    }

    /********/

    // 지인 경조사 전체 조회
    @GetMapping("/acquaintance")
    public ResponseEntity<GetAllAcEventsResponseDto> getAllAcEvents() {
        return new ResponseEntity<>(
                this.eventService.getAllAcEvents(), HttpStatus.OK
        );
    }

    // 내가 금액 지출한 모든 지인 이름 조회
    @GetMapping("/acquaintance/people")
    public ResponseEntity<List<String>> getAllAcName() {
        return new ResponseEntity<>(this.eventService.getAcName(), HttpStatus.OK);
    }

    // 지인 경조사 업데이트
    @PatchMapping("/acquaintance/{id}")
    public ResponseEntity<Long> updateAcEvents(
            @PathVariable Long id,
            @RequestBody UpdateAcEventRequestDto request
    ) {
        return ResponseEntity.ok(this.eventService.updateAcEvent(id, request));
    }

    //지인 경조사 삭제
    @DeleteMapping("/acquaintance/{id}")
    public ResponseEntity<Void> deleteAcEvent(
            @PathVariable Long id
    ) {
        this.eventService.deleteAcEvent(id);
        return ResponseEntity.ok().build();
    }

    //지인 경조사 단일(디데이) 조회
    @GetMapping("/acquaintance/{id}/detail")
    public ResponseEntity<GetAcEventsResponseDto> getAcEventDetail(
            @PathVariable Long id
    ) {
        return new ResponseEntity<>(
                this.eventService.getAcEventsWithDay(id), HttpStatus.OK);
    }


    // 지인 경조사 필터링 조회
    @GetMapping("/acquaintance/filter")
    public ResponseEntity<GetAllAcEventsResponseDto> getAllAcEventsByFilter(
            @RequestParam String acTypes,
            @RequestParam String eventCategories
    ) {
        return new ResponseEntity<>(
                this.eventService.getAllAcEventsByFilter(acTypes, eventCategories), HttpStatus.OK);
    }

    // 지인 경조사 등록
    @PostMapping("/acquaintance")
    public ResponseEntity<Long> createAcEvent(
            @Valid @RequestBody CreateAcEventRequestDto request
    ) {
        return new ResponseEntity<>(
                this.eventService.createAcEvent(request), HttpStatus.CREATED);
    }


    // 지인 경조사 이름 조회
    @GetMapping("/acquaintance/nickname")
    public ResponseEntity<GetAllAcEventsResponseDto> getAcEventsByNickname(
            @RequestParam String name
    ) {
        return ResponseEntity.ok(eventService.getAcEventsByName(name));
    }


    /*********/
    // 사용자 공지 사항 조회
    @GetMapping("/notice")
    public ResponseEntity<List<GetAllNoticeResponseDto>> getAllNoticeResponseDto() {
        return new ResponseEntity<>(this.noticeService.getAllNotices(), HttpStatus.OK);
    }


    // 사용자 공지 사항 상세 조회
    @GetMapping("/notice/{id}")
    public ResponseEntity<GetNoticeResponseDto> getNoticeResponseDto(
            @PathVariable Long id
    ) {
        return new ResponseEntity<>(this.noticeService.getNoticeById(id), HttpStatus.OK);
    }
}