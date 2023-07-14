package com.example.howmuch.service.event;

import com.example.howmuch.contant.AcType;
import com.example.howmuch.contant.EventCategory;
import com.example.howmuch.domain.entity.AcEvent;
import com.example.howmuch.domain.entity.MyEvent;
import com.example.howmuch.domain.entity.User;
import com.example.howmuch.domain.repository.AcEventRepository;
import com.example.howmuch.domain.repository.MyEventRepository;
import com.example.howmuch.domain.repository.UserRepository;
import com.example.howmuch.dto.event.*;
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

    @Transactional
    public Long createMyEvent(CreateMyEventRequestDto request) {

        log.info(SecurityUtil.getCurrentUserId().toString());
        return this.myEventRepository.save(request.toEntity(getUser())).getId();
    }

    @Transactional(readOnly = true)
    public GetAllMyEventsResponseDto getAllMyEvents() {

        User user = getUser();
        Long totalReceiveAmount = user.getTotalRcv();

        List<GetAllMyEventsResponse> allMyEvents =
                this.myEventRepository.findAllByUser(user, Sort.by(Sort.Direction.DESC, "eventAt"))
                        .stream()
                        .map(MyEvent::of)
                        .toList();

        return new GetAllMyEventsResponseDto(totalReceiveAmount, allMyEvents);
    }


    @Transactional(readOnly = true)
    public GetAllAcEventsResponseDto getAllMyEventsByFilter(String acTypeList, String eventCategoryList) {
        List<String> acTypes = List.of(acTypeList.split(","));
        List<String> eventCategories = List.of(eventCategoryList.split(","));

        List<AcEvent> result = acTypes.stream()
                .map(acTypeString -> AcType.fromValue(Integer.parseInt(acTypeString.trim())))
                .flatMap(acType -> eventCategories.stream()
                        .map(eventCategoryString -> EventCategory.fromValue(Integer.parseInt(eventCategoryString.trim())))
                        .flatMap(eventCategory -> this.acEventRepository.findAllByAcquaintanceTypeAndEventCategory(acType, eventCategory).stream()))
                .toList();

        List<GetAllAcEventsResponse> res = result.stream()
                .map(AcEvent::of)
                .toList();

        return new GetAllAcEventsResponseDto(getUser().getTotalPay(), res);
    }

    /*********/

    @Transactional
    public Long createAcEvent(CreateAcEventRequestDto request) {
        log.info(SecurityUtil.getCurrentUserId().toString());
        return this.acEventRepository.save(request.toEntity(getUser())).getId();
    }

    @Transactional(readOnly = true)
    public GetAllAcEventsResponseDto getAllAcEvents() {

        User user = getUser();
        Long totalPayAmount = user.getTotalPay();

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
}
