package com.example.howmuch.dto.calendar;

import com.example.howmuch.domain.entity.MyEvent;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetMyEventsCalendarResponseDto {

    private LocalDate eventAt;
    private long totalReceiveAmount;
    private String eventName;

    public static GetMyEventsCalendarResponseDto from(MyEvent myEvent) {

        String myEventDisplayName;
        if (myEvent.getMyEventName() == null) {
            myEventDisplayName = myEvent.getMyEventCharacterName() + "의 " + myEvent.getEventCategory();
        } else {
            myEventDisplayName = myEvent.getMyEventCharacterName() + "의 " + myEvent.getMyEventName();
        }
        return GetMyEventsCalendarResponseDto.builder()
                .eventAt(myEvent.getEventAt())
                .totalReceiveAmount(myEvent.getTotalReceiveAmount())
                .eventName(myEventDisplayName)
                .build();
    }


}
