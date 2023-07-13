package com.example.howmuch.contant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum AcType {

    FRIEND("친구"),
    FAMILY("가족"),
    COLLEAGUE("동료"),
    ETC("기타");

    private final String typeName;

}
