package com.example.my.domain.todo.service;

import com.example.my.common.dto.LoginUserDTO;
import com.example.my.domain.todo.dto.ResTodoTableDTO;
import com.example.my.model.todo.entity.TodoEntity;
import com.example.my.model.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoService {

    private final TodoRepository todoRepository;

    public ResTodoTableDTO getTodoTableData(LoginUserDTO loginUserDTO) {

        List<TodoEntity> todoEntityList = todoRepository.findByUserEntity_IdxAndDeleteDateIsNull(loginUserDTO.getUser().getIdx());

        return ResTodoTableDTO.of(todoEntityList);
    }

}
