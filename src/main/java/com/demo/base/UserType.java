package com.demo.base;

public enum UserType {
    USER(1),
    ADMIN(2);

    public static final UserType[] ALL = { USER, ADMIN };

    private int code;

    UserType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
