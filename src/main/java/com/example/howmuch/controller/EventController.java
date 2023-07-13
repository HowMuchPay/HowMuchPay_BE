package com.example.howmuch.controller;

import com.example.howmuch.dto.event.CreateMyEventRequestDto;
import com.example.howmuch.dto.event.GetAllMyEventsResponseDto;
import com.example.howmuch.service.event.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequestMapping("/event")
@RequiredArgsConstructor
@RestController
public class EventController {

    private final EventService eventService;

    @GetMapping("/my")
    public ResponseEntity<GetAllMyEventsResponseDto> getAllMyEvents() {
        return new ResponseEntity<>(
                this.eventService.getAllMyEvents(), HttpStatus.OK);
    }

    @PostMapping("/my")
    public ResponseEntity<Long> createMyEvent(
            @Valid @RequestBody CreateMyEventRequestDto request
    ) {
        return new ResponseEntity<>(
                this.eventService.createMyEvent(request), HttpStatus.CREATED);
    }
}
