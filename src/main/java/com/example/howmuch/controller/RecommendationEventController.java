package com.example.howmuch.controller;


import com.example.howmuch.dto.recommednation.CalculateAverageAmountRequestDto;
import com.example.howmuch.service.recommendation.RecommendationEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/recommendation")

public class RecommendationEventController {

    private final RecommendationEventService recommendationEventService;

    @GetMapping("/get")
    public ResponseEntity<Integer> getRecommendation(
            CalculateAverageAmountRequestDto requestDto) {
        int result = recommendationEventService.calculateRecommendationEventByIncome(requestDto);
        if (result == 0) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(result);
        }
        return ResponseEntity.ok(result);
    }
}


