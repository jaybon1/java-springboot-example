package com.example.my.domain.todo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.my.config.security.auth.CustomUserDetails;
import com.example.my.domain.todo.dto.ResTodoTableDTO;
import com.example.my.model.todo.entity.TodoEntity;
import com.example.my.model.todo.repository.TodoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoService {

    private final TodoRepository todoRepository;

    public ResTodoTableDTO getTodoTableData(CustomUserDetails customUserDetails) {

        List<TodoEntity> todoEntityList = todoRepository.findByUserEntity_IdxAndDeleteDateIsNull(customUserDetails.getUser().getIdx());

        return ResTodoTableDTO.of(todoEntityList);
    }

}
