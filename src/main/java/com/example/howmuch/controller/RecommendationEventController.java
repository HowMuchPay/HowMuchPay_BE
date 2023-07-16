package com.example.howmuch.controller;


import com.example.howmuch.contant.AcType;
import com.example.howmuch.contant.EventCategory;
import com.example.howmuch.dto.recommednation.CalculateAverageAmountRequestDto;
import com.example.howmuch.dto.recommednation.CreateRecommendationEventRequestDto;
import com.example.howmuch.service.recommendation.RecommendationEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/recommendation")

public class RecommendationEventController {

    private final RecommendationEventService recommendationEventService;


    @PostMapping
    public ResponseEntity<Long> createRecommendationEvent(
            @Valid @RequestBody CreateRecommendationEventRequestDto request
    ) {
        return new ResponseEntity<>(recommendationEventService.createRecommendationEvent(request), HttpStatus.CREATED);
    }

    @GetMapping("/recommendation")
    public ResponseEntity<Integer> getRecommendation(
            @Valid @RequestParam EventCategory eventCategory,
            @Valid @RequestParam AcType acquaintanceType,
            @Valid @RequestParam List<Boolean> intimacyAnswers) {
        CalculateAverageAmountRequestDto requestDto = CalculateAverageAmountRequestDto.builder()
                .eventCategory(eventCategory)
                .acquaintanceType(acquaintanceType)
                .intimacyAnswers(intimacyAnswers)
                .build();

        int result = recommendationEventService.CalculateRecommendationEvent(requestDto);

        if (result == 0) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(result);
        } else {
            return ResponseEntity.ok(result);
        }
    }
}


