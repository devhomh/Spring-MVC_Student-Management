package com.nhnacademy.springmvc.repository;

import com.nhnacademy.springmvc.domain.Student;
import java.util.Map;

public interface StudentRepository {
    boolean exists(long id);

    Student getStudent(long id);

    Student register(long id, String name, String email, int score, String comment);

    void modify(Student student);

    Map<Long, Student> findAll();
}
