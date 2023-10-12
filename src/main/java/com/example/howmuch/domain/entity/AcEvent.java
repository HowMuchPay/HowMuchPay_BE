package com.example.howmuch.domain.entity;

import com.example.howmuch.constant.AcType;
import com.example.howmuch.constant.EventCategory;
import com.example.howmuch.domain.BaseTimeEntity;
import com.example.howmuch.dto.calendar.schedule.GetCalendarScheduleResponseDto;
import com.example.howmuch.dto.calendar.statistics.StatisticsListResponse;
import com.example.howmuch.dto.event.GetAllAcEventsResponse;
import com.example.howmuch.dto.event.UpdateAcEventRequestDto;
import com.example.howmuch.dto.home.HomeResponseDto;
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
     * 지인의 이름 지인 경조사 이름
     */
    public GetAllAcEventsResponse toGetAllAcEventsResponse() {

        String acEventDisplayName = getAcEventDisplayName();
        return GetAllAcEventsResponse.builder()
                .id(id)
                .acEventDisplayName(acEventDisplayName)
                .eventAt(eventAt)
                .payAmount(payAmount)
                .eventCategory(eventCategory.getValue())
                .build();
    }

    public HomeResponseDto toHomeResponseDto(long pay, long receive, Integer payPercentage,
                                             int dDay, Long id) {
        String acEventDisplayName = getAcEventDisplayName();
        return HomeResponseDto.builder()
                .userTotalPayAmount(pay)
                .userTotalReceiveAmount(receive)
                .payPercentage(payPercentage)
                .eventDisplayName(acEventDisplayName)
                .eventCategory(eventCategory.getValue())
                .eventType("acEvent")
                .dDay(dDay)
                .eventId(id)
                .build();
    }

    public void updateAcEvent(UpdateAcEventRequestDto request) {
        // 삼항 연산자를 사용하여 필드 업데이트
        this.acquaintanceNickname =
                (request.getAcName() != null) ? request.getAcName() : this.acquaintanceNickname;
        this.acquaintanceType =
                (request.getAcType() != null) ? AcType.fromValue(request.getAcType())
                        : this.acquaintanceType;
        this.eventCategory = (request.getEventCategory() != null) ? EventCategory.fromValue(
                request.getEventCategory()) : this.eventCategory;
        this.payAmount = (request.getPayAmount() != null) ? request.getPayAmount() : this.payAmount;
        this.eventAt = (request.getEventAt() != null) ? request.getEventAt() : this.eventAt;
        this.eventTime = (request.getEventTime() != null) ? request.getEventTime() : this.eventTime;
        this.eventName = (request.getEventName() != null) ? request.getEventName() : this.eventName;
    }

    public GetCalendarScheduleResponseDto toGetCalendarScheduleResponseDto() {
        String eventDisplayName = getAcEventDisplayName();

        return GetCalendarScheduleResponseDto.builder()
                .type("acEvent")
                .eventAt(eventAt)
                .amount(payAmount)
                .eventDisplayName(eventDisplayName)
                .build();
    }

    public StatisticsListResponse toStatisticsListResponseDto() {
        String eventDisplayName = getAcEventDisplayName();

        return StatisticsListResponse.builder()
                .type("acEvent")
                .eventAt(eventAt)
                .amount(payAmount)
                .eventDisplayName(eventDisplayName)
                .build();
    }

    public String getAcEventDisplayName() {
        String eventDisplayName;
        if (eventCategory == EventCategory.ETC && eventName != null) {
            eventDisplayName = acquaintanceNickname + "의 " + eventName;
        } else {
            eventDisplayName = acquaintanceNickname + "의 " + eventCategory.getCategoryName();
        }
        return eventDisplayName;
    }

    public String getAcEventDisplayNameWithDetail(){
        String eventDisplayName;
        if (eventCategory == EventCategory.ETC && eventName != null) {
            eventDisplayName = eventName;
        } else {
            eventDisplayName = eventCategory.getCategoryName();
        }
        return eventDisplayName;
    }
}
