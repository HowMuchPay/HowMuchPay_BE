package com.example.howmuch.domain.repository;

import com.example.howmuch.domain.entity.MyEvent;
import com.example.howmuch.domain.entity.MyEventDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MyEventDetailRepository extends JpaRepository<MyEventDetail, Long> {

    // 나의 경조사 세부 사항 오름차순
    List<MyEventDetail> findAllByMyEventOrderByReceiveAmountAsc(MyEvent myEvent);

    // 나의 경조사 세부 사항 내림차순
    List<MyEventDetail> findAllByMyEventOrderByReceiveAmountDesc(MyEvent myEvent);

    List<MyEventDetail> findAllByAcquaintanceNicknameContainingIgnoreCase(String acquaintanceNickname);
}
