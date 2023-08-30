package com.example.howmuch.controller;


import com.example.howmuch.dto.recommednation.CreateAverageAmountRequestDto;
import com.example.howmuch.dto.recommednation.GetAverageAmountRequestDto;
import com.example.howmuch.service.recommendation.RecommendationEventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/recommendation")

public class RecommendationEventController {

    private final RecommendationEventService recommendationEventService;

    @GetMapping("/get")
    public ResponseEntity<Integer> getRecommendation(
            @RequestParam @NotNull Integer ageGroup,
            @RequestParam @NotNull Integer annualIncome,
            @RequestParam @NotNull @Min(0) @Max(4) Integer eventCategory,
            @RequestParam @NotNull @Min(0) @Max(4) Integer acquaintanceType,
            @RequestParam @NotNull @Min(1) @Max(5) Integer intimacyLevel) {

        GetAverageAmountRequestDto requestDto = GetAverageAmountRequestDto.builder()
                .ageGroup(ageGroup)
                .annualIncome(annualIncome)
                .eventCategory(eventCategory)
                .acquaintanceType(acquaintanceType)
                .intimacyLevel(intimacyLevel)
                .build();

        double recommendationAmount = recommendationEventService.getRecommendationEvent(requestDto);
        return ResponseEntity.ok((int) recommendationAmount);
    }

    @PostMapping("/save")
    public ResponseEntity<Void> saveRecommendation(
            @RequestBody @Valid CreateAverageAmountRequestDto requestDto) {
        recommendationEventService.createRecommendationEvent(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/app-get")
    public ResponseEntity<Integer> getAppRecommendation(
            @RequestParam @NotNull Integer ageGroup,
            @RequestParam @NotNull Integer annualIncome,
            @RequestParam @NotNull @Min(0) @Max(4) Integer eventCategory,
            @RequestParam @NotNull @Min(0) @Max(4) Integer acquaintanceType,
            @RequestParam @NotNull @Min(1) @Max(5) Integer intimacyLevel) {

        GetAverageAmountRequestDto requestDto = GetAverageAmountRequestDto.builder()
                .ageGroup(ageGroup)
                .annualIncome(annualIncome)
                .eventCategory(eventCategory)
                .acquaintanceType(acquaintanceType)
                .intimacyLevel(intimacyLevel)
                .build();

        double recommendationAmount = recommendationEventService.getRecommendationEvent(requestDto);
        return ResponseEntity.ok((int) recommendationAmount);
    }
}


