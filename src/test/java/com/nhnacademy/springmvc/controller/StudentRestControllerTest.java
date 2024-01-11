package com.nhnacademy.springmvc.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.springmvc.domain.Student;
import com.nhnacademy.springmvc.exception.StudentAlreadyExistsException;
import com.nhnacademy.springmvc.exception.StudentNotFoundException;
import com.nhnacademy.springmvc.repository.StudentRepository;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

@Slf4j
class StudentRestControllerTest {
    @Mock
    private StudentRepository studentRepository;
    private MockMvc mockMvc;
    private final Student testStudent =  Student.create(10, "이진우", "jinwoo7654@naver.com", 100, "Good");;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(new StudentRestController(studentRepository))
                .addFilter(new CharacterEncodingFilter("UTF-8"))
                .defaultResponseCharacterEncoding(StandardCharsets.UTF_8)
                .build();
    }

    @Test
    void getStudentTest() throws Exception{
        when(studentRepository.getStudent(10)).thenReturn(testStudent);

        MvcResult mvcResult = mockMvc.perform(get("/students/{studentId}", 10)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();

        assertThat(responseBody).isEqualTo(objectToJSONString(testStudent));
    }

    @Test
    @DisplayName("getStudent 할 때 StudentNotFoundException 발생")
    void getStudentExceptionTest() throws Exception{
        when(studentRepository.getStudent(anyLong())).thenThrow(StudentNotFoundException.class);

        mockMvc.perform(get("/students/{studentId}", 11111))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof StudentNotFoundException));
    }

    private String objectToJSONString(Object obj) throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    @Test
    void registerStudent() throws Exception{
        mockMvc.perform(post("/students")
                        .content(objectToJSONString(testStudent))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string("Registerd"))
                .andDo(print());
    }

    @Test
    @DisplayName("registerStudent 할 때 StudentAlreadyExistsException 발생")
    void registerExceptionTest() throws Exception{
        when(studentRepository.register(anyLong(), anyString(), anyString(), anyInt(), anyString())).thenThrow(StudentAlreadyExistsException.class);
        mockMvc.perform(post("/students")
                        .content(objectToJSONString(testStudent))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof StudentAlreadyExistsException))
                .andExpect(result -> assertEquals(result.getResponse().getContentAsString(), "Already Exist"))
                .andReturn();
    }

    @Test
    void modifyStudentTest() throws Exception{
        doNothing().when(studentRepository).modify(testStudent);
        mockMvc.perform(put("/students/{studentId}", 10)
                        .content(objectToJSONString(testStudent))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> assertEquals(result.getResponse().getContentAsString(), "Modified"));
    }

    @Test
    @DisplayName("잘못된 요청으로 수정 실패할 경우")
    void modifyStudentFailedTest() throws Exception{
        mockMvc.perform(put("/students/{studentId}", 11)
                        .content(objectToJSONString(testStudent))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertEquals(result.getResponse().getContentAsString(), "Recheck id value"));
    }
}