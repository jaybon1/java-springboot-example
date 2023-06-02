package com.example.my.module.todo.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.my.common.dto.ResDTO;
import com.example.my.module.todo.dto.TodoDTO;
import com.example.my.module.todo.entity.TodoEntity;
import com.example.my.module.todo.repository.TodoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoServiceApiV2 {

    private final TodoRepository todoRepository;

    public HttpEntity<?> findByDeleteYn(Character deleteYn) {

        List<TodoEntity> todoEntityList = todoRepository.findByDeleteYn(deleteYn);

        return new ResponseEntity<>(
                ResDTO.builder()
                        .code(0)
                        .message("할 일 조회에 성공하였습니다.")
                        .data(TodoDTO.ResBasic.fromEntityList(todoEntityList))
                        .build(),
                HttpStatus.OK
        );
    }

    @Transactional
    public HttpEntity<?> insert(TodoDTO.ReqBasic reqDto) {

        todoRepository.insert(reqDto.toEntity());

        return new ResponseEntity<>(
                ResDTO.builder()
                        .code(0)
                        .message("할 일 추가에 성공하였습니다.")
                        .build(),
                HttpStatus.OK
        );
    }

    @Transactional
    public HttpEntity<?> update(Integer idx) {
        TodoEntity todoEntity = todoRepository.findByIdx(idx);

        if (todoEntity.getDoneYn().equals('N')) {
            todoEntity.setDoneYn('Y');
        } else {
            todoEntity.setDoneYn('N');
        }
        todoEntity.setUpdateDate(LocalDateTime.now());

        todoRepository.update(todoEntity);

        return new ResponseEntity<>(
                ResDTO.builder()
                        .code(0)
                        .message(todoEntity.getIdx() + "번 할 일(" + todoEntity.getContent() + ") 수정에 성공하였습니다.")
                        .build(),
                HttpStatus.OK
        );
    }

    @Transactional
    public HttpEntity<?> delete(Integer idx) {

        TodoEntity todoEntity = todoRepository.findByIdx(idx);
        todoEntity.setDeleteYn('Y');
        todoEntity.setDeleteDate(LocalDateTime.now());

        todoRepository.update(todoEntity);

        return new ResponseEntity<>(
                ResDTO.builder()
                        .code(0)
                        .message(todoEntity.getIdx() + "번 할 일(" + todoEntity.getContent() + ") 삭제에 성공하였습니다.")
                        .build(),
                HttpStatus.OK
        );
    }

}
