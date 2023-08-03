package com.example.howmuch.domain.repository;

import com.example.howmuch.constant.EventCategory;
import com.example.howmuch.constant.MyType;
import com.example.howmuch.domain.entity.MyEvent;
import com.example.howmuch.domain.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface MyEventRepository extends JpaRepository<MyEvent, Long> {
    List<MyEvent> findAllByUser(User user, Sort eventAt);

    List<MyEvent> findAllByMyTypeAndEventCategoryOrderByEventAtDesc(MyType myType, EventCategory eventCategory);

    List<MyEvent> findAllByEventAt(LocalDate eventAt);

    @Query("select m from MyEvent m where year(m.eventAt) = :year and month(m.eventAt) = :month")
    List<MyEvent> findAllByYearAndMonth(int year, int month);
}
