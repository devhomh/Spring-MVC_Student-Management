package com.nhnacademy.springmvc.domain;

import lombok.Getter;

@Getter
public class User {
    private final String id;

    private final String password;

    public static User create(String id, String password) {
        return new User(id, password);
    }

    private User(String id, String password) {
        this.id = id;
        this.password = password;
    }

}
