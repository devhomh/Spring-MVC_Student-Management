package com.nhnacademy.springmvc.controller;

import com.nhnacademy.springmvc.exception.StudentAlreadyExistsException;
import com.nhnacademy.springmvc.exception.StudentNotFoundException;
import com.nhnacademy.springmvc.exception.ValidationFailedException;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@ControllerAdvice
public class WebControllerAdvice {
    @ExceptionHandler(StudentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView notFoundException(RuntimeException exception) {
        log.error("없는 학생 입니다.", exception);
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("exception", exception);

        return mav;
    }

    @ExceptionHandler({StudentAlreadyExistsException.class, ValidationFailedException.class})
    public String handleException(Exception exception, Model model) {
        log.error("에러 발생", exception);

        model.addAttribute("exception", exception);
        return "error";
    }
}
