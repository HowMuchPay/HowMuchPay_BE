package com.example.howmuch.domain.repository;

import com.example.howmuch.constant.EventCategory;
import com.example.howmuch.constant.MyType;
import com.example.howmuch.domain.entity.MyEvent;
import com.example.howmuch.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MyEventRepository extends JpaRepository<MyEvent, Long> {
    List<MyEvent> findAllByUserOrderByEventAtDesc(User user);

    List<MyEvent> findAllByUserAndMyTypeAndEventCategoryOrderByEventAtDesc(User user, MyType myType, EventCategory eventCategory);

    List<MyEvent> findByUserAndEventAt(User user, LocalDate eventDate);

    Optional<MyEvent> findFirstByUserAndEventAtGreaterThanEqualOrderByEventAtAsc(User user, LocalDate currentDate);

}