package com.example.howmuch.dto.event;

import com.example.howmuch.domain.entity.AcEvent;
import java.util.List;
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
public class GetAllAcNicknameResponseDto {

    List<GetAllAcNicknameResponse> names;


    public static GetAllAcNicknameResponseDto from(List<AcEvent> acEvents) {
        return new GetAllAcNicknameResponseDto(acEvents.stream()
            .map(GetAllAcNicknameResponse::from).toList());
    }
}
