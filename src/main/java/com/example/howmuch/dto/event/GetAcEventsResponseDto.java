package com.example.howmuch.dto.event;

import com.example.howmuch.domain.entity.AcEvent;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAcEventsResponseDto {

    private String acEventDisplayName;

    private String nickname;

    private LocalDate eventAt;

    @JsonProperty("d-day")
    private int dDay;

    private Long payAmount;

    private Integer eventCategory;

    private Integer acType;

    public static GetAcEventsResponseDto of(AcEvent acEvent, int dDay) {
        return GetAcEventsResponseDto.builder()
            .nickname(acEvent.getAcquaintanceNickname())
            .eventAt(acEvent.getEventAt())
            .dDay(dDay)
            .payAmount(acEvent.getPayAmount())
            .eventCategory(acEvent.getEventCategory().getValue())
            .acType(acEvent.getAcquaintanceType().getValue())
            .acEventDisplayName(acEvent.getAcEventDisplayName())
            .build();
    }
}
