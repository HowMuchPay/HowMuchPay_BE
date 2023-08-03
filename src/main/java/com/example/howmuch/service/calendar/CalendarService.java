package com.example.howmuch.service.calendar;

import com.example.howmuch.domain.entity.AcEvent;
import com.example.howmuch.domain.entity.MyEvent;
import com.example.howmuch.domain.repository.AcEventRepository;
import com.example.howmuch.domain.repository.MyEventRepository;
import com.example.howmuch.dto.calendar.GetAcEventsCalendarResponseDto;
import com.example.howmuch.dto.calendar.GetCalendarResponseDto;
import com.example.howmuch.dto.calendar.GetMyEventsCalendarResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class CalendarService {

    private final MyEventRepository myEventRepository;
    private final AcEventRepository acEventRepository;

    public GetCalendarResponseDto getSchedule(LocalDate time) {
        List<MyEvent> myEvents = this.myEventRepository.findAllByEventAt(time);
        List<AcEvent> acEvents = this.acEventRepository.findAllByEventAt(time);

        return new GetCalendarResponseDto(myEvents.stream()
                .map(GetMyEventsCalendarResponseDto::from)
                .collect(Collectors.toList()), acEvents.stream()
                .map(GetAcEventsCalendarResponseDto::from)
                .collect(Collectors.toList()));
    }
}
