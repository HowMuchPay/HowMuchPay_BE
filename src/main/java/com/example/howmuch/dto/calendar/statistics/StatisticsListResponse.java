package com.example.howmuch.dto.calendar.statistics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;


/* 캘린더 - 일정 반환(GetCalendarScheduleResponseDto)과 동일 */
@Getter
@Builder
@AllArgsConstructor
public class StatisticsListResponse {
    private String type;
    private LocalDate eventAt;
    private Long amount;
    private int eventCategory;
    private String eventDisplayName;
}
