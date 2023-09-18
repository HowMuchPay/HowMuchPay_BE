package com.example.howmuch.controller;


import com.example.howmuch.dto.home.GetAllStatisticsResponseDto;
import com.example.howmuch.dto.home.HomeResponseDto;
import com.example.howmuch.service.event.EventService;
import com.example.howmuch.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/home")
@RestController
public class HomeController {
    private final UserService userService;

    private final EventService eventService;

    @GetMapping
    public ResponseEntity<HomeResponseDto> getHome() {
        return ResponseEntity.ok(userService.getHome());
    }

    // 나의, 지인 모든 경조사 조회
    @GetMapping("/statistics")
    public ResponseEntity<GetAllStatisticsResponseDto> getStatistics(){
        return ResponseEntity.ok(eventService.getStatistics());
    }
}
