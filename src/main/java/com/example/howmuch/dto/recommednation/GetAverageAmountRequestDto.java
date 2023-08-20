package com.example.howmuch.dto.recommednation;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetAverageAmountRequestDto {
    //나이 20~ 70대
    @NotNull
    private Integer ageGroup;

    //연수입 3000~ 8000
    @NotNull
    private Integer annualIncome;

    @NotNull
    @Min(0)
    @Max(4)
    private Integer eventCategory;

    //관계분류
    @NotNull
    @Min(0)
    @Max(4)
    private Integer acquaintanceType;

    //친밀도
    @NotNull
    @Min(1)
    @Max(5)
    private Integer intimacyLevel;
}
