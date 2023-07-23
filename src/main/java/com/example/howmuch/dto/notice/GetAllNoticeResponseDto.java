package com.example.howmuch.dto.notice;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAllNoticeResponseDto {
    private String title;
    private String content;
}
