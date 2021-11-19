package nl.com.yacht.jspring.javaquiz.controller;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import nl.com.yacht.jspring.javaquiz.utils.QuizUtils;

@Controller
public class AdminController {

    @GetMapping("/")
    public String index(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Arrays.asList("quiztype", "questionsTemp", "user", "actualPage", "availableQuizz").forEach(session::removeAttribute);
        request.setAttribute("quizz", QuizUtils.loadAvailableQuizz());
        return "index";
    }
}