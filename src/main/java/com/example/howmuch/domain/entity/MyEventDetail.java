package com.example.howmuch.domain.entity;

import com.example.howmuch.domain.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Slf4j
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "my_event_details")
public class MyEventDetail extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_detail_id")
    private Long id;

    @Column(name = "ac_name", nullable = false)
    private String acquaintanceName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "my_event_id")
    private MyEvent myEvent;
}
