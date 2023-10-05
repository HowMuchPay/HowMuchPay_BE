package com.example.howmuch.domain.entity;

import com.example.howmuch.domain.BaseTimeEntity;
import com.example.howmuch.dto.event.GetAllMyEventDetailResponseDto.GetAllMyEventDetails;
import com.example.howmuch.dto.event.GetAllMyEventsResponse;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Slf4j
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "my_event_details")
public class MyEventDetail extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_detail_id")
    private Long id;

    @Column(name = "ac_name", nullable = false)
    private String acquaintanceNickname;

    @Column(name = "rcv_amnt", nullable = false)
    private long receiveAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usr_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "my_event_id")
    private MyEvent myEvent;

    public GetAllMyEventDetails toGetAllMyEventDetails() {
        return GetAllMyEventDetails.builder()
                .id(id)
                .acquaintanceNickname(acquaintanceNickname)
                .receiveAmount(receiveAmount)
                .build();
    }

    public GetAllMyEventsResponse toGetAllMyEventsResponse(MyEvent myEvent) {

        String myEventDisplayName;
        if (myEvent.getMyEventName() == null) {
            myEventDisplayName = myEvent.getMyEventCharacterName() + "의 " + myEvent.getEventCategory().getCategoryName();
        } else {
            myEventDisplayName = myEvent.getMyEventCharacterName() + "의 " + myEvent.getMyEventName();
        }
        return GetAllMyEventsResponse.builder()
                .id(id)
                .eventAt(myEvent.getEventAt())
                .receiveAmount(receiveAmount)
                .eventCategory(myEvent.getEventCategory().getValue())
                .myEventDisplayName(myEventDisplayName)
                .build();
    }

}
