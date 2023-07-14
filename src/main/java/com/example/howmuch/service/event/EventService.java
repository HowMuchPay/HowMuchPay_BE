package com.example.howmuch.service.event;

import com.example.howmuch.domain.entity.MyEvent;
import com.example.howmuch.domain.entity.User;
import com.example.howmuch.domain.repository.AcEventRepository;
import com.example.howmuch.domain.repository.MyEventRepository;
import com.example.howmuch.domain.repository.UserRepository;
import com.example.howmuch.dto.event.CreateAcEventRequestDto;
import com.example.howmuch.dto.event.CreateMyEventRequestDto;
import com.example.howmuch.dto.event.GetAllMyEventsResponse;
import com.example.howmuch.dto.event.GetAllMyEventsResponseDto;
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

        log.info(SecurityUtil.getCurrentUserId().toString());
        User user = getUser();

        List<MyEvent> myEvents
                = this.myEventRepository.findAllByUser(user, Sort.by(Sort.Direction.DESC, "eventAt"));

        Long totalReceiveAmount = this.userRepository.findById(user.getId())
                .orElseThrow(() -> new NotFoundUserException("일치하는 회원이 존재하지 않습니다."))
                .getTotalRcv();

        List<GetAllMyEventsResponse> allMyEvents = myEvents.stream()
                .map(MyEvent::of)
                .toList();

        return new GetAllMyEventsResponseDto(totalReceiveAmount, allMyEvents);
    }

    @Transactional
    public Long createAcEvent(CreateAcEventRequestDto request) {
        log.info(SecurityUtil.getCurrentUserId().toString());
        return this.acEventRepository.save(request.toEntity(getUser())).getId();
    }

    private User getUser() {
        return this.userRepository.findById(SecurityUtil.getCurrentUserId())
                .orElseThrow(() -> new NotFoundUserException("일치하는 회원이 존재하지 않습니다."));
    }
}
