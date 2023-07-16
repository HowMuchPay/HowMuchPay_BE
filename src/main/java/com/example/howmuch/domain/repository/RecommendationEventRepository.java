package com.example.howmuch.domain.repository;

import com.example.howmuch.domain.entity.RecommendationEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendationEventRepository extends JpaRepository<RecommendationEvent, Long> {

}
