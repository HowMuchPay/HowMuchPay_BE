package com.example.howmuch.controller;


import com.example.howmuch.dto.recommednation.CreateAverageAmountRequestDto;
import com.example.howmuch.dto.recommednation.GetAppAverageAmountRequestDto;
import com.example.howmuch.dto.recommednation.GetAverageAmountRequestDto;
import com.example.howmuch.service.recommendation.RecommendationEventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;


@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/recommendation")

public class RecommendationEventController {

    private final RecommendationEventService recommendationEventService;

    @GetMapping("/get")
    public ResponseEntity<Integer> getRecommendation(
            @RequestBody @Valid GetAverageAmountRequestDto requestDto) {
        double recommendationAmount = recommendationEventService.getRecommendationEvent(requestDto);
        return ResponseEntity.ok((int) recommendationAmount);
    }

    @PostMapping("/save")
    public ResponseEntity<Void> SaveRecommendation(
            @RequestBody @Valid CreateAverageAmountRequestDto requestDto) {
        recommendationEventService.createRecommendationEvent(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/app-get")
    public ResponseEntity<Integer> getRecommendationApp(
            @RequestBody @Valid GetAppAverageAmountRequestDto requestDto) {
        double recommendationAmount = recommendationEventService.getRecommendationApp(requestDto);
        return ResponseEntity.ok((int) recommendationAmount);
    }
}


