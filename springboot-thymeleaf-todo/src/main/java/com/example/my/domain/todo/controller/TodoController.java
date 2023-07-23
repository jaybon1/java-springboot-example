package com.example.my.domain.todo.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TodoController {

    @GetMapping({"", "/"})
    public ModelAndView index(HttpServletRequest request) {

        HttpSession session = request.getSession();

        System.out.println(session.getAttribute("userIdx"));

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("todo/table");
        return modelAndView;
    }

}
