package com.nhnacademy.springmvc.repository;

import com.nhnacademy.springmvc.domain.Student;
import com.nhnacademy.springmvc.exception.StudentAlreadyExistsException;
import com.nhnacademy.springmvc.exception.StudentNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class StudentRepositoryImpl implements StudentRepository{
    private final Map<Long, Student> studentMap = new HashMap<>();

    @Override
    public boolean exists(long id) {
        return studentMap.containsKey(id);
    }

    @Override
    public Student getStudent(long id) {
        if(exists(id)){
            return studentMap.get(id);
        }
        throw new StudentNotFoundException();
    }

    @Override
    public Student register(long id, String name, String email, int score, String comment) {
        if(exists(id)){
            throw new StudentAlreadyExistsException();
        }

        Student student = Student.create(id, name, email, score, comment);
        studentMap.put(id, student);
        return student;
    }

    @Override
    public void modify(Student student) {
        if(!exists(student.getId())){
            throw new StudentNotFoundException();
        }
        Student oldStudent = studentMap.get(student.getId());
        studentMap.replace(student.getId(), oldStudent, student);
    }

    @Override
    public Map<Long, Student> findAll() {
        return studentMap;
    }
}
