package com.example.howmuch.service.calendar;

import com.example.howmuch.domain.entity.AcEvent;
import com.example.howmuch.domain.entity.MyEvent;
import com.example.howmuch.domain.repository.AcEventRepository;
import com.example.howmuch.domain.repository.MyEventRepository;
import com.example.howmuch.dto.calendar.schedule.GetAcEventsCalendarResponseDto;
import com.example.howmuch.dto.calendar.schedule.GetCalendarResponseDto;
import com.example.howmuch.dto.calendar.schedule.GetMyEventsCalendarResponseDto;
import com.example.howmuch.dto.calendar.statistics.GetStatisticsResponseDto;
import com.example.howmuch.dto.calendar.statistics.StatisticsListResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class CalendarService {

    private final MyEventRepository myEventRepository;
    private final AcEventRepository acEventRepository;

    private static String getFormattedDate(LocalDate date) {
        String month = date.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault());
        int day = date.getDayOfMonth();
        String dayOfWeek = date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());
        return month + " " + day + "일 " + dayOfWeek;
    }

    @Transactional(readOnly = true)
    public GetCalendarResponseDto getSchedule(LocalDate time) {
        List<MyEvent> myEvents = this.myEventRepository.findAllByEventAt(time);
        List<AcEvent> acEvents = this.acEventRepository.findAllByEventAt(time);

        return new GetCalendarResponseDto(
                myEvents.stream()
                        .map(GetMyEventsCalendarResponseDto::from)
                        .collect(Collectors.toList()),

                acEvents.stream()
                        .map(GetAcEventsCalendarResponseDto::from)
                        .collect(Collectors.toList())
        );
    }

    @Transactional(readOnly = true)
    public GetStatisticsResponseDto getStatistics(String yearAndMonth) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        YearMonth yearMonth = YearMonth.parse(yearAndMonth, formatter);

        int year = yearMonth.getYear();
        int month = yearMonth.getMonthValue();

        List<MyEvent> myEvents = this.myEventRepository.findAllByYearAndMonth(year, month);
        List<AcEvent> acEvents = this.acEventRepository.findAllByYearAndMonth(year, month);

        // Grouping myEvents by LocalDate
        Map<LocalDate, List<MyEvent>> myEventsGroupedByDate = myEvents.stream()
                .collect(Collectors.groupingBy(MyEvent::getEventAt));

        // Grouping acEvents by LocalDate
        Map<LocalDate, List<AcEvent>> acEventsGroupedByDate = acEvents.stream()
                .collect(Collectors.groupingBy(AcEvent::getEventAt));


        return GetStatisticsResponseDto.builder()
                .totalPayment(acEvents.stream().mapToLong(AcEvent::getPayAmount).sum())
                .totalReceiveAmount(myEvents.stream().mapToLong(MyEvent::getTotalReceiveAmount).sum())
                .eventCategory(Objects.requireNonNull(acEvents.stream()
                                .collect(Collectors.groupingBy(AcEvent::getEventCategory,
                                        Collectors.summingLong(AcEvent::getPayAmount))
                                )
                                .entrySet()
                                .stream()
                                .max(Map.Entry.comparingByValue())
                                .map(Map.Entry::getKey)
                                .orElse(null))
                        .getCategoryName()
                )
                .statisticsListResponse(
                        myEventsGroupedByDate.entrySet().stream()
                                .map(entry -> {
                                    LocalDate eventAt = entry.getKey();
                                    List<MyEvent> myEventsForDate = entry.getValue();
                                    List<AcEvent> acEventsForDate = acEventsGroupedByDate.getOrDefault(eventAt, List.of());

                                    return StatisticsListResponse.builder()
                                            .eventAt(getFormattedDate(eventAt))
                                            .myEvents(myEventsForDate.stream()
                                                    .map(StatisticsListResponse.MyEventStatisticList::from)
                                                    .collect(Collectors.toList()))
                                            .acEvents(acEventsForDate.stream()
                                                    .map(StatisticsListResponse.AcEventStatisticList::from)
                                                    .collect(Collectors.toList()))
                                            .build();
                                })
                                .collect(Collectors.toList())
                )
                .build();
    }
}
