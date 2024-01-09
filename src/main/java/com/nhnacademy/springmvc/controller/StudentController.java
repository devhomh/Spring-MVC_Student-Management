package com.nhnacademy.springmvc.controller;

import com.nhnacademy.springmvc.domain.Student;
import com.nhnacademy.springmvc.repository.StudentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/student")
public class StudentController {
    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
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

}
