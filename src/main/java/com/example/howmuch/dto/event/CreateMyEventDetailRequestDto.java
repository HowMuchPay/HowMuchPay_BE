package com.example.howmuch.dto.event;

import com.example.howmuch.domain.entity.MyEvent;
import com.example.howmuch.domain.entity.MyEventDetail;
import com.example.howmuch.domain.entity.User;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateMyEventDetailRequestDto {

    @NotBlank(message = "지인의 별칭은 필수 입력값 입니다.")
    @Size(max = 255)
    private String acquaintanceNickname;

    @NotNull(message = "받은 금액은 필수 입력값 입니다.")
    private long receiveAmount;

    public MyEventDetail toEntity(MyEvent myEvent, User user) {
        return MyEventDetail.builder()
                .user(user)
                .acquaintanceNickname(acquaintanceNickname)
                .receiveAmount(receiveAmount)
                .myEvent(myEvent)
                .build();
    }
}
