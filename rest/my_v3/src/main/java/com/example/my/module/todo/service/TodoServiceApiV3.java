package com.example.my.module.todo.service;

import com.example.my.config.security.CustomUserDetails;
import com.example.my.module.todo.dto.TodoDTO;
import com.example.my.module.todo.entity.TodoEntity;
import com.example.my.module.todo.repository.TodoRepository;
import com.example.my.common.dto.ResDTO;

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
public class TodoServiceApiV3 {

    private final TodoRepository todoRepository;

    public HttpEntity<?> findByUserIdxAndDeleteYn(CustomUserDetails customUserDetails, Character deleteYn) {
        List<TodoEntity> todoEntityList = todoRepository.findByUserIdxAndDeleteYn(customUserDetails.getUserEntity().getIdx(), deleteYn);

        return new ResponseEntity<>(
                ResDTO.builder()
                        .code(0)
                        .message("할 일 조회에 성공하였습니다.")
                        .data(TodoDTO.ResAuth.fromEntityListAndUserId(customUserDetails.getUsername(), todoEntityList))
                        .build(),
                HttpStatus.OK
        );
    }

    @Transactional
    public HttpEntity<?> insert(CustomUserDetails customUserDetails, TodoDTO.ReqBasic reqDto) {

        todoRepository.insert(reqDto.toEntity(customUserDetails.getUserEntity().getIdx()));

        return new ResponseEntity<>(
                ResDTO.builder()
                        .code(0)
                        .message("할 일 추가에 성공하였습니다.")
                        .build(),
                HttpStatus.OK
        );
    }

    @Transactional
    public HttpEntity<?> update(CustomUserDetails customUserDetails, Integer idx) {
        TodoEntity todoEntity = todoRepository.findByIdx(idx);

        if (!todoEntity.getUserIdx().equals(customUserDetails.getUserEntity().getIdx())) {
            throw new RuntimeException("잘못된 요청입니다.");
        }

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
    public HttpEntity<?> delete(CustomUserDetails customUserDetails, Integer idx) {

        TodoEntity todoEntity = todoRepository.findByIdx(idx);

        if (!todoEntity.getUserIdx().equals(customUserDetails.getUserEntity().getIdx())) {
            throw new RuntimeException("잘못된 요청입니다.");
        }

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
