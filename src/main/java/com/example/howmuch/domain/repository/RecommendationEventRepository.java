package com.example.howmuch.domain.repository;

import com.example.howmuch.constant.AcType;
import com.example.howmuch.constant.EventCategory;
import com.example.howmuch.domain.entity.RecommendationEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.OptionalDouble;
import java.util.OptionalInt;

public interface RecommendationEventRepository extends JpaRepository<RecommendationEvent, Long> {

    //제일 최신에 생성된 기준으로 친밀도 +-1 로 오차범위를 두어 20개를 가져와 평균값을 리턴하였습니다.
    @Query(value = "SELECT AVG(pay_amount) FROM recommendation_events WHERE event_category = ?1 AND ac_type = ?2 AND " +
            "intimacy_level BETWEEN ?3 AND ?4 AND ageGroup = ?5 AND annualIncome BETWEEN ?6 AND  ?7 ORDER BY  " +
            "created_at DESC LIMIT 20", nativeQuery = true)
    OptionalDouble findByEventAndIntimacyAndIncome(EventCategory eventCategory, AcType acquaintanceType,
                                                   int minIntimacyLevel, int maxIntimacyLevel, int AgeGroup,
                                                   int minAnnualIncome, int maxAnnualIncome);

    @Query(value = "SELECT AVG(pay_amnt) FROM recommendation_events WHERE event_category = ?1 AND ac_type = ?2 AND " +
            "intimacy_level BETWEEN ?3 AND ?4 AND ageGroup = ?5 AND annualIncome ?6 ORDER BY  " +
            "created_at DESC", nativeQuery = true)
    OptionalInt getPayAmountByRecommendationEvent(EventCategory eventCategory, AcType acquaintanceType,
                                                  int minIntimacyLevel, int maxIntimacyLevel,
                                                  int AgeGroup, int AnnualIncome);
}
