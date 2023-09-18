package com.example.howmuch.dto.event;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAcEventRequestDto {

    private String acName;

    private Integer acType;

    private Integer eventCategory;

    private Long payAmount;

    private LocalDate eventAt;

    private String eventTime;

    private String eventName;
}
