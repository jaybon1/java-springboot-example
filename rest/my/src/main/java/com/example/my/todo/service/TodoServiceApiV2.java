package com.example.my.todo.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.my.common.ResDTO;
import com.example.my.todo.dto.TodoDTO;
import com.example.my.todo.entity.TodoEntity;
import com.example.my.todo.repository.TodoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoServiceApiV2 {

    private final TodoRepository todoRepository;

    public ResDTO<?> findByDeleteYn(Character deleteYn) {

        List<TodoEntity> todoEntityList = todoRepository.findByDeleteYn(deleteYn);

        return ResDTO.builder()
                .code(0)
                .message("할 일 조회에 성공하였습니다.")
                .data(TodoDTO.ResBasic.fromEntityList(todoEntityList))
                .build();
    }

    @Transactional
    public ResDTO<?> insert(TodoDTO.ReqBasic reqDto) {

        todoRepository.insert(reqDto.toEntity());

        return ResDTO.builder()
                .code(0)
                .message("할 일 추가에 성공하였습니다.")
                .build();
    }

    @Transactional
    public ResDTO<?> update(Integer idx) {
        TodoEntity todoEntity = todoRepository.findByIdx(idx);

        if (todoEntity.getDoneYn().equals('N')) {
            todoEntity.setDoneYn('Y');
        } else {
            todoEntity.setDoneYn('N');
        }
        todoEntity.setUpdateDate(LocalDateTime.now());

        todoRepository.update(todoEntity);

        return ResDTO.builder()
                .code(0)
                .message(todoEntity.getIdx() + "번 할 일(" + todoEntity.getContent() + ") 수정에 성공하였습니다.")
                .build();
    }

    @Transactional
    public ResDTO<?> delete(Integer idx) {

        TodoEntity todoEntity = todoRepository.findByIdx(idx);
        todoEntity.setDeleteYn('Y');
        todoEntity.setDeleteDate(LocalDateTime.now());

        todoRepository.update(todoEntity);

        return ResDTO.builder()
            .code(0)
            .message(todoEntity.getIdx() + "번 할 일(" + todoEntity.getContent() + ") 삭제에 성공하였습니다.")
            .build();
    }

}
