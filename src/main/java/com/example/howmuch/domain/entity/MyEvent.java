package com.example.howmuch.domain.entity;

import com.example.howmuch.constant.EventCategory;
import com.example.howmuch.constant.MyType;
import com.example.howmuch.domain.BaseTimeEntity;
import com.example.howmuch.dto.event.GetAllMyEventsResponse;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.LocalDate;

@Slf4j
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "my_events")
public class MyEvent extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "my_event_id")
    private Long id;

    @Column(name = "event_at", nullable = false)
    private LocalDate eventAt;

    @Column(name = "total_rcv_amnt")
    private long totalReceiveAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "my_type", nullable = false)
    private MyType myType;

    @Column(name = "event_category", nullable = false)
    @Enumerated(EnumType.STRING)
    private EventCategory eventCategory;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "usr_id")
    private User user;

    public GetAllMyEventsResponse of() {
        return GetAllMyEventsResponse.builder()
                .month(eventAt.getMonthValue())
                .eventAt(eventAt)
                .receiveAmount(totalReceiveAmount)
                .eventCategory(eventCategory.getValue())
                .build();
    }

    public void addReceiveAmount(Long receiveAmount) {
        this.totalReceiveAmount += receiveAmount;
    }
}
