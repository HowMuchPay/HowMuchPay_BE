package com.example.howmuch.service.recommendation;

import com.example.howmuch.contant.AcType;
import com.example.howmuch.contant.EventCategory;
import com.example.howmuch.domain.entity.RecommendationEvent;
import com.example.howmuch.domain.repository.RecommendationEventRepository;
import com.example.howmuch.dto.recommednation.CalculateAverageAmountRequestDto;
import com.example.howmuch.dto.recommednation.CreateRecommendationEventRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.OptionalDouble;

@Slf4j
@RequiredArgsConstructor
@Service
public class RecommendationEventService {

    private final RecommendationEventRepository recommendationEventRepository;

    //승현 : 추후 지인 경조사를 등록하는 과정에서 호출해도 좋을것 같다는 생각이 듭니다.
    @Transactional
    public Long createRecommendationEvent(CreateRecommendationEventRequestDto requestDto) {
        int intimacyLevel = calculateIntimacyLevel(requestDto.getIntimacyAnswers());

        RecommendationEvent recommendationEvent = RecommendationEvent.builder()
                .eventCategory(EventCategory.fromValue(requestDto.getEventCategory()))
                .acquaintanceType(AcType.fromValue(requestDto.getAcquaintanceType()))
                .payAmount(requestDto.getPayAmount())
                .intimacyLevel(intimacyLevel)
                .ageGroup(requestDto.getAgeGroup())
                .annualIncome(requestDto.getAnnualIncome())
                .build();

        RecommendationEvent save = recommendationEventRepository.save(recommendationEvent);
        return save.getId();
    }

    @Transactional(readOnly = true)
    public int CalculateRecommendationEvent(CalculateAverageAmountRequestDto requestDto) {
        int intimacyLevel = calculateIntimacyLevel(requestDto.getIntimacyAnswers());

        int minIntimacyLevel = Math.max(0, intimacyLevel - 1); // 친밀도가 0 미만인 경우 0으로 설정
        int maxIntimacyLevel = Math.min(5, intimacyLevel + 1); // 친밀도가 5 이상인 경우 5로 설정


        OptionalDouble AverageAmount = recommendationEventRepository.findByEventAndIntimacy(
                EventCategory.fromValue(requestDto.getEventCategory()),
                AcType.fromValue(requestDto.getAcquaintanceType()),
                minIntimacyLevel,
                maxIntimacyLevel,
                requestDto.getAgeGroup()
        );
        if (AverageAmount.isPresent()) {
            double averageAmount = AverageAmount.getAsDouble();
            int amountInTenThousand = (int) (averageAmount / 10000) * 10000;

            return amountInTenThousand;
        } else {
            return 0;
            //데이터가 부족한 경우 0을 리턴
        }
    }

    @Transactional(readOnly = true)
    public int CalculateRecommendationEventByIncome(CalculateAverageAmountRequestDto requestDto) {
        int intimacyLevel = calculateIntimacyLevel(requestDto.getIntimacyAnswers());

        int minIntimacyLevel = Math.max(0, intimacyLevel - 1); // 친밀도가 0 미만인 경우 0으로 설정
        int maxIntimacyLevel = Math.min(5, intimacyLevel + 1); // 친밀도가 5 이상인 경우 5로 설정

        int minAnnualIncome = Math.max(0, intimacyLevel - 1);
        int maxAnnualIncome = Math.min(5, intimacyLevel + 1);


        OptionalDouble AverageAmount = recommendationEventRepository.findByEventAndIntimacyAndIncome(
                EventCategory.fromValue(requestDto.getEventCategory()),
                AcType.fromValue(requestDto.getAcquaintanceType()),
                minIntimacyLevel,
                maxIntimacyLevel,
                requestDto.getAgeGroup(),
                minAnnualIncome,
                maxAnnualIncome
        );
        if (AverageAmount.isPresent()) {
            double averageAmount = AverageAmount.getAsDouble();
            int amountInTenThousand = (int) (averageAmount / 10000) * 10000;

            return amountInTenThousand;
        } else {
            return 0;
            //데이터가 부족한 경우 0을 리턴
        }
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

