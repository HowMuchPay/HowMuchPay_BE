package com.example.howmuch.domain.entity;

import com.example.howmuch.contant.AcType;
import com.example.howmuch.contant.EventCategory;
import com.example.howmuch.domain.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.LocalDate;

@Slf4j
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ac_events")
public class AcEvent extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ac_event_id")
    private Long id;

    @Column(name = "event_at", nullable = false)
    private LocalDate eventAt;

    @Column(name = "pay_amnt")
    private long payAmount;


    @Column(name = "event_category", nullable = false)
    private EventCategory eventCategory;

    @Column(name = "ac_type", nullable = false)
    private AcType acquaintanceType;

    @Column(name = "ac_id", nullable = false)
    private Long acquaintanceId;
}
