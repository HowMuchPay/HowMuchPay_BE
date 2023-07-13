package com.example.howmuch.domain.repository;

import com.example.howmuch.domain.entity.MyEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyEventRepository extends JpaRepository<MyEvent, Long> {
}
