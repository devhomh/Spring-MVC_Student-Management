package com.nhnacademy.springmvc.controller;

import com.nhnacademy.springmvc.domain.Student;
import com.nhnacademy.springmvc.exception.ValidationFailedException;
import com.nhnacademy.springmvc.repository.StudentRepository;
import com.nhnacademy.springmvc.validator.StudentValidator;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/student")
public class StudentController {
    private final StudentRepository studentRepository;
    private final StudentValidator validator;

    public StudentController(StudentRepository studentRepository, StudentValidator validator) {
        this.studentRepository = studentRepository;
        this.validator = validator;
    }

    @GetMapping("/{studentId}")
    public String getStudent(@PathVariable Long studentId,
                             Model model){
        Student student = studentRepository.getStudent(studentId);
        model.addAttribute("student", student);
        return "student";
    }

    @GetMapping(value = "/{studentId}", params = "hideScore")
    public String getStudentWithoutScore(@PathVariable Long studentId,
                                         @RequestParam(name = "hideScore") boolean hideOpt,
                                         ModelMap modelMap){
        Student student = studentRepository.getStudent(studentId);
        modelMap.addAttribute("student", student);
        return hideOpt ? "studentWithoutScore" : "student";
    }

    @GetMapping("/{studentId}/modify")
    public ModelAndView getStudentModify(@PathVariable Long studentId){
        Student student = studentRepository.getStudent(studentId);

        ModelAndView mav = new ModelAndView("studentRegister");
        mav.addObject("student", student);
        return mav;
    }

    @PostMapping("/{studentId}/modify")
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView modifyStudent(@PathVariable Long studentId,
                                      @Valid @ModelAttribute Student student,
                                      BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new ValidationFailedException(bindingResult);
        }
        studentRepository.modify(student);

        Student modifiedStudent = studentRepository.getStudent(studentId);

        ModelAndView mav = new ModelAndView("student");
        mav.addObject("student", modifiedStudent);

        return mav;
    }

    @InitBinder("student")
    protected void initBinder(WebDataBinder binder){
        binder.addValidators(validator);
    }

}
