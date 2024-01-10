package com.nhnacademy.springmvc.controller;

import com.nhnacademy.springmvc.domain.Student;
import com.nhnacademy.springmvc.exception.StudentNotFoundException;
import com.nhnacademy.springmvc.exception.ValidationFailedException;
import com.nhnacademy.springmvc.repository.StudentRepository;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequestMapping("/student")
public class StudentController {
    private final StudentRepository studentRepository;
    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @ModelAttribute("foundStudent")
    public Student findStudent(@PathVariable("studentId") Long studentId){
        return studentRepository.getStudent(studentId);
    }

    @GetMapping("/{studentId}")
    public String getStudent(@ModelAttribute("foundStudent") Student student,
                             Model model){
        model.addAttribute("student", student);
        return "thymeleaf/student";
    }

    @GetMapping(value = "/{studentId}", params = "hideScore")
    public String getStudentWithoutScore(@ModelAttribute("foundStudent") Student student,
                                         @RequestParam(name = "hideScore") boolean hideOpt,
                                         ModelMap modelMap){
        modelMap.addAttribute("student", student);
        return hideOpt ? "thymeleaf/studentWithoutScore" : "thymeleaf/student";
    }

    @GetMapping("/{studentId}/modify")
    public ModelAndView getStudentModify(@PathVariable Long studentId){
        Student student = studentRepository.getStudent(studentId);

        ModelAndView mav = new ModelAndView("thymeleaf/studentRegister");
        mav.addObject("student", student);
        return mav;
    }

    @PostMapping("/{studentId}/modify")
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView modifyStudent(@Valid @ModelAttribute Student student,
                                      BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new ValidationFailedException(bindingResult);
        }
        studentRepository.modify(student);

        ModelAndView mav = new ModelAndView("thymeleaf/student");
        mav.addObject("student", student);

        return mav;
    }

    @ExceptionHandler(StudentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView notFoundException(RuntimeException exception) {
        log.error("없는 학생 입니다.", exception);
        ModelAndView mav = new ModelAndView("thymeleaf/error");
        mav.addObject("exception", exception);

        return mav;
    }
}
