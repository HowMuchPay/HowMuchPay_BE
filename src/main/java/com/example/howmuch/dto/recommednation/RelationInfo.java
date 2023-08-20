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
public class RelationInfo {
    //경조사 종류
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

    //값 (경조사비 지불)
    @NotNull
    private Integer payAmount;
}
