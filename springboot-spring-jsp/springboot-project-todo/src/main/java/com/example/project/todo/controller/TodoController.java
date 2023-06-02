package com.example.project.todo.controller;

import com.example.project.todo.dto.TodoDTO;
import com.example.project.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class TodoController {

    private final TodoService todoService;

    @GetMapping("/todoList")
    public String select(Model model) {
        List<TodoDTO.ResBasic> dtoList = todoService.findByDeleteYn("N");
        model.addAttribute("todoList", dtoList);
        model.addAttribute("todoSize", dtoList.stream().filter(todo -> todo.getDoneYn().equals("N")).count());
        return "todoList";
    }

}
