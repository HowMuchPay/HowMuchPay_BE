package com.example.howmuch.service.recommendation;

import com.example.howmuch.constant.AcType;
import com.example.howmuch.constant.EventCategory;
import com.example.howmuch.domain.entity.RecommendationEvent;
import com.example.howmuch.domain.repository.RecommendationEventRepository;
import com.example.howmuch.dto.recommednation.CreateAverageAmountRequestDto;
import com.example.howmuch.dto.recommednation.GetAverageAmountRequestDto;
import com.example.howmuch.dto.recommednation.RelationInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class RecommendationEventService {

    private final RecommendationEventRepository recommendationEventRepository;

    @Transactional
    public void createRecommendationEvent(CreateAverageAmountRequestDto requestDto) {
        Integer ageGroup = requestDto.getAgeGroup();
        Integer annualIncome = requestDto.getAnnualIncome();
        List<RelationInfo> relationInfoList = requestDto.getRelationInfoList();
        String phoneNumber = requestDto.getPhoneNumber();

        List<RecommendationEvent> recommendationEvents = new ArrayList<>();

        for (RelationInfo relationInfo : relationInfoList) {
            RecommendationEvent recommendationEvent = RecommendationEvent.builder()
                    .ageGroup(ageGroup)
                    .annualIncome(annualIncome)
                    .acquaintanceType(AcType.fromValue(relationInfo.getAcquaintanceType()))
                    .eventCategory(EventCategory.fromValue(relationInfo.getEventCategory()))
                    .intimacyLevel(relationInfo.getIntimacyLevel())
                    .payAmount(relationInfo.getPayAmount())
                    .phoneNumber(phoneNumber)
                    .build();

            recommendationEvents.add(recommendationEvent);
        }
        recommendationEventRepository.saveAll(recommendationEvents);
    }

    @Transactional(readOnly = true)
    public int getRecommendationEvent(GetAverageAmountRequestDto requestDto) {
        Integer intimacyLevel = requestDto.getIntimacyLevel();
        int minIntimacyLevel = calculateMinIntimacyLevel(intimacyLevel);
        int maxIntimacyLevel = calculateMaxIntimacyLevel(intimacyLevel);

        List<Object[]> results = recommendationEventRepository.getMostSelectedPayAmountByRecommendationEvent(
            EventCategory.fromValue(requestDto.getEventCategory()),
            AcType.fromValue(requestDto.getAcquaintanceType()),
            minIntimacyLevel,
            maxIntimacyLevel,
            requestDto.getAgeGroup(),
            requestDto.getAnnualIncome()
        );
        if (!results.isEmpty()) {
            Object[] firstResult = results.get(0);
            return (int) firstResult[0];
        } else {
            return 0; // 결과가 없을 경우 기본값 0을 반환
        }
    }

    private int calculateMinIntimacyLevel(int intimacyLevel) {
        return Math.max(0, intimacyLevel - 1);
    }

    private int calculateMaxIntimacyLevel(int intimacyLevel) {
        return Math.min(5, intimacyLevel + 1);
    }
}

