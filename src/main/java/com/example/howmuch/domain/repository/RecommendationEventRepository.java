package com.example.howmuch.domain.repository;

import com.example.howmuch.constant.AcType;
import com.example.howmuch.constant.EventCategory;
import com.example.howmuch.domain.entity.RecommendationEvent;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RecommendationEventRepository extends JpaRepository<RecommendationEvent, Long> {

    @Query(value = "SELECT AVG(r.payAmount) FROM RecommendationEvent r WHERE r.eventCategory = ?1 AND r.acquaintanceType = ?2 AND " +
            "r.intimacyLevel BETWEEN ?3 AND ?4 AND r.ageGroup = ?5 AND r.annualIncome = ?6 ")
    Optional<Double> getPayAmountByRecommendationEvent(EventCategory eventCategory, AcType acquaintanceType,
                                               int minIntimacyLevel, int maxIntimacyLevel,
                                               int AgeGroup, int AnnualIncome);

    @Query("SELECT r.payAmount, COUNT(*) FROM RecommendationEvent r WHERE r.eventCategory = ?1 AND r.acquaintanceType = ?2 AND " +
        "r.intimacyLevel BETWEEN ?3 AND ?4 AND r.ageGroup = ?5 AND r.annualIncome = ?6 " +
        "GROUP BY r.payAmount " +
        "ORDER BY COUNT(*) DESC")
    List<Object[]> getMostSelectedPayAmountByRecommendationEvent(EventCategory eventCategory, AcType acquaintanceType,
        int minIntimacyLevel, int maxIntimacyLevel,
        int AgeGroup, int AnnualIncome);
}
