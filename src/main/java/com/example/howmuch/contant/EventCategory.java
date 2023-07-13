package com.example.howmuch.contant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum EventCategory {

    WEDDING("결혼식"),
    FUNERAL("장례식"),
    FIRST_BIRTHDAY("돌잔치"),
    ETC("기타");

    private final String categoryName;
}
