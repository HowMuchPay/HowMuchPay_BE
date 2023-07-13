package com.example.howmuch.service.event;

import com.example.howmuch.contant.EventCategory;
import com.example.howmuch.domain.entity.MyEvent;
import com.example.howmuch.domain.entity.User;
import com.example.howmuch.domain.repository.AcEventRepository;
import com.example.howmuch.domain.repository.MyEventRepository;
import com.example.howmuch.domain.repository.UserRepository;
import com.example.howmuch.dto.event.CreateMyEventRequestDto;
import com.example.howmuch.exception.user.NotFoundUserException;
import com.example.howmuch.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class EventService {
    private final UserRepository userRepository;
    private final MyEventRepository myEventRepository;
    private final AcEventRepository eventRepository;

    @Transactional
    public Long createMyEvent(CreateMyEventRequestDto request) {

        log.info(SecurityUtil.getCurrentUserId().toString());

        User user = this.userRepository.findById(SecurityUtil.getCurrentUserId())
                .orElseThrow(() -> new NotFoundUserException("일치하는 회원이 존재하지 않습니다."));

        return this.myEventRepository.save(
                        MyEvent.builder()
                                .eventAt(request.getEventAt())
                                .eventCategory(EventCategory.fromValue(request.getEventCategory()))
                                .user(user)
                                .build())
                .getId();
    }
}
