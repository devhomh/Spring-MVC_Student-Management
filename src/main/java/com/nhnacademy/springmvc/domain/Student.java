package com.nhnacademy.springmvc.domain;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class Student {
    private long id;
    @NotBlank
    private String name;
    @Email
    private String email;
    @Max(100)
    private int score;
    @Length(max = 200)
    @NotBlank
    private String comment;

    private Student(long id, String name, String email, int score, String comment) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.score = score;
        this.comment = comment;
    }

    public static Student create(long id, String name, String email, int score, String comment){
        return new Student(id, name, email, score, comment);
    }
}
