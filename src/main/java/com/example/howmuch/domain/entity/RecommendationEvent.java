package com.example.howmuch.domain.entity;

import com.example.howmuch.constant.AcType;
import com.example.howmuch.constant.EventCategory;
import com.example.howmuch.domain.BaseTimeEntity;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Slf4j
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "recommendation_events")
public class RecommendationEvent extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recommendation_event_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_category", nullable = false)
    private EventCategory eventCategory;

    @Enumerated(EnumType.STRING)
    @Column(name = "ac_type", nullable = false)
    private AcType acquaintanceType;

    @Column(name = "pay_amnt")
    private long payAmount;

    @Column(name = "intimacy_level", nullable = false)
    private int intimacyLevel;

    //나이대 : 1 =10대 , 2=20대
    @Column(name = "age_group", nullable = false)
    private int ageGroup;

    //연소득 : 1000만원 단위
    @Column(name = "annual_income")
    private int annualIncome;

}
