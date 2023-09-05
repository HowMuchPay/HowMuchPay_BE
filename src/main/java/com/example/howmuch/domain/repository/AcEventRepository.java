package com.example.howmuch.domain.repository;

import com.example.howmuch.constant.AcType;
import com.example.howmuch.constant.EventCategory;
import com.example.howmuch.domain.entity.AcEvent;
import com.example.howmuch.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AcEventRepository extends JpaRepository<AcEvent, Long> {
    List<AcEvent> findAllByUserOrderByEventAtDesc(User user);

    List<AcEvent> findAllByAcquaintanceType(AcType type);

    List<AcEvent> findAllByAcquaintanceTypeAndEventCategoryOrderByEventAtDesc(AcType acType, EventCategory category);

    List<AcEvent> findAllByEventAt(LocalDate eventAt);

    Optional<AcEvent> findFirstByUserAndEventAtGreaterThanOrderByEventAtAsc(User user, LocalDate currentDate);

    @Query("SELECT SUM(a.payAmount) FROM AcEvent a WHERE a.user = ?1 AND a.eventCategory = ?2 AND a.acquaintanceType = ?3")
    Optional<Long> sumPayAmountByUserAndCategoryAndType(User user, EventCategory eventCategory, AcType acType);


    @Query("select a from AcEvent a where year(a.eventAt) = :year and month(a.eventAt) = :month")
    List<AcEvent> findAllByYearAndMonth(int year, int month);
}
