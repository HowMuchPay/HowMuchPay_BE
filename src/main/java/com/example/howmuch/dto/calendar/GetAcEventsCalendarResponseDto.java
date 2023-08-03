package com.example.howmuch.dto.calendar;

import com.example.howmuch.domain.entity.AcEvent;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAcEventsCalendarResponseDto {

    private LocalDate eventAt;
    private long payAmount;
    private String eventName;

    public static GetAcEventsCalendarResponseDto from(AcEvent acEvent) {
        return GetAcEventsCalendarResponseDto.builder()
                .eventAt(acEvent.getEventAt())
                .payAmount(acEvent.getPayAmount())
                .eventName(acEvent.getAcquaintanceNickname() + "님의 " + acEvent.getEventCategory().getCategoryName())
                .build();
    }
}
