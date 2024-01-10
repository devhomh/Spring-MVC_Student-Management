package com.nhnacademy.springmvc.controller;

import com.nhnacademy.springmvc.exception.StudentNotFoundException;
import com.nhnacademy.springmvc.exception.UserNotFoundException;
import com.nhnacademy.springmvc.repository.UserRepository;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
public class LoginController {
    private final UserRepository userRepository;

    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/login")
    public String login(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return Objects.isNull(session.getAttribute("login")) ?"thymeleaf/loginForm" :  "redirect:/";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam("id") String id,
                          @RequestParam("password") String password,
                          HttpServletRequest request) {
        if(userRepository.matches(id, password)){
            HttpSession session = request.getSession();
            session.setAttribute("login", id);

            return "redirect:/";
        }

        log.error("비밀번호가 맞지 않습니다.");
        return "redirect:/login";
    }

    @PostMapping("/logout")
    public String logOut(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        session.removeAttribute("login");
        return "redirect:/login";
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView notFoundException(RuntimeException exception) {
        String errorMsg = "유저를 찾을 수 없습니다";
        log.error(errorMsg, exception);
        ModelAndView mav = new ModelAndView("thymeleaf/loginForm");
        mav.addObject("errorMsg", errorMsg);

        return mav;
    }
}
