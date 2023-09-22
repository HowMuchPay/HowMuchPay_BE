package com.example.howmuch.domain.entity;

import com.example.howmuch.constant.EventCategory;
import com.example.howmuch.constant.MyType;
import com.example.howmuch.domain.BaseTimeEntity;
import com.example.howmuch.dto.calendar.schedule.GetCalendarScheduleResponseDto;
import com.example.howmuch.dto.event.GetAllMyEventsResponse;
import com.example.howmuch.dto.event.GetMyEventInfoResponseDto;
import com.example.howmuch.dto.home.HomeResponseDto;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "my_event_name")
    private String myEventName; // 경조사 타입이 기타인 경우 not null

    @Column(name = "character_name")
    private String myEventCharacterName; // 경조사 대상이 기타 혹은 가족인 경우 not null

    @Column(name = "event_time")
    private String eventTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usr_id")
    private User user;

    @Builder.Default
    @OneToMany(mappedBy = "myEvent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MyEventDetail> myEventDetails = new ArrayList<>();

    public GetAllMyEventsResponse toGetAllMyEventsResponse() {

        String myEventDisplayName;
        if (myEventName == null) {
            myEventDisplayName = myEventCharacterName + "의 " + eventCategory.getCategoryName();
        } else {
            myEventDisplayName = myEventCharacterName + "의 " + myEventName;
        }
        return GetAllMyEventsResponse.builder()
            .id(id)
            .eventAt(eventAt)
            .receiveAmount(totalReceiveAmount)
            .eventCategory(eventCategory.getValue())
            .myEventDisplayName(myEventDisplayName)
            .build();
    }

    /**
     * 나의 경조사 세부사항 조회시 상단에 나타나는 나의 경조사 정보 DTO
     **/
    public GetMyEventInfoResponseDto toGetMyEventInfoResponsedto(long remainedDay) {
        String myEventDisplayName;

        if (myEventName == null) {
            myEventDisplayName = myEventCharacterName + "의 " + eventCategory.getCategoryName();
        } else {
            myEventDisplayName = myEventCharacterName + "의 " + myEventName;
        }

        return GetMyEventInfoResponseDto.builder()
            .myEventDisplayName(myEventDisplayName)
            .eventAt(eventAt)
            .eventTime(eventTime)
            .remainedDay(remainedDay)
            .build();
    }

    public HomeResponseDto toHomeResponseDto(long pay, long receive, Integer payPercentage,
        int dDay, Long id) {
        String myEventDisplayName;
        if (myEventName == null) {
            myEventDisplayName = myEventCharacterName + "의 " + eventCategory.getCategoryName();
        } else {
            myEventDisplayName = myEventCharacterName + "의 " + myEventName;
        }

        return HomeResponseDto.builder()
            .userTotalPayAmount(pay)
            .userTotalReceiveAmount(receive)
            .payPercentage(payPercentage)
            .eventDisplayName(myEventDisplayName)
            .eventCategory(eventCategory.getValue())
            .dDay(dDay)
            .eventId(id)
            .build();
    }

    public void addReceiveAmount(User user, Long addAmount) {
        user.addTotalReceiveAmount(addAmount);
        this.totalReceiveAmount += addAmount;
    }

    public void minusReceiveAmount(User user, Long minusAmount) {
        user.minusUserTotalReceiveAmount(minusAmount);
        this.totalReceiveAmount -= minusAmount;
    }

    public GetCalendarScheduleResponseDto toGetCalendarScheduleResponseDto() {

        String eventDisplayName;
        if (myEventName == null) {
            eventDisplayName = myEventCharacterName + "의 " + eventCategory.getCategoryName();
        } else {
            eventDisplayName = myEventCharacterName + "의 " + myEventName;
        }

        return GetCalendarScheduleResponseDto.builder()
                .type("myEvent")
                .eventAt(eventAt)
                .amount(totalReceiveAmount)
                .eventDisplayName(eventDisplayName)
                .build();
    }
}
