package com.example.howmuch.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
@Getter
public enum EventCategory {

    /**
     * 0번: 결혼, 1번: 상, 2번: 생일, 3번: 돌잔치, 4: 기타
     */

    WEDDING(0, "결혼식"),
    FUNERAL(1, "장례식"),
    BIRTHDAY(2, "생일"),
    FIRST_BIRTHDAY(3, "돌잔치"),
    ETC(4, "기타");

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
