package com.example.howmuch.domain.repository;

import com.example.howmuch.domain.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    @Query("select n from Notice n order by n.updatedAt DESC ")
    List<Notice> findAllNotices();
}
