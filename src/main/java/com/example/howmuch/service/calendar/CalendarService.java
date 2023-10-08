package com.example.howmuch.service.calendar;

import com.example.howmuch.constant.EventCategory;
import com.example.howmuch.domain.entity.AcEvent;
import com.example.howmuch.domain.entity.MyEvent;
import com.example.howmuch.domain.entity.User;
import com.example.howmuch.domain.repository.AcEventRepository;
import com.example.howmuch.domain.repository.MyEventRepository;
import com.example.howmuch.domain.repository.UserRepository;
import com.example.howmuch.dto.calendar.schedule.GetCalendarScheduleResponseDto;
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
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class CalendarService {
    private final MyEventRepository myEventRepository;
    private final AcEventRepository acEventRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public Map<String, List<GetCalendarScheduleResponseDto>> getSchedule(String time) {
        YearMonth yearMonth = getYearMonth(time);
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();
        User user = getUser();

        // 1. 해당 Year, Month 에 해당하는 모든 경조사 정보 가져오기
        List<GetCalendarScheduleResponseDto> responseDtoList = new ArrayList<>();
        this.myEventRepository.findAllByUserAndEventAtBetween(user, startDate, endDate).stream()
                .map(MyEvent::toGetCalendarScheduleResponseDto)
                .forEach(responseDtoList::add);

        this.acEventRepository.findAllByUserAndEventAtBetween(user, startDate, endDate).stream()
                .map(AcEvent::toGetCalendarScheduleResponseDto)
                .forEach(responseDtoList::add);

        // 2. EventAt 최신순 정렬(내림차순)
        responseDtoList.sort(Comparator.comparing(GetCalendarScheduleResponseDto::getEventAt).reversed());

        // 3. Map 으로 그룹화해서 리턴
        return responseDtoList.stream()
                .collect(Collectors.groupingBy(dto -> dto.getEventAt().toString()));
    }

    @Transactional(readOnly = true)
    public GetStatisticsResponseDto getStatistics(String time) {
        YearMonth yearMonth = getYearMonth(time);
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();
        User user = getUser();

        List<MyEvent> myEvents = this.myEventRepository.findAllByUserAndEventAtBetween(user, startDate, endDate);
        List<AcEvent> acEvents = this.acEventRepository.findAllByUserAndEventAtBetween(user, startDate, endDate);

        // 1. 해당 Year, Month 에 해당하는 모든 경조사 정보 가져오기
        List<StatisticsListResponse> responseDtoList = new ArrayList<>();
        myEvents.stream()
                .map(MyEvent::toStatisticsListResponseDto)
                .forEach(responseDtoList::add);

        acEvents.stream()
                .map(AcEvent::toStatisticsListResponseDto)
                .forEach(responseDtoList::add);

        // 2. EventAt 최신순 정렬(내림차순)
        responseDtoList.sort(Comparator.comparing(StatisticsListResponse::getEventAt).reversed());

        // 3. Map 으로 날짜별 그룹화
        Map<String, List<StatisticsListResponse>> resultList = responseDtoList.stream()
                .collect(Collectors.groupingBy(dto -> dto.getEventAt().toString()));

        // 4 - 1. acEvents 에서 가장 많은 비용을 지출한 eventCategory
        Map<EventCategory, Long> totalPayByCategory = acEvents.stream()
                .collect(Collectors.groupingBy(AcEvent::getEventCategory,
                        Collectors.summingLong(AcEvent::getPayAmount)));

        // 4 - 2. 가장 많은 비용을 지출한 EventCategory를 찾습니다.
        Optional<Map.Entry<EventCategory, Long>> maxEntry = totalPayByCategory.entrySet().stream()
                .max(Map.Entry.comparingByValue());


        // 5. GetStatisticsResponseDto 반환
        return GetStatisticsResponseDto.builder()
                .totalPayment(myEvents.stream().mapToLong(MyEvent::getTotalReceiveAmount).sum())
                .totalPayment(acEvents.stream().mapToLong(AcEvent::getPayAmount).sum())
                .statisticsListResponse(resultList)
                .mostEventCategory(maxEntry.orElse(null).getKey().getCategoryName())
                .mostEventPayAmount(maxEntry.orElse(null).getValue())
                .build();
    }

    private YearMonth getYearMonth(String yearAndMonth) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        return YearMonth.parse(yearAndMonth, formatter);
    }

    private EventCategory findMostExpensiveCategory(List<AcEvent> acEvents) {
        Map<EventCategory, Long> totalPayByCategory = acEvents.stream()
                .collect(Collectors.groupingBy(AcEvent::getEventCategory, Collectors.summingLong(AcEvent::getPayAmount)));

        return totalPayByCategory.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
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