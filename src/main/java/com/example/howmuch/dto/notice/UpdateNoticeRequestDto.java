package com.example.howmuch.dto.notice;

import lombok.*;

import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateNoticeRequestDto {

    @NotBlank
    @Size(max = 255)
    private String title;

    @NotBlank
    @Lob
    private String content;
}
