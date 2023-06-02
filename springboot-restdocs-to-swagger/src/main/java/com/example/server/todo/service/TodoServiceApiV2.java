package com.example.server.todo.service;


import com.example.server.common.dto.ResDTO;
import com.example.server.todo.dto.TodoDTO;
import com.example.server.todo.entity.TodoEntity;
import com.example.server.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoServiceApiV2 {

    private final TodoRepository todoRepository;

    public HttpEntity<?> findAllByDeleteYn(Character deleteYn) {
        List<TodoEntity> todoEntityList = todoRepository.findAllByDeleteYn(deleteYn);

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
    public HttpEntity<?> save(TodoDTO.ReqBasic reqDto) {
        todoRepository.save(reqDto.toEntity());

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

        return new ResponseEntity<>(
                ResDTO.builder()
                        .code(0)
                        .message(todoEntity.getIdx() + "번 할 일(" + todoEntity.getContent() + ") 삭제에 성공하였습니다.")
                        .build(),
                HttpStatus.OK
        );
    }

}
