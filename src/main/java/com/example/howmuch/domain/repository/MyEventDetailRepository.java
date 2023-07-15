package com.example.howmuch.domain.repository;

import com.example.howmuch.domain.entity.MyEvent;
import com.example.howmuch.domain.entity.MyEventDetail;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MyEventDetailRepository extends JpaRepository<MyEventDetail, Long> {
    List<MyEventDetail> findAllByMyEvent(MyEvent myEvent, Sort sort);

    List<MyEventDetail> findAllByAcquaintanceNicknameContainingIgnoreCase(String acquaintanceNickname);
}
