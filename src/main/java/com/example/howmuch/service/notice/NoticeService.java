package com.example.howmuch.service.notice;

import com.example.howmuch.domain.entity.Notice;
import com.example.howmuch.domain.repository.NoticeRepository;
import com.example.howmuch.dto.notice.GetAllNoticeResponseDto;
import com.example.howmuch.dto.notice.GetNoticeResponseDto;
import com.example.howmuch.dto.notice.UpdateNoticeRequestDto;
import com.example.howmuch.dto.notice.createNoticeRequestDto;
import com.example.howmuch.exception.notice.NotFoundNoticeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class NoticeService {
    private final NoticeRepository noticeRepository;

    /* 공지사항 등록 */
    @Transactional
    public Long createNotice(createNoticeRequestDto request) {
        return this.noticeRepository.save(request.toEntity())
                .getId();
    }

    /* 공지사항 전체 조회 */
    @Transactional(readOnly = true)
    public List<GetAllNoticeResponseDto> getAllNotices() {
        return this.noticeRepository.findAllNotices()
                .stream()
                .sorted(Comparator.comparing(Notice::getUpdatedAt).reversed()) // 수정 날짜 기준 내림차순
                .map(Notice::toGetAllNoticeResponseDto)
                .collect(Collectors.toList());
    }

    /* 공지사항 수정 */
    @Transactional
    public void updateNotice(Long id, UpdateNoticeRequestDto request) {
        Notice notice = this.getNotice(id);
        notice.updateNotice(request);
    }

    /* 공지사항 삭제 */
    @Transactional
    public void deleteNotice(Long id) {
        Notice notice = this.getNotice(id);
        this.noticeRepository.delete(notice);
    }

    /* 공지사항 상세 조회 */
    @Transactional(readOnly = true)
    public GetNoticeResponseDto getNoticeById(Long id) {
        return this.getNotice(id).toGetNoticeResponseDto();
    }

    private Notice getNotice(Long id) {
        return this.noticeRepository.findById(id)
                .orElseThrow(() -> new NotFoundNoticeException("일치하는 공지사항 정보가 존재하지 않습니다."));
    }
}
