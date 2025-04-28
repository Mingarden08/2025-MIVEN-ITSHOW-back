package com.bookmoment.api.constant;

import lombok.Getter;

@Getter
public enum Role {
    USER("USER", "일반회원"),
    ADMIN("ADMIN", "관리자");

    private String code;
    private String name;

    Role(String code, String name) {
        this.code = code;
        this.name = name;
    }
}