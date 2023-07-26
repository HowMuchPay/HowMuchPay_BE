package com.example.howmuch.domain.entity;

import com.example.howmuch.domain.BaseTimeEntity;
import com.example.howmuch.dto.notice.GetAllNoticeResponseDto;
import com.example.howmuch.dto.notice.UpdateNoticeRequestDto;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Slf4j
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Notice extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Long id;

    private String title;
    private String content;


    public GetAllNoticeResponseDto from() {
        return GetAllNoticeResponseDto.builder()
                .title(title)
                .content(content)
                .updatedAt(getUpdatedAt().toLocalDate())
                .build();
    }

    public void updateNotice(UpdateNoticeRequestDto request) {
        this.title = request.getTitle();
        this.content = request.getContent();
    }
}
