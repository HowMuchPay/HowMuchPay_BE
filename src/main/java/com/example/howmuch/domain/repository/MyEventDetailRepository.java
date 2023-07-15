package com.example.howmuch.domain.repository;

import com.example.howmuch.domain.entity.MyEventDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyEventDetailRepository extends JpaRepository<MyEventDetail, Long> {
}
