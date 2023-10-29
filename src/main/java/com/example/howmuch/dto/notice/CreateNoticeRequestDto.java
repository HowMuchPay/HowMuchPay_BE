package com.example.howmuch.dto.notice;

import com.example.howmuch.domain.entity.Notice;
import lombok.*;

import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateNoticeRequestDto {

    @NotBlank(message = "제목은 필수 입력값 입니다.")
    @Size(max = 255)
    private String title;

    @NotBlank(message = "내용은 필수 입력값 입니다.")
    @Lob
    private String content;

    public Notice toEntity() {
        return Notice.builder()
                .title(title)
                .content(content)
                .build();
    }
}
