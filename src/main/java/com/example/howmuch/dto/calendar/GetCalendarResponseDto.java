package com.example.howmuch.dto.calendar;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetCalendarResponseDto {
    private List<GetMyEventsCalendarResponseDto> getMyEventsCalendarResponseDto;
    private List<GetAcEventsCalendarResponseDto> getAcEventsCalendarResponseDto;
}
