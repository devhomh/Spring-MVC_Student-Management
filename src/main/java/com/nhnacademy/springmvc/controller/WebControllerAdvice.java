package com.nhnacademy.springmvc.controller;

import com.nhnacademy.springmvc.exception.StudentAlreadyExistsException;
import com.nhnacademy.springmvc.exception.StudentNotFoundException;
import com.nhnacademy.springmvc.exception.ValidationFailedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class WebControllerAdvice {
    @ExceptionHandler({StudentAlreadyExistsException.class, ValidationFailedException.class})
    public String handleException(Exception exception, Model model) {
        log.error("에러 발생", exception);

        model.addAttribute("exception", exception);
        return "error";
    }

    @ExceptionHandler({StudentNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String notFoundException(StudentNotFoundException exception, Model model){
        log.error("없는 학생 입니다.", exception);

        model.addAttribute("exception", exception);
        return "error";
    }

}
