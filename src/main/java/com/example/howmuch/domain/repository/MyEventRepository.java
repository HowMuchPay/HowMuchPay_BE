package com.example.howmuch.domain.repository;

import com.example.howmuch.domain.entity.MyEvent;
import com.example.howmuch.domain.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MyEventRepository extends JpaRepository<MyEvent, Long> {
    List<MyEvent> findAllByUser(User user, Sort eventAt);
}
