package com.example.howmuch.contant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum AcType {

    FAMILY(0, "가족"),
    FRIEND(1, "친구"),
    COLLEAGUE(2, "동료"),
    ETC(3, "기타");

    private final Integer value;
    private final String typeName;

}
