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
import com.example.howmuch.exception.event.NotFoundEventDetailException;
import com.example.howmuch.exception.event.NotFoundEventException;
import com.example.howmuch.exception.user.NotFoundUserException;
import com.example.howmuch.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

        log.info(SecurityUtil.getCurrentUserId().toString());
        return this.myEventRepository.save(request.toEntity(getUser())).getId();
    }

    // 나의 모든 경조사 조회
    @Transactional(readOnly = true)
    public GetAllMyEventsResponseDto getAllMyEvents() {

        User user = getUser();
        Long totalReceiveAmount = user.getUserTotalReceiveAmount();

        List<GetAllMyEventsResponse> allMyEvents =
                this.myEventRepository.findAllByUser(user, Sort.by(Sort.Direction.DESC, "eventAt"))
                        .stream()
                        .map(MyEvent::of)
                        .toList();

        return new GetAllMyEventsResponseDto(totalReceiveAmount, allMyEvents);
    }

    // 나의 경조사 필터링 조회
    @Transactional(readOnly = true)
    public GetAllMyEventsResponseDto getAllMyEventsByFilter(String myTypes, String eventCategories) {

        List<String> myTypeList = List.of(myTypes.split(","));
        List<String> eventCategoryList = List.of(eventCategories.split(","));

        List<MyEvent> result = myTypeList.stream()
                .map(myTypeString -> MyType.fromValue(Integer.parseInt(myTypeString.trim())))
                .flatMap(myType -> eventCategoryList.stream()
                        .map(eventCategoryString -> EventCategory.fromValue(Integer.parseInt(eventCategoryString.trim())))
                        .flatMap(eventCategory -> this.myEventRepository.findAllByMyTypeAndEventCategoryOrderByEventAtDesc(myType, eventCategory).stream()))
                .toList();

        List<GetAllMyEventsResponse> res = result.stream()
                .map(MyEvent::of)
                .toList();

        return new GetAllMyEventsResponseDto(getUser().getUserTotalPayAmount(), res);
    }

    // 나의 경조사 지인으로부터 받은 금액 등록
    @Transactional
    public Long createMyEventDetail(Long id, CreateMyEventDetailRequestDto request) {

        MyEvent myEvent = getMyEvent(id);

        getUser().addTotalReceiveAmount(request.getReceiveAmount()); // 내가 받은 총 금액 추가
        myEvent.addReceiveAmount(request.getReceiveAmount()); // 해당 경조사 받은 금액 추가

        return this.myEventDetailRepository.save(request.toEntity(myEvent)).getId();
    }

    // 나의 경조사 지인으로부터 받은 금액 전체 조회
    @Transactional(readOnly = true)
    public List<GetAllMyEventDetailResponseDto> getAllMyEventDetails(Long id) {

        MyEvent myEvent = getMyEvent(id);
        return this.myEventDetailRepository.findAllByMyEvent(myEvent, Sort.by(Sort.Direction.DESC, "createdAt"))
                .stream()
                .map(MyEventDetail::from)
                .toList();
    }

    // 나의 경조사 지인으로부터 받은 금액 이름 조회
    @Transactional(readOnly = true)
    public List<GetAllMyEventDetailResponseDto> getAllMyEventDetailsByName(Long id, String name) {

        getMyEvent(id);
        return this.myEventDetailRepository.findAllByAcquaintanceNicknameContainingIgnoreCase(name)
                .stream()
                .map(MyEventDetail::from)
                .toList();
    }

    // 나의 경조사 삭제
    @Transactional
    public void deleteMyEvent(Long id) {
        MyEvent myEvent = getMyEvent(id);
        this.myEventRepository.delete(myEvent);
    }

    // 나의 경조사 세부사항 삭제
    @Transactional
    public void deleteMyEventDetail(Long eventId, Long detailId) {
        MyEvent myEvent = getMyEvent(eventId);
        MyEventDetail myEventDetail = this.myEventDetailRepository.findById(detailId)
                .orElseThrow(() -> new NotFoundEventDetailException("일치하는 경조사 세부사항 정보가 존재하지 않습니다."));

        this.myEventDetailRepository.delete(myEventDetail);
    }


    /*********/

    // 지인 경조사 등록
    @Transactional
    public Long createAcEvent(CreateAcEventRequestDto request) {
        log.info(SecurityUtil.getCurrentUserId().toString());
        return this.acEventRepository.save(request.toEntity(getUser())).getId();
    }

    // 지인 경조사 필터링 조회
    @Transactional(readOnly = true)
    public GetAllAcEventsResponseDto getAllAcEventsByFilter(String acTypes, String eventCategories) {

        List<String> acTypeList = List.of(acTypes.split(","));
        List<String> eventCategoryList = List.of(eventCategories.split(","));

        List<AcEvent> result = acTypeList.stream()
                .map(acTypeString -> AcType.fromValue(Integer.parseInt(acTypeString.trim())))
                .flatMap(acType -> eventCategoryList.stream()
                        .map(eventCategoryString -> EventCategory.fromValue(Integer.parseInt(eventCategoryString.trim())))
                        .flatMap(eventCategory -> this.acEventRepository.findAllByAcquaintanceTypeAndEventCategoryOrderByEventAtDesc(acType, eventCategory).stream()))
                .toList();

        List<GetAllAcEventsResponse> res = result.stream()
                .map(AcEvent::of)
                .toList();

        return new GetAllAcEventsResponseDto(getUser().getUserTotalPayAmount(), res);
    }

    // 지인 모든 경조사 조회
    @Transactional(readOnly = true)
    public GetAllAcEventsResponseDto getAllAcEvents() {

        User user = getUser();
        Long totalPayAmount = user.getUserTotalPayAmount();

        List<GetAllAcEventsResponse> allAcEvents
                = this.acEventRepository.findAllByUser(user, Sort.by(Sort.Direction.DESC, "eventAt"))
                .stream()
                .map(AcEvent::of)
                .toList();

        return new GetAllAcEventsResponseDto(totalPayAmount, allAcEvents);
    }

    private User getUser() {
        return this.userRepository.findById(SecurityUtil.getCurrentUserId())
                .orElseThrow(() -> new NotFoundUserException("일치하는 회원이 존재하지 않습니다."));
    }

    private MyEvent getMyEvent(Long id) {
        return this.myEventRepository.findById(id)
                .orElseThrow(() -> new NotFoundEventException("일치하는 경조사 정보가 존재하지 않습니다."));
    }
}
