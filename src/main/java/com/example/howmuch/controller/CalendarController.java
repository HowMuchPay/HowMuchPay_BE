package com.example.howmuch.controller;

import com.example.howmuch.dto.calendar.schedule.GetCalendarResponseDto;
import com.example.howmuch.dto.calendar.statistics.GetStatisticsResponseDto;
import com.example.howmuch.service.calendar.CalendarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/calendar")
@RestController
public class CalendarController {

    private final CalendarService calendarService;


    @GetMapping("/schedule")
    public ResponseEntity<GetCalendarResponseDto> getSchedule(
            @RequestParam(value = "time") String yearAndMonth
    ) {
        System.out.println(yearAndMonth);
        return new ResponseEntity<>(this.calendarService.getSchedule(yearAndMonth), HttpStatus.OK);
    }


    @GetMapping("/statistics")
    public ResponseEntity<GetStatisticsResponseDto> getStatistics(
            @RequestParam(value = "time") @DateTimeFormat(pattern = "yyyy-MM") String yearAndMonth
    ) {
        return new ResponseEntity<>(this.calendarService.getStatistics(yearAndMonth), HttpStatus.OK);
    }
}
