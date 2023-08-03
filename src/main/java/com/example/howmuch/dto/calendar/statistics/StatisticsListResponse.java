package com.example.howmuch.dto.calendar.statistics;

import com.example.howmuch.domain.entity.AcEvent;
import com.example.howmuch.domain.entity.MyEvent;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class StatisticsListResponse {
    private String eventAt;
    private List<MyEventStatisticList> myEvents;
    private List<AcEventStatisticList> acEvents;

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MyEventStatisticList {
        private String eventName;
        private long totalReceiveAmount;

        public static MyEventStatisticList from(MyEvent myEvent) {
            String eventName;
            if (myEvent.getMyEventName() == null) {
                eventName = myEvent.getMyEventCharacterName() + "의 " + myEvent.getEventCategory().getCategoryName();
            } else {
                eventName = myEvent.getMyEventCharacterName() + "의 " + myEvent.getMyEventName();
            }
            return MyEventStatisticList.builder()
                    .eventName(eventName)
                    .totalReceiveAmount(myEvent.getTotalReceiveAmount())
                    .build();
        }
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AcEventStatisticList {
        private String eventName;
        private long payAmount;

        public static AcEventStatisticList from(AcEvent acEvent) {
            return AcEventStatisticList.builder()
                    .eventName(acEvent.getAcquaintanceNickname() + "님의 " + acEvent.getEventCategory().getCategoryName())
                    .payAmount(acEvent.getPayAmount())
                    .build();
        }

    }
}
