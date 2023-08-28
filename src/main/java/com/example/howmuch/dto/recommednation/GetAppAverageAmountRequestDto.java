package com.example.howmuch.dto.recommednation;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetAppAverageAmountRequestDto {

    @NotNull
    @Min(0)
    @Max(4)
    private Integer eventCategory;

    @NotNull
    @Min(0)
    @Max(4)
    private Integer acquaintanceType;

    @NotNull
    private List<Boolean> intimacyAnswers;

    @NotNull
    private int ageGroup;
    @NotNull
    private int annualIncome;
}
