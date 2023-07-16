package com.example.howmuch.domain.repository;

import com.example.howmuch.contant.AcType;
import com.example.howmuch.contant.EventCategory;
import com.example.howmuch.domain.entity.RecommendationEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.OptionalDouble;

public interface RecommendationEventRepository extends JpaRepository<RecommendationEvent, Long> {

    //제일 최신에 생성된 기준으로 친밀도 +-1 로 오차범위를 두어 20개를 가져와 평균값을 리턴하였습니다.
    @Query(value = "SELECT AVG(pay_amnt) FROM recommendation_events WHERE event_category = ?1 AND ac_type = ?2 AND intimacy_level BETWEEN ?3 AND ?4 ORDER BY created_at DESC LIMIT 20", nativeQuery = true)
    OptionalDouble findByEventAndIntimacy(EventCategory eventCategory, AcType acquaintanceType, int minIntimacyLevel, int maxIntimacyLevel);


}