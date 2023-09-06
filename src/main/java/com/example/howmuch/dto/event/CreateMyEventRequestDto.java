package com.example.howmuch.dto.event;

import com.example.howmuch.constant.EventCategory;
import com.example.howmuch.constant.MyType;
import com.example.howmuch.domain.entity.MyEvent;
import com.example.howmuch.domain.entity.User;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateMyEventRequestDto {

    /**
     * 1. EventCategory 에서 기타 선택 시 경조사명을 자유롭게 입력하는 화면 등장
     */
    @NotNull
    private LocalDate eventAt;

    @NotNull
    @Min(0)
    @Max(2)
    private Integer myType;

    @NotNull
    @Min(0)
    @Max(4)
    private Integer eventCategory;

    @Size(max = 255)
    private String myEventName; // eventCategory 가 etc 인 경우 !null

    @Size(max = 255)
    private String myEventCharacterName; // myType 이 '나' 가 아닌 경우 !null

    private String eventTime; // 경조사 시각


    public MyEvent toEntity(User user) {
        return MyEvent.builder()
                .eventAt(eventAt)
                .totalReceiveAmount(0L)
                .myType(MyType.fromValue(myType))
                .eventCategory(EventCategory.fromValue(eventCategory))
                .myEventName(myEventName)
                .myEventCharacterName(myEventCharacterName == null ? "나" : myEventCharacterName)
                .eventTime(eventTime)
                .user(user)
                .build();
    }
}
