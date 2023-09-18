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

    List<AcEvent> findAllByUserAndAcquaintanceTypeAndEventCategoryOrderByEventAtDesc(User user, AcType acType, EventCategory category);

    List<AcEvent> findAllByEventAt(LocalDate eventAt);

    @Query("SELECT ae FROM AcEvent ae WHERE ae.user = :user AND ae.acquaintanceNickname LIKE %:acquaintanceName%")
    List<AcEvent> findByUserAndAcquaintanceNickname(User user, String acquaintanceName);

    Optional<AcEvent> findFirstByUserAndEventAtGreaterThanEqualOrderByEventAtAsc(User user, LocalDate currentDate);

    @Query("SELECT SUM(a.payAmount) FROM AcEvent a WHERE a.user = ?1 AND a.eventCategory = ?2 AND a.acquaintanceType = ?3")
    Optional<Long> sumPayAmountByUserAndCategoryAndType(User user, EventCategory eventCategory, AcType acType);


    @Query("select a from AcEvent a where a.user = :user and year(a.eventAt) = :year and month(a.eventAt) = :month")
    List<AcEvent> findAllByUserAndYearAndMonth(User user, int year, int month);
}
