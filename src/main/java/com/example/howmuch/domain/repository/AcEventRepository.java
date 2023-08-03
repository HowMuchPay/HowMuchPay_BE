package com.example.howmuch.domain.repository;

import com.example.howmuch.constant.AcType;
import com.example.howmuch.constant.EventCategory;
import com.example.howmuch.domain.entity.AcEvent;
import com.example.howmuch.domain.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface AcEventRepository extends JpaRepository<AcEvent, Long> {
    List<AcEvent> findAllByUser(User user, Sort sort);

    List<AcEvent> findAllByAcquaintanceType(AcType type);

    List<AcEvent> findAllByAcquaintanceTypeAndEventCategoryOrderByEventAtDesc(AcType acType, EventCategory category);

    List<AcEvent> findAllByEventAt(LocalDate eventAt);

    @Query("select a from AcEvent a where year(a.eventAt) = :year and month(a.eventAt) = :month")
    List<AcEvent> findAllByYearAndMonth(int year, int month);
}
