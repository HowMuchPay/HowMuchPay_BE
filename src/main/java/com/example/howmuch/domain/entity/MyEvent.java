package com.example.howmuch.domain.entity;

import com.example.howmuch.constant.EventCategory;
import com.example.howmuch.constant.MyType;
import com.example.howmuch.domain.BaseTimeEntity;
import com.example.howmuch.dto.event.GetAllMyEventsResponse;
import com.example.howmuch.dto.event.GetMyEventInfoResponseDto;
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
            myEventDisplayName = myEventCharacterName + "의 " + eventCategory;
        } else {
            myEventDisplayName = myEventCharacterName + "의 " + myEventName;
        }
        return GetAllMyEventsResponse.builder()
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
            myEventDisplayName = myEventCharacterName + "의 " + eventCategory;
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

    public void addReceiveAmount(Long receiveAmount) {
        this.totalReceiveAmount += receiveAmount;
    }
}
