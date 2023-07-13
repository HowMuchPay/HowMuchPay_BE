package com.example.howmuch.domain.entity;

import com.example.howmuch.contant.EventCategory;
import com.example.howmuch.domain.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.Date;

@Slf4j
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "my_events")
public class MyEvent extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "my_event_id")
    private Long id;

    @Column(name = "event_at", nullable = false)
    private Date eventAt;

    @Column(name = "rcv_amnt")
    private Long receiveAmount;

    @Column(name = "event_category", nullable = false)
    @Enumerated(EnumType.STRING)
    private EventCategory eventCategory;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "usr_id")
    private User user;
}
