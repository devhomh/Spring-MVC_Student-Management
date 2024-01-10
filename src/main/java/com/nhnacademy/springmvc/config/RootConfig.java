package com.nhnacademy.springmvc.config;

import com.nhnacademy.springmvc.Base;
import com.nhnacademy.springmvc.repository.StudentRepository;
import com.nhnacademy.springmvc.repository.StudentRepositoryImpl;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;

@ComponentScan(basePackageClasses = Base.class,
        excludeFilters = { @ComponentScan.Filter(Controller.class)})
public class RootConfig {
    @Bean
    public StudentRepository studentRepository(){
        StudentRepository studentRepository = new StudentRepositoryImpl();
        studentRepository.register(1, "이진우", "jinwoo7654@naver.com", 100, "Best");
        studentRepository.register(2, "김진우", "jinwoo1234@naver.com", 80, "Normal");
        studentRepository.register(3, "박진우", "jinwoo5678@naver.com", 90, "Good");

        return studentRepository;
    }

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setBasename("message");

        return messageSource;
    }
}
