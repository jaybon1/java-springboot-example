package com.example.my.module.todo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class TodoController {

//    private final TodoService todoService;

    @GetMapping("/todo")
    public String todoList(Model model) {

//        List<TodoEntity> todoList = todoService.findByDeleteYn('N');
//        long todoCount = todoList.stream().filter((todoEntity) -> todoEntity.getDoneYn().equals('N')).count();
//
//        model.addAttribute("todoList", todoList);
//        model.addAttribute("todoCount", todoCount);

        return "todoList";
    }

}
