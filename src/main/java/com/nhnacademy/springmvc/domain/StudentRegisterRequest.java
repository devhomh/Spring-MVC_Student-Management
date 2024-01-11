package com.nhnacademy.springmvc.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class StudentRegisterRequest {
    long id;
    String name;
    String email;
    int score;
    String comment;
}
