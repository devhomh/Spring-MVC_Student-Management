package com.nhnacademy.springmvc.validator;

import com.nhnacademy.springmvc.domain.Student;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class StudentValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Student.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "", "name is empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "comment", "", "comment is empty");

        Student student = (Student) target;

        String comment = student.getComment();
        if(comment.length() > 200){
            errors.rejectValue("comment", "", "200 자를 넘으면 안됩니다.");
        }

        int score = student.getScore();
        if(score < 0 || score > 100){
            errors.rejectValue("score", "", "점수는 0점에서 100점 사이여야 합니다.");
        }
    }
}
