package com.example.howmuch.contant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
@Getter
public enum EventCategory {

    WEDDING(0, "결혼식"),
    FUNERAL(1, "장례식"),
    FIRST_BIRTHDAY(2, "돌잔치"),
    ETC(3, "기타");

    private final Integer value;
    private final String categoryName;

    public static EventCategory fromValue(Integer value) {
        for (EventCategory category : EventCategory.values()) {
            if (Objects.equals(category.value, value)) {
                return category;
            }
        }
        throw new IllegalArgumentException("유효하지 않은 값입니다.");
    }
}
