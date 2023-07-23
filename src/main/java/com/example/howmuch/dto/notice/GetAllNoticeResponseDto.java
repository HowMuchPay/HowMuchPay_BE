package com.example.howmuch.dto.notice;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAllNoticeResponseDto {
    private String title;
    private String content;
    private LocalDate updatedAt;
}
