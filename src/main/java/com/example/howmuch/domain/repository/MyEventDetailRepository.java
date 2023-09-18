package com.example.howmuch.domain.repository;

import com.example.howmuch.domain.entity.MyEvent;
import com.example.howmuch.domain.entity.MyEventDetail;
import com.example.howmuch.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MyEventDetailRepository extends JpaRepository<MyEventDetail, Long> {

    // 나의 경조사 세부 사항 조회 (페치조인)
    @Query("select md from MyEventDetail md join fetch md.myEvent where md.user = :user")
    List<MyEventDetail> findAllByUserOrderByCreatedAtDesc(@Param("user") User user);
    // 나의 경조사 세부 사항 오름차순
    List<MyEventDetail> findAllByMyEventOrderByReceiveAmountAsc(MyEvent myEvent);

    // 나의 경조사 세부 사항 내림차순
    List<MyEventDetail> findAllByMyEventOrderByReceiveAmountDesc(MyEvent myEvent);


    List<MyEventDetail> findAllByMyEventAndAcquaintanceNicknameContainingIgnoreCase(MyEvent myEvent, String acquaintanceNickname);
}
