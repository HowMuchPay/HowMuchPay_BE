package com.example.howmuch.domain.entity;

import com.example.howmuch.constant.AcType;
import com.example.howmuch.constant.EventCategory;
import com.example.howmuch.domain.BaseTimeEntity;
import com.example.howmuch.dto.event.GetAllAcEventsResponse;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "event_category")
    private EventCategory eventCategory;

    @Enumerated(EnumType.STRING)
    @Column(name = "ac_type", nullable = false)
    private AcType acquaintanceType;

    @Column(name = "ac_name", nullable = false)
    private String acquaintanceNickname;

    @Column(name = "event_time")
    private String eventTime;

    @Column(name = "event_name")
    private String eventName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usr_id")
    private User user;

    /**
     * 지인의 이름
     * 지인 경조사 이름
     */
    public GetAllAcEventsResponse toGetAllAcEventsResponse() {

        String acEventDisplayName;
        if (eventCategory == EventCategory.ETC && eventName != null) {
            acEventDisplayName = acquaintanceNickname + "의 " + eventName;
        } else {
            acEventDisplayName = acquaintanceNickname + "의 " + eventCategory.getCategoryName();
        }
        return GetAllAcEventsResponse.builder()
                .acEventDisplayName(acEventDisplayName)
                .eventAt(eventAt)
                .payAmount(payAmount)
                .eventCategory(eventCategory.getValue())
                .build();
    }
}
