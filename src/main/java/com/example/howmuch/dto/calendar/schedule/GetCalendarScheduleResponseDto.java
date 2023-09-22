package com.example.howmuch.dto.calendar.schedule;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class GetCalendarScheduleResponseDto {
    private String type;
    private LocalDate eventAt;
    private Long amount;
    private String eventDisplayName;
}
