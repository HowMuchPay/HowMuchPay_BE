package com.example.howmuch.domain.entity;

import com.example.howmuch.contant.EventCategory;
import com.example.howmuch.domain.BaseTimeEntity;
import com.example.howmuch.dto.event.GetAllMyEventsResponse;
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
@Table(name = "my_events")
public class MyEvent extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "my_event_id")
    private Long id;

    @Column(name = "event_at", nullable = false)
    private LocalDate eventAt;

    @Column(name = "rcv_amnt")
    private long receiveAmount;

    @Column(name = "event_category", nullable = false)
    @Enumerated(EnumType.STRING)
    private EventCategory eventCategory;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "usr_id")
    private User user;

    public GetAllMyEventsResponse of() {
        return GetAllMyEventsResponse.builder()
                .eventAt(eventAt)
                .receiveAmount(receiveAmount)
                .eventCategory(eventCategory.getValue())
                .build();
    }
}
