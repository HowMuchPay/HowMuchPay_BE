package com.example.howmuch.dto.recommednation;

import com.example.howmuch.contant.AcType;
import com.example.howmuch.contant.EventCategory;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CalculateAverageAmountRequestDto {

    @NotNull
    private Integer eventCategory;

    @NotNull
    private AcType acquaintanceType;

    @NotNull
    private List<Boolean> intimacyAnswers;
}
