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
public class createNoticeRequestDto {

    @NotBlank
    @Size(max = 255)
    private String title;

    @NotBlank
    @Lob
    private String content;

    public Notice toEntity() {
        return Notice.builder()
                .title(title)
                .content(content)
                .build();
    }
}
