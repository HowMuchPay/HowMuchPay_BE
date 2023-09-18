package com.example.howmuch.domain.repository;

import com.example.howmuch.constant.EventCategory;
import com.example.howmuch.constant.MyType;
import com.example.howmuch.domain.entity.MyEvent;
import com.example.howmuch.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MyEventRepository extends JpaRepository<MyEvent, Long> {
    List<MyEvent> findAllByUserOrderByEventAtDesc(User user);

    List<MyEvent> findAllByUserAndMyTypeAndEventCategoryOrderByEventAtDesc(User user, MyType myType, EventCategory eventCategory);

    List<MyEvent> findAllByEventAt(LocalDate eventAt);

    @Query("select m from MyEvent m where m.user = :user and year(m.eventAt) = :year and month(m.eventAt) = :month")
    List<MyEvent> findAllByUserAndYearAndMonth(User user, int year, int month);

    Optional<MyEvent> findFirstByUserAndEventAtGreaterThanEqualOrderByEventAtAsc(User user, LocalDate currentDate);


}
