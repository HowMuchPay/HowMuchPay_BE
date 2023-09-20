package com.example.howmuch.service.calendar;

import com.example.howmuch.domain.entity.AcEvent;
import com.example.howmuch.domain.entity.MyEvent;
import com.example.howmuch.domain.entity.User;
import com.example.howmuch.domain.repository.AcEventRepository;
import com.example.howmuch.domain.repository.MyEventRepository;
import com.example.howmuch.domain.repository.UserRepository;
import com.example.howmuch.dto.calendar.schedule.GetAcEventsCalendarResponseDto;
import com.example.howmuch.dto.calendar.schedule.GetCalendarResponseDto;
import com.example.howmuch.dto.calendar.schedule.GetMyEventsCalendarResponseDto;
import com.example.howmuch.dto.calendar.statistics.GetStatisticsResponseDto;
import com.example.howmuch.dto.calendar.statistics.StatisticsListResponse;
import com.example.howmuch.exception.user.NotFoundUserException;
import com.example.howmuch.util.SecurityUtil;
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
    private final UserRepository userRepository;

    private static YearMonth getYearMonth(String yearAndMonth) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        return YearMonth.parse(yearAndMonth, formatter);
    }

    @Transactional(readOnly = true)
    public GetCalendarResponseDto getSchedule(String yearAndMonth) {
        YearMonth yearMonth = getYearMonth(yearAndMonth);

        int year = yearMonth.getYear();
        int month = yearMonth.getMonthValue();

        User user = getUser();

        List<GetMyEventsCalendarResponseDto> myEvents = this.myEventRepository.findAllByUserAndYearAndMonth(user, year, month)
                .stream()
                .map(GetMyEventsCalendarResponseDto::from)
                .toList();

        List<GetAcEventsCalendarResponseDto> acEvents = this.acEventRepository.findAllByUserAndYearAndMonth(user, year, month)
                .stream()
                .map(GetAcEventsCalendarResponseDto::from)
                .toList();

        return new GetCalendarResponseDto(myEvents, acEvents);
    }

    @Transactional(readOnly = true)
    public GetStatisticsResponseDto getStatistics(String yearAndMonth) {
        YearMonth yearMonth = getYearMonth(yearAndMonth);

        int year = yearMonth.getYear();
        int month = yearMonth.getMonthValue();

        User user = getUser();

        List<MyEvent> myEvents = this.myEventRepository.findAllByUserAndYearAndMonth(user, year, month);
        List<AcEvent> acEvents = this.acEventRepository.findAllByUserAndYearAndMonth(user, year, month);

        if (myEvents.isEmpty() && acEvents.isEmpty()) {
            return null;
        }

        // Grouping myEvents by LocalDate
        Map<LocalDate, List<MyEvent>> myEventsGroupedByDate = myEvents.stream()
                .collect(Collectors.groupingBy(MyEvent::getEventAt));

        // Grouping acEvents by LocalDate
        Map<LocalDate, List<AcEvent>> acEventsGroupedByDate = acEvents.stream()
                .collect(Collectors.groupingBy(AcEvent::getEventAt));


        return GetStatisticsResponseDto.builder()
                .totalPayment(acEvents.stream().mapToLong(AcEvent::getPayAmount).sum())
                .totalReceiveAmount(myEvents.stream().mapToLong(MyEvent::getTotalReceiveAmount).sum())
                .mostEventCategory(Objects.requireNonNull(acEvents.stream()
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

    private String getFormattedDate(LocalDate date) {
        String month = date.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault());
        int day = date.getDayOfMonth();
        String dayOfWeek = date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());
        return month + " " + day + "일 " + dayOfWeek;
    }

    private User getUser() {
        return this.userRepository.findById(SecurityUtil.getCurrentUserId())
                .orElseThrow(() -> new NotFoundUserException("일치하는 회원이 존재하지 않습니다."));
    }
}