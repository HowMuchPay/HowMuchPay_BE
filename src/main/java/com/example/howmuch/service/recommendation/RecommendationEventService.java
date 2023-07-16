package com.example.howmuch.service.recommendation;

import com.example.howmuch.domain.entity.RecommendationEvent;
import com.example.howmuch.domain.repository.RecommendationEventRepository;
import com.example.howmuch.dto.recommednation.RecommendationEventRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class RecommendationEventService {

    private final RecommendationEventRepository recommendationEventRepository;

    //승현 : 추후 지인 경조사를 등록하는 과정에서 호출해도 좋을것 같다는 생각이 듭니다.
    @Transactional
    public RecommendationEvent createRecommendationEvent(RecommendationEventRequestDto requestDto) {
        int intimacyLevel = calculateIntimacyLevel(requestDto.getIntimacyAnswers());

        RecommendationEvent recommendationEvent = RecommendationEvent.builder()
                .eventCategory(requestDto.getEventCategory())
                .acquaintanceType(requestDto.getAcquaintanceType())
                .payAmount(requestDto.getPayAmount())
                .intimacyLevel(intimacyLevel)
                .build();

        return recommendationEventRepository.save(recommendationEvent);
    }

    //YES, NO 질문을 통해 친밀도 계산 질문(5개)
    private int calculateIntimacyLevel(List<Boolean> intimacyAnswers) {
        int intimacyLevel = 0;

        for (boolean answer : intimacyAnswers) {
            if (answer) {
                intimacyLevel++;
            }
        }

        return intimacyLevel;
    }
}

