package com.example.howmuch.dto.event;

import com.example.howmuch.domain.entity.AcEvent;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAcEventsResponseDto {
    private String nickname;

    private LocalDate eventAt;

    private int dDay;

    private Long payAmount;

    private Integer eventCategory;

    private Integer acType;

    public static GetAcEventsResponseDto from(AcEvent acEvent, int dDay) {
        return GetAcEventsResponseDto.builder()
                .nickname(acEvent.getAcquaintanceNickname())
                .eventAt(acEvent.getEventAt())
                .dDay(dDay)
                .payAmount(acEvent.getPayAmount())
                .eventCategory(acEvent.getEventCategory().getValue())
                .acType(acEvent.getAcquaintanceType().getValue())
                .build();
    }
}
