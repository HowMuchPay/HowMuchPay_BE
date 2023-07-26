package com.example.howmuch.controller;

import com.example.howmuch.domain.BaseTimeEntity;
import com.example.howmuch.dto.notice.UpdateNoticeRequestDto;
import com.example.howmuch.dto.notice.createNoticeRequestDto;
import com.example.howmuch.service.notice.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/notice")
@RestController
public class AdminController extends BaseTimeEntity {
    private final NoticeService noticeService;

    @PostMapping
    public ResponseEntity<Long> createNotice(
            @Valid @RequestBody createNoticeRequestDto request
    ) {
        return new ResponseEntity<>(this.noticeService.createNotice(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateNotice(
            @PathVariable Long id,
            @Valid @RequestBody UpdateNoticeRequestDto request
    ) {
        this.noticeService.updateNotice(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotice(
            @PathVariable Long id
    ) {
        this.noticeService.deleteNotice(id);
        return ResponseEntity.ok().build();
    }
}
