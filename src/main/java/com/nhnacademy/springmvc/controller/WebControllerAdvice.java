package com.nhnacademy.springmvc.controller;

import com.nhnacademy.springmvc.exception.StudentAlreadyExistsException;
import com.nhnacademy.springmvc.exception.ValidationFailedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class WebControllerAdvice {
    @ExceptionHandler({StudentAlreadyExistsException.class, ValidationFailedException.class})
    public String handleException(Exception exception, Model model) {
        log.error("에러 발생", exception);

        model.addAttribute("exception", exception);
        return "thymeleaf/error";
    }
}
