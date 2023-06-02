package com.example.my.todo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.my.todo.entity.TodoEntity;
import com.example.my.todo.service.TodoService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @GetMapping("/todoList")
    public String todoList(Model model) {

        List<TodoEntity> todoList = todoService.findByDeleteYn('N');
        long todoCount = todoList.stream().filter((todoEntity) -> todoEntity.getDoneYn().equals('N')).count();
        
        model.addAttribute("todoList", todoList);
        model.addAttribute("todoCount", todoCount);

        return "todoList";
    }

}
