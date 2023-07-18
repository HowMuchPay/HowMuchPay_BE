package com.example.howmuch.contant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
@Getter
public enum AcType {

    /**
     * 0: 가족 ,1: 친구 ,2: 동료,3: 친척,4: 기타
     */

    FAMILY(0, "가족"),
    FRIEND(1, "친구"),
    COLLEAGUE(2, "동료"),
    RELATIVES(3, "친척"),
    ETC(4, "기타");

    private final Integer value;
    private final String typeName;

    public static AcType fromValue(Integer value) {
        for (AcType acType : AcType.values()) {
            if (Objects.equals(acType.value, value)) {
                return acType;
            }
        }
        throw new IllegalArgumentException("유효하지 않은 값입니다.");
    }
}
