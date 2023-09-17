package com.example.howmuch.dto.notice;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAllNoticeResponseDto {
    private Long id;
    private String title;
    private String content;
    private LocalDate updatedAt;
}
