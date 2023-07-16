package com.example.howmuch.controller;


import com.example.howmuch.domain.entity.RecommendationEvent;
import com.example.howmuch.dto.recommednation.RecommendationEventRequestDto;
import com.example.howmuch.service.recommendation.RecommendationEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class RecommendationEventController {

    private final RecommendationEventService recommendationEventService;


    @PostMapping
    public ResponseEntity<Long> createRecommendationEvent(
            @Valid @RequestBody RecommendationEventRequestDto request
    ) {
        return new ResponseEntity<>(recommendationEventService.createRecommendationEvent(request), HttpStatus.CREATED);
    }
}


