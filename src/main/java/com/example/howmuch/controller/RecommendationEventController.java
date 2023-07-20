package com.example.howmuch.controller;



import com.example.howmuch.dto.recommednation.CalculateAverageAmountRequestDto;
import com.example.howmuch.dto.recommednation.CreateRecommendationEventRequestDto;
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


    @PostMapping("/add")
    public ResponseEntity<Long> createRecommendationEvent(
            @Valid @RequestBody CreateRecommendationEventRequestDto request
    ) {
        return new ResponseEntity<>(recommendationEventService.createRecommendationEvent(request), HttpStatus.CREATED);
    }

    @GetMapping("/get")
    public ResponseEntity<Integer> getRecommendation(
            CalculateAverageAmountRequestDto requestDto) {

        if (requestDto.getAnnualIncome() == 0) {
            int result = recommendationEventService.CalculateRecommendationEvent(requestDto);

            if (result == 0) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(result);
            } else {
                return ResponseEntity.ok(result);
            }
        } else {
            int result = recommendationEventService.CalculateRecommendationEventByIncome(requestDto);

            if (result == 0) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(result);
            } else {
                return ResponseEntity.ok(result);
            }
        }
    }
}


