package com.example.howmuch.dto.notice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class GetNoticeResponseDto {
    private String title;
    private String content;
    private LocalDate updatedAt;
}
