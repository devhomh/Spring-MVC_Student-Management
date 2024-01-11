package com.nhnacademy.springmvc.controller;

import com.nhnacademy.springmvc.domain.Student;
import com.nhnacademy.springmvc.domain.StudentModifyRequest;
import com.nhnacademy.springmvc.domain.StudentRegisterRequest;
import com.nhnacademy.springmvc.exception.StudentAlreadyExistsException;
import com.nhnacademy.springmvc.exception.StudentNotFoundException;
import com.nhnacademy.springmvc.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/students")
public class StudentRestController {
    private final StudentRepository studentRepository;

    public StudentRestController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<Student> getStudent(@PathVariable("studentId") long studentId){
        Student student = studentRepository.getStudent(studentId);

        return ResponseEntity.ok(student);
    }


    @PostMapping
    public ResponseEntity<String> registerStudent(@RequestBody StudentRegisterRequest student){
        studentRepository.register(student.getId(), student.getName(), student.getEmail(), student.getScore(), student.getComment());

        return ResponseEntity.status(HttpStatus.CREATED).body("Registerd");
    }

    @PutMapping(("/{studentId}"))
    public ResponseEntity<String> modifyStudent(@PathVariable("studentId") long studentId, @RequestBody StudentModifyRequest student){
        if(studentId == student.getId()){
            Student modified = Student.create(student.getId(), student.getName(), student.getEmail(), student.getScore(), student.getComment());
            studentRepository.modify(modified);
            return ResponseEntity.ok("Modified");
        }

        return ResponseEntity.badRequest().body("Recheck id value");
    }
    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<String> notFoundException(RuntimeException exception) {
        log.error("없는 학생 입니다. = {}", exception.getMessage());
        return ResponseEntity.notFound().build();
    }
    @ExceptionHandler(StudentAlreadyExistsException.class)
    public ResponseEntity<String> alreadyExistsException(RuntimeException exception) {
        String errorMsg = "Already Exist";
        log.error(errorMsg, exception);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMsg);
    }
}
