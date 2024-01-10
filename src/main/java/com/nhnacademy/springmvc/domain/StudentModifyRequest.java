package com.nhnacademy.springmvc.domain;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import lombok.Value;
import org.hibernate.validator.constraints.Length;

@Value
public class StudentModifyRequest {
    long id;
    @NotBlank
    String name;
    @Email
    String email;
    @Max(100)
    @Min(0)
    int score;
    @Length(max = 200)
    @NotBlank
    String comment;
}
