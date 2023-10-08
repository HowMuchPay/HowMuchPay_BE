package com.example.howmuch.controller;

import com.example.howmuch.dto.calendar.schedule.GetCalendarScheduleResponseDto;
import com.example.howmuch.dto.calendar.statistics.GetStatisticsResponseDto;
import com.example.howmuch.service.calendar.CalendarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/calendar")
@RestController
public class CalendarController {
    private final CalendarService calendarService;

    @GetMapping("/schedule")
    public ResponseEntity<Map<String, List<GetCalendarScheduleResponseDto>>> getSchedule(
            @RequestParam String time
    ) {
        return new ResponseEntity<>(this.calendarService.getSchedule(time), HttpStatus.OK);
    }


    @GetMapping("/statistics")
    public ResponseEntity<GetStatisticsResponseDto> getStatistics(
            @RequestParam String time) {
        return new ResponseEntity<>(this.calendarService.getStatistics(time), HttpStatus.OK);
    }
}
