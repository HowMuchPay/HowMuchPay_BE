package com.example.howmuch.domain.repository;

import com.example.howmuch.domain.entity.AcEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcEventRepository extends JpaRepository<AcEvent, Long> {
}
