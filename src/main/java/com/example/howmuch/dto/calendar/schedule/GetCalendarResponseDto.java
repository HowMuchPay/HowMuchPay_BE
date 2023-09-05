package com.example.howmuch.dto.calendar.schedule;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetCalendarResponseDto {
    private List<GetMyEventsCalendarResponseDto> myEventsSchedule;
    private List<GetAcEventsCalendarResponseDto> acEventsSchedule;
}
