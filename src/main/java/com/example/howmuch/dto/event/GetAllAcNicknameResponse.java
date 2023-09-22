package com.example.howmuch.dto.event;

import com.example.howmuch.domain.entity.AcEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAllAcNicknameResponse {

    String name;

    public static GetAllAcNicknameResponse from(AcEvent acEvent){
        return new GetAllAcNicknameResponse(acEvent.getAcquaintanceNickname());
    }

}
