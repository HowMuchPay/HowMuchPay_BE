package com.example.howmuch.dto.event;

import com.example.howmuch.constant.AcType;
import com.example.howmuch.constant.EventCategory;
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
    @NotBlank(message = "지인의 이름은 필수 입력값 입니다.")
    @Size(max = 255)
    private String acName;

    @NotNull(message = "지인의 유형은 필수 입력값 입니다.")
    @Min(0)
    @Max(4)
    private Integer acType;

    @NotNull(message = "경조사 유형은 필수 입력값 입니다.")
    @Min(0)
    @Max(4)
    private Integer eventCategory;

    private long payAmount;

    @NotNull
    private LocalDate eventAt;

    private String eventTime; // 경조사 시각

    private String eventName; // eventCategory 가 etc 인 경우(4) !null

    /**
     * request json key 중 eventCategory 가 4이면 eventName 지정
     * request json key 중 eventCategory 가 4가 아니면 eventName null 지정
     **/

    public AcEvent toEntity(User user) {
        return AcEvent.builder()
                .eventAt(eventAt)
                .payAmount(payAmount)
                .eventCategory(EventCategory.fromValue(eventCategory))
                .acquaintanceType(AcType.fromValue(acType))
                .acquaintanceNickname(acName)
                .eventTime(eventTime)
                .eventName(eventName)
                .user(user)
                .build();
    }
}
