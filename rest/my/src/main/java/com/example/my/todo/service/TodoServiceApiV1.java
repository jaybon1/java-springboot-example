package com.example.my.todo.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.my.common.ResDTO;
import com.example.my.todo.entity.TodoEntity;
import com.example.my.todo.repository.TodoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoServiceApiV1 {

    private final TodoRepository todoRepository;

    public ResDTO<?> findByDeleteYn(Character deleteYn) {
        return ResDTO.builder()
                .code(0)
                .message("할 일 조회에 성공하였습니다.")
                .data(todoRepository.findByDeleteYn(deleteYn))
                .build();
    }

    @Transactional
    public ResDTO<?> insert(String content) {
        TodoEntity todoEntity = TodoEntity.builder()
                .content(content)
                .doneYn('N')
                .deleteYn('N')
                .createDate(LocalDateTime.now())
                .build();

        todoRepository.insert(todoEntity);

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

        // 리턴값 수정
        // 1번 할 일(잠자기) 삭제에 성공하였습니다.
        return ResDTO.builder()
            .code(0)
            .message(todoEntity.getIdx() + "번 할 일(" + todoEntity.getContent() + ") 삭제에 성공하였습니다.")
            .build();
    }

}
