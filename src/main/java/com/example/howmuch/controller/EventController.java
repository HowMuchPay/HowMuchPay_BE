package com.example.howmuch.controller;

import com.example.howmuch.dto.event.*;
import com.example.howmuch.service.event.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequestMapping("/event")
@RequiredArgsConstructor
@RestController
public class EventController {

    private final EventService eventService;

    // 나의 경조사 조회
    @GetMapping("/my")
    public ResponseEntity<GetAllMyEventsResponseDto> getAllMyEvents() {
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

    // 나의 경조사 세부사항 조회
    @GetMapping("/my/{id}/details")
    public ResponseEntity<List<GetAllMyEventDetailResponseDto>> getAllMyEventDetails(
            @PathVariable Long id
    ) {
        return new ResponseEntity<>(
                this.eventService.getAllMyEventDetails(id), HttpStatus.OK);
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
    public ResponseEntity<List<GetAllMyEventDetailResponseDto>> getAllMyEventDetailsByName(
            @PathVariable Long id,
            @RequestParam String name
    ) {
        return new ResponseEntity<>(
                this.eventService.getAllMyEventDetailsByName(id, name), HttpStatus.OK);
    }

    /********/


    // 지인 경조사 조회
    @GetMapping("/acquaintance")
    public ResponseEntity<GetAllAcEventsResponseDto> getAllAcEvents() {
        return new ResponseEntity<>(
                this.eventService.getAllAcEvents(), HttpStatus.OK
        );
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


    // 지인 경조사 생성
    @PostMapping("/acquaintance")
    public ResponseEntity<Long> createAcEvent(
            @Valid @RequestBody CreateAcEventRequestDto request
    ) {
        return new ResponseEntity<>(
                this.eventService.createAcEvent(request), HttpStatus.CREATED);

    }
}
