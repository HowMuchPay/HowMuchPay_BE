package com.example.howmuch.controller;


import com.example.howmuch.dto.recommednation.CalculateAverageAmountRequestDto;
import com.example.howmuch.dto.recommednation.CreateAverageAmountRequestDto;
import com.example.howmuch.dto.recommednation.GetAverageAmountRequestDto;
import com.example.howmuch.service.recommendation.RecommendationEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
@RequestMapping("/recommendation")

public class RecommendationEventController {

    private final RecommendationEventService recommendationEventService;

    @GetMapping("/get")
    public ResponseEntity<Integer> getRecommendation(
            GetAverageAmountRequestDto requestDto) {
        int recommendationAmount = recommendationEventService.getRecommendationEvent(requestDto);
        return ResponseEntity.ok(recommendationAmount);
    }


    @PostMapping("/save")
    public ResponseEntity<Void> SaveRecommendation(
            CreateAverageAmountRequestDto requestDto) {
        recommendationEventService.createRecommendationEvent(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @GetMapping("/app-Get")
    public ResponseEntity<Integer> getRecommendationApp(
            CalculateAverageAmountRequestDto requestDto) {
        int result = recommendationEventService.calculateRecommendationEventByIncome(requestDto);
        if (result == 0) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(result);
        }
        return ResponseEntity.ok(result);
    }

}


