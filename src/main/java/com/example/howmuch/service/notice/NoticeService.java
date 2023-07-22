package com.example.howmuch.service.notice;

import com.example.howmuch.domain.repository.NoticeRepository;
import com.example.howmuch.dto.notice.createNoticeRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class NoticeService {

    private final NoticeRepository noticeRepository;

    @Transactional
    public Long createNotice(createNoticeRequestDto request) {
        return this.noticeRepository.save(request.toEntity())
                .getId();
    }
}
