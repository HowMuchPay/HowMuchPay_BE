package com.example.howmuch.dto.event;

import com.example.howmuch.contant.AcType;
import com.example.howmuch.contant.EventCategory;
import com.example.howmuch.domain.entity.AcEvent;
import com.example.howmuch.domain.entity.User;
import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateAcEventRequestDto {

    @NotBlank
    @Size(max = 255)
    private String acName;

    @Min(0)
    @Max(3)
    private Integer acType;

    @Min(0)
    @Max(3)
    private Integer eventCategory;

    private Long payAmount;

    @NotNull
    private LocalDate eventAt;

    public AcEvent toEntity(User user) {
        return AcEvent.builder()
                .eventAt(eventAt)
                .payAmount(payAmount)
                .eventCategory(EventCategory.fromValue(eventCategory))
                .acquaintanceType(AcType.fromValue(acType))
                .acquaintanceNickname(acName)
                .user(user)
                .build();
    }
}
