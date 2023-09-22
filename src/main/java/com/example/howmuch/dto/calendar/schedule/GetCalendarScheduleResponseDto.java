package com.example.howmuch.dto.calendar.schedule;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetCalendarScheduleResponseDto {
    private String type;
    private LocalDate eventAt;
    private Long totalReceiveAmount;
    private Long payAmount;
    private String eventDisplayName;
}
