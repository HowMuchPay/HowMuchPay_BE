package com.example.howmuch.service.event;

import com.example.howmuch.constant.AcType;
import com.example.howmuch.constant.EventCategory;
import com.example.howmuch.constant.MyType;
import com.example.howmuch.domain.entity.AcEvent;
import com.example.howmuch.domain.entity.MyEvent;
import com.example.howmuch.domain.entity.MyEventDetail;
import com.example.howmuch.domain.entity.User;
import com.example.howmuch.domain.repository.AcEventRepository;
import com.example.howmuch.domain.repository.MyEventDetailRepository;
import com.example.howmuch.domain.repository.MyEventRepository;
import com.example.howmuch.domain.repository.UserRepository;
import com.example.howmuch.dto.event.*;
import com.example.howmuch.dto.event.GetAllMyEventDetailResponseDto.GetAllMyEventDetails;
import com.example.howmuch.exception.event.NeedEventCharacterNameException;
import com.example.howmuch.exception.event.NeedEventNameException;
import com.example.howmuch.exception.event.NotFoundEventDetailException;
import com.example.howmuch.exception.event.NotFoundEventException;
import com.example.howmuch.exception.user.NotFoundUserException;
import com.example.howmuch.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class EventService {
    private final UserRepository userRepository;
    private final MyEventRepository myEventRepository;
    private final AcEventRepository acEventRepository;
    private final MyEventDetailRepository myEventDetailRepository;


    // 나의 경조사 등록
    @Transactional
    public Long createMyEvent(CreateMyEventRequestDto request) {
        checkRequest(request);
        return this.myEventRepository.save(request.toEntity(getUser())).getId();
    }

    // 나의 모든 경조사 조회
    @Transactional(readOnly = true)
    public GetAllMyEventsResponseDto getAllMyEvents() {

        User user = getUser();
        Map<String, List<GetAllMyEventsResponse>> allMyEvents = this.myEventRepository.findAllByUserOrderByEventAtDesc(user)
            .stream()
            .map(MyEvent::toGetAllMyEventsResponse)
            .collect(Collectors.groupingBy(
                response -> YearMonth.from(response.getEventAt()).toString(),
                LinkedHashMap::new,
                Collectors.toList()
            ));

        Map<String, List<GetAllMyEventsResponse>> sortedMyEvents = allMyEvents.entrySet()
            .stream()
            .sorted(Map.Entry.<String, List<GetAllMyEventsResponse>>comparingByKey().reversed())
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (oldValue, newValue) -> oldValue,
                LinkedHashMap::new
            ));

        return new GetAllMyEventsResponseDto(user.getUserTotalReceiveAmount(), sortedMyEvents);
    }

    // 나의 경조사 필터링 조회
    @Transactional(readOnly = true)
    public GetAllMyEventsResponseDto getAllMyEventsByFilter(String myTypes, String eventCategories) {

        List<String> myTypeList = List.of(myTypes.split(","));
        List<String> eventCategoryList = List.of(eventCategories.split(","));

        User user = getUser();
        long totalReceiveAmount = 0L;

        List<MyEvent> myEvents = myTypeList.stream()
            .map(myTypeString -> MyType.fromValue(Integer.parseInt(myTypeString.trim())))
            .flatMap(myType -> eventCategoryList.stream()
                .map(eventCategoryString -> EventCategory.fromValue(Integer.parseInt(eventCategoryString.trim())))
                .flatMap(eventCategory -> this.myEventRepository.findAllByUserAndMyTypeAndEventCategoryOrderByEventAtDesc(user, myType, eventCategory).stream())
            )
            .toList();
        for (MyEvent myEvent : myEvents) {
            totalReceiveAmount += myEvent.getTotalReceiveAmount();
        }

        Map<String, List<GetAllMyEventsResponse>> sortedMyEvents = myEvents
            .stream()
            .map(MyEvent::toGetAllMyEventsResponse)
            .collect(Collectors.groupingBy(
                response -> YearMonth.from(response.getEventAt()).toString(),
                Collectors.toList()
            )).entrySet()
            .stream()
            .sorted(Map.Entry.<String, List<GetAllMyEventsResponse>>comparingByKey().reversed())
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (oldValue, newValue) -> oldValue,
                LinkedHashMap::new
            ));

        return new GetAllMyEventsResponseDto(totalReceiveAmount, sortedMyEvents);
    }

    // 나의 경조사 세부 사항 등록

    /**
     * 1. MyEvent receiveAmount 필드 증가
     * 2. 해당 User TotalReceiveAmount 필드 증가
     **/
    @Transactional
    public Long createMyEventDetail(Long id, CreateMyEventDetailRequestDto request) {
        MyEvent myEvent = getMyEvent(id);
        myEvent.addReceiveAmount(getUser(), request.getReceiveAmount()); // 해당 경조사 받은 금액 추가
        return this.myEventDetailRepository.save(request.toEntity(myEvent)).getId();
    }

    // 나의 경조사 세부 사항 전체 조회
    @Transactional(readOnly = true)
    public GetAllMyEventDetailResponseDto getAllMyEventDetails(Long id, String sort) {
        MyEvent myEvent = getMyEvent(id);

        // MyEventInfo
        GetMyEventInfoResponseDto myEventInfo
            = myEvent.toGetMyEventInfoResponsedto(calculateRemainedDay(myEvent));

        if (sort.equals("asc")) { // 오름 차순
            return GetAllMyEventDetailResponseDto.builder()
                .myEventInfo(myEventInfo)
                .myDetails(this.myEventDetailRepository.findAllByMyEventOrderByReceiveAmountAsc(myEvent)
                    .stream()
                    .map(MyEventDetail::toGetAllMyEventDetails)
                    .collect(Collectors.toList()))
                .build();
        } else { // 내림 차순
            return GetAllMyEventDetailResponseDto.builder()
                .myEventInfo(myEventInfo)
                .myDetails(this.myEventDetailRepository.findAllByMyEventOrderByReceiveAmountDesc(myEvent)
                    .stream()
                    .map(MyEventDetail::toGetAllMyEventDetails)
                    .collect(Collectors.toList()))
                .build();
        }
    }

    // 나의 경조사 세부 사항 이름 조회
    @Transactional(readOnly = true)
    public List<GetAllMyEventDetails> getAllMyEventDetailsByName(Long id, String name) {
        return this.myEventDetailRepository.findAllByMyEventAndAcquaintanceNicknameContainingIgnoreCase(getMyEvent(id), name)
            .stream()
            .map(MyEventDetail::toGetAllMyEventDetails)
            .toList();
    }

    // 나의 경조사 삭제
    @Transactional
    public void deleteMyEvent(Long id) {
        User user = getUser();
        MyEvent myEvent = getMyEvent(id);
        user.minusUserTotalReceiveAmount(myEvent.getTotalReceiveAmount());
        user.getMyEvents().remove(myEvent);
    }

    // 나의 경조사 세부사항 삭제

    /**
     * 1. 나의 경조사 세부사항 삭제 시 MyEvent receiveAmount 필드 금액 차감 필요
     * 2. 해당 사용자 User userTotalReceiveAmount 필드 금액 차감 필요
     **/
    @Transactional
    public void deleteMyEventDetail(Long eventId, Long detailId) {
        MyEvent myEvent = getMyEvent(eventId);
        MyEventDetail myEventDetail = getMyEventDetail(detailId);
        myEvent.minusReceiveAmount(getUser(), myEventDetail.getReceiveAmount());
        myEvent.getMyEventDetails().remove(myEventDetail);
    }


    /*********/

    // 지인 경조사 등록 + payAmount 누적
    @Transactional
    public Long createAcEvent(CreateAcEventRequestDto request) {
        if (request.getEventCategory() == 4 && request.getEventName() == null) {
            throw new NeedEventNameException("경조사 종류가 기타인 경우에는 별도의 EventName이 필요합니다.");
        }
        getUser().addTotalPayAmount(request.getPayAmount()); // 내가 지불한 총 금액 추가
        return this.acEventRepository.save(request.toEntity(getUser())).getId();
    }

    // 지인 모든 경조사 조회
    @Transactional(readOnly = true)
    public GetAllAcEventsResponseDto getAllAcEvents() {

        User user = getUser();
        Map<String, List<GetAllAcEventsResponse>> allAcEvents = this.acEventRepository.findAllByUserOrderByEventAtDesc(user)
            .stream()
            .map(AcEvent::toGetAllAcEventsResponse)
            .collect(Collectors.groupingBy(
                response -> YearMonth.from(response.getEventAt()).toString(),
                LinkedHashMap::new,
                Collectors.toList()
            ));

        Map<String, List<GetAllAcEventsResponse>> sortedAcEvents = allAcEvents.entrySet()
            .stream()
            .sorted(Map.Entry.<String, List<GetAllAcEventsResponse>>comparingByKey().reversed())
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (oldValue, newValue) -> oldValue,
                LinkedHashMap::new
            ));

        return new GetAllAcEventsResponseDto(user.getUserTotalPayAmount(), sortedAcEvents);
    }

    // 지인 경조사 필터링 조회
    @Transactional(readOnly = true)
    public GetAllAcEventsResponseDto getAllAcEventsByFilter(String acTypes, String eventCategories) {

        List<String> acTypeList = List.of(acTypes.split(","));
        List<String> eventCategoryList = List.of(eventCategories.split(","));

        User user = getUser();
        long totalPayAmount = 0L;

        List<AcEvent> acEvents = acTypeList.stream()
            .map(acTypeString -> AcType.fromValue(Integer.parseInt(acTypeString.trim())))
            .flatMap(acType -> eventCategoryList.stream()
                .map(eventCategoryString -> EventCategory.fromValue(Integer.parseInt(eventCategoryString.trim())))
                .flatMap(eventCategory
                    -> this.acEventRepository.findAllByUserAndAcquaintanceTypeAndEventCategoryOrderByEventAtDesc(user, acType, eventCategory).stream())
            ).toList();

        for (AcEvent acEvent : acEvents) {
            totalPayAmount += acEvent.getPayAmount();
        }

        Map<String, List<GetAllAcEventsResponse>> sortedAcEvents = acEvents
            .stream()
            .map(AcEvent::toGetAllAcEventsResponse)
            .collect(Collectors.groupingBy(
                response -> YearMonth.from(response.getEventAt()).toString(),
                Collectors.toList()
            )).entrySet()
            .stream()
            .sorted(Map.Entry.<String, List<GetAllAcEventsResponse>>comparingByKey().reversed())
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (oldValue, newValue) -> oldValue,
                LinkedHashMap::new
            ));


        return new GetAllAcEventsResponseDto(totalPayAmount, sortedAcEvents);
    }

    // 지인 단일 경조사 조회 (디데이)
    @Transactional(readOnly = true)
    public GetAcEventsResponseDto getAcEventsWithDay(Long acId) {
        AcEvent acEvent = getAcEvent(acId); // 특정 지인의 경조사 정보를 가져옴
        //dDay 로직
        long daysUntilEvent = ChronoUnit.DAYS.between(acEvent.getEventAt(), LocalDate.now());
        return GetAcEventsResponseDto.of(acEvent, (int) daysUntilEvent);
    }

    // 지인 경조사 삭제
    @Transactional
    public void deleteAcEvent(Long id) {
        User user = getUser();
        AcEvent acEvent = getAcEvent(id);
        user.minusUserTotalPayAmount(acEvent.getPayAmount());
        this.acEventRepository.delete(acEvent);
    }

    // 지인 경조사 조회 (이름)
    // 이름으로 조회시 여러개의 경조사가 있을 수 있음
    @Transactional(readOnly = true)
    public GetAllAcEventsResponseDto getAcEventsByName(String acquaintanceName) {
        User user = getUser();
        List<AcEvent> byUserAndAcquaintanceNickname = this.acEventRepository.findByUserAndAcquaintanceNickname(
            user, acquaintanceName);

        Map<String, List<GetAllAcEventsResponse>> allAcEvents = byUserAndAcquaintanceNickname
            .stream()
            .map(AcEvent::toGetAllAcEventsResponse)
            .collect(Collectors.groupingBy(
                response -> YearMonth.from(response.getEventAt()).toString(),
                LinkedHashMap::new,
                Collectors.toList()
            ));


        Map<String, List<GetAllAcEventsResponse>> sortedAcEvents = allAcEvents.entrySet()
            .stream()
            .sorted(Map.Entry.<String, List<GetAllAcEventsResponse>>comparingByKey().reversed())
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (oldValue, newValue) -> oldValue,
                LinkedHashMap::new
            ));
        // 경조사 비용 집계
        double totalCost = byUserAndAcquaintanceNickname.stream().mapToDouble(AcEvent::getPayAmount).sum();

        return new GetAllAcEventsResponseDto((long) totalCost, sortedAcEvents);
    }

    private User getUser() {
        return this.userRepository.findById(SecurityUtil.getCurrentUserId())
            .orElseThrow(() -> new NotFoundUserException("일치하는 회원이 존재하지 않습니다."));
    }

    private MyEvent getMyEvent(Long id) {
        return this.myEventRepository.findById(id)
            .orElseThrow(() -> new NotFoundEventException("일치하는 경조사 정보가 존재하지 않습니다."));
    }

    private AcEvent getAcEvent(Long id) {
        return this.acEventRepository.findById(id)
            .orElseThrow(() -> new NotFoundEventException("일치하는 경조사 정보가 존재하지 않습니다,"));
    }

    private long calculateRemainedDay(MyEvent myEvent) {
        return ChronoUnit.DAYS.between(myEvent.getEventAt(), LocalDate.now());
    }

    private void checkRequest(CreateMyEventRequestDto request) {
        if (request.getEventCategory() == 4 && request.getMyEventName() == null) {
            throw new NeedEventNameException("경조사 종류가 기타인 경우에는 별도의 EventName 이 필요합니다.");
        } else if (request.getMyType() != 0 && request.getMyEventCharacterName() == null) {
            throw new NeedEventCharacterNameException("나의 경조사 타입이 나가 아닌 경우에는 별도의 경조사 주인공 이름이 필요합니다");
        } else if (request.getMyType() == 0 && request.getMyEventCharacterName() != null) {
            throw new RuntimeException("서버 에러");
        } else if (request.getEventCategory() != 4 && request.getMyEventName() != null) {
            throw new RuntimeException("서버 에러");
        }
    }

    private MyEventDetail getMyEventDetail(Long detailId) {
        return this.myEventDetailRepository.findById(detailId)
            .orElseThrow(() -> new NotFoundEventDetailException("일치하는 경조사 세부사항 정보가 존재하지 않습니다."));
    }
}