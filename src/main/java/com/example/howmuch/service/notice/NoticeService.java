package com.example.howmuch.service.notice;

import com.example.howmuch.domain.entity.Notice;
import com.example.howmuch.domain.repository.NoticeRepository;
import com.example.howmuch.dto.notice.GetAllNoticeResponseDto;
import com.example.howmuch.dto.notice.createNoticeRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    @Transactional(readOnly = true)
    public List<GetAllNoticeResponseDto> getAllNotices() {
        return this.noticeRepository.findAllNotices()
                .stream()
                .map(Notice::from)
                .collect(Collectors.toList());
    }
}
