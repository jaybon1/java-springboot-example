package com.example.project.todo.service;

import com.example.project.todo.dto.TodoDTO;
import com.example.project.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoService {

    private final TodoRepository todoRepository;

    public List<TodoDTO.ResBasic> findByDeleteYn(String deleteYn) {
        return todoRepository.findByDeleteYn(deleteYn)
                .stream()
                .map(TodoDTO.ResBasic::fromEntity)
                .collect(Collectors.toList());
    }

}
