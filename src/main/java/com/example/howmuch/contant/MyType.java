package com.example.howmuch.contant;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
@Getter
public enum MyType {

    /**
     * 0: 나의, 1: 부모의, 2:가족의, 3:자녀의
     */

    MY(0, "나의"),
    PARENT(1, "부모의"),
    FAMILY(2, "가족의"),
    CHILD(3, "자녀의");

    private final Integer value;
    private final String typeName;

    public static MyType fromValue(Integer value) {
        for (MyType myType : MyType.values()) {
            if (Objects.equals(myType.value, value)) {
                return myType;
            }
        }
        throw new IllegalArgumentException("유효하지 않은 값입니다.");
    }
}
