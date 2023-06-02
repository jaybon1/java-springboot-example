package com.example.project.todo.service;

import java.util.List;
import java.util.stream.Collectors;

import com.example.project.todo.dto.TodoDTO;
import com.example.project.todo.repository.TodoRepository;

public class TodoService {
	
    public List<TodoDTO.ResBasic> findByDeleteYn(String deleteYn) {
        return TodoRepository.findByDeleteYn(deleteYn)
                .stream()
                .map(TodoDTO.ResBasic::fromEntity)
                .collect(Collectors.toList());
    }

}
