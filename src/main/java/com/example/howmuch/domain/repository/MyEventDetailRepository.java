package com.example.howmuch.domain.repository;

import com.example.howmuch.domain.entity.MyEvent;
import com.example.howmuch.domain.entity.MyEventDetail;
import com.example.howmuch.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MyEventDetailRepository extends JpaRepository<MyEventDetail, Long> {

    // 나의 경조사 세부 사항 조회 (페치조인)
    @Query("select md from MyEventDetail md join fetch md.myEvent where md.user = :user")
    List<MyEventDetail> findAllByUserOrderByCreatedAtDesc(@Param("user") User user);

    List<MyEventDetail> findAllByUser(User user);
    
    List<MyEventDetail> findAllByMyEventAndAcquaintanceNicknameContainingIgnoreCase(MyEvent myEvent, String acquaintanceNickname);

    List<MyEventDetail> findByUserAndAcquaintanceNickname(User user, String acquaintanceNickName);
}
