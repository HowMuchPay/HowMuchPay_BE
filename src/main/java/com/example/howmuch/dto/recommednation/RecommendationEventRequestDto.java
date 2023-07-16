package com.example.howmuch.dto.recommednation;

import com.example.howmuch.contant.AcType;
import com.example.howmuch.contant.EventCategory;
import com.example.howmuch.domain.entity.MyEvent;
import com.example.howmuch.domain.entity.User;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class RecommendationEventRequestDto {
    private EventCategory eventCategory;
    private AcType acquaintanceType;
    private long payAmount;
    private List<Boolean> intimacyAnswers;

}