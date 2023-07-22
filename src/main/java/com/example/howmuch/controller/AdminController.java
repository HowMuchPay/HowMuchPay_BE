package com.example.howmuch.controller;

import com.example.howmuch.domain.BaseTimeEntity;
import com.example.howmuch.dto.notice.createNoticeRequestDto;
import com.example.howmuch.service.notice.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin")
@RestController
public class AdminController extends BaseTimeEntity {
    private final NoticeService noticeService;

    @PostMapping
    public ResponseEntity<Long> createNotice(
            @Valid @RequestBody createNoticeRequestDto request
    ) {
        return new ResponseEntity<>(this.noticeService.createNotice(request), HttpStatus.CREATED);
    }
}
