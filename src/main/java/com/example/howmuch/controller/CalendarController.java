package com.example.howmuch.controller;

import com.example.howmuch.dto.calendar.GetCalendarResponseDto;
import com.example.howmuch.service.calendar.CalendarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/calendar")
@RestController
public class CalendarController {

    private final CalendarService calendarService;

    @GetMapping("/schedule")
    public ResponseEntity<GetCalendarResponseDto> getSchedule(
            @RequestParam(value = "time") LocalDate time
    ) {
        return new ResponseEntity<>(this.calendarService.getSchedule(time), HttpStatus.OK);
    }
}
