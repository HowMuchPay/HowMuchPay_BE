package com.example.howmuch.dto.event;

import com.example.howmuch.domain.entity.AcEvent;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetAllAcNicknameResponseDto {

    private List<String> names;

    public static GetAllAcNicknameResponseDto from(List<AcEvent> names) {
        GetAllAcNicknameResponseDto response = new GetAllAcNicknameResponseDto();
        List<String> nameList = new ArrayList<>();

        for (AcEvent acEvent : names) {
            String name = acEvent.getAcquaintanceNickname();
            nameList.add(name);
        }
        response.setNames(nameList);
        return response;
    }
}
