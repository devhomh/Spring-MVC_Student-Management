package com.nhnacademy.springmvc.controller;

import com.nhnacademy.springmvc.domain.Student;
import com.nhnacademy.springmvc.exception.ValidationFailedException;
import com.nhnacademy.springmvc.repository.StudentRepository;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/student/register")
public class StudentRegisterController {
    private final StudentRepository studentRepository;

    public StudentRegisterController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping
    public String studentRegisterForm(){
        return "thymeleaf/studentRegister";
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ModelAndView registerStudent(@Valid @ModelAttribute Student student,
                                        BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new ValidationFailedException(bindingResult);
        }
        Student newStudent = studentRepository.register(student.getId(),
                                                        student.getName(),
                                                        student.getEmail(),
                                                        student.getScore(),
                                                        student.getComment());

        ModelAndView mav = new ModelAndView("thymeleaf/student");
        mav.addObject("student", newStudent);

        return mav;
    }
}
