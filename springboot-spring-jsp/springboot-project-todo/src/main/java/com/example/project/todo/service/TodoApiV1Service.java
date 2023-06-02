package com.example.project.todo.service;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.project.common.ResDTO;
import com.example.project.todo.dto.TodoDTO;
import com.example.project.todo.entity.TodoEntity;
import com.example.project.todo.repository.TodoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoApiV1Service {

        private final TodoRepository todoRepository;

        public HttpEntity<ResDTO<?>> select(String deleteYn) {
                return ResponseEntity
                                .ok()
                                .body(ResDTO.builder()
                                                .code(0)
                                                .message("조회에 성공하였습니다.")
                                                .data(todoRepository.findByDeleteYn(deleteYn)
                                                                .stream()
                                                                .map(TodoDTO.ResBasic::fromEntity)
                                                                .collect(Collectors.toList()))
                                                .build());
        }

        @Transactional
        public HttpEntity<ResDTO<?>> insert(TodoDTO.ReqBasic reqDto) {

                TodoEntity todoEntity = reqDto.toEntity();

                try {
                        todoRepository.insert(todoEntity);
                        return ResponseEntity
                                        .ok()
                                        .body(ResDTO.builder()
                                                        .code(0)
                                                        .message("등록에 성공하였습니다.")
                                                        .build());
                } catch (Exception e) {
                        e.printStackTrace();
                        return ResponseEntity
                                        .internalServerError()
                                        .body(ResDTO.builder()
                                                        .code(1)
                                                        .message("등록에 실패하였습니다.")
                                                        .build());
                }
        }

        @Transactional
        public HttpEntity<ResDTO<?>> updateDoneYn(TodoDTO.ReqUpdate reqDto) {

                TodoEntity todoEntity = todoRepository.findByIdx(reqDto.getIdx());

                if (todoEntity == null) {
                        return ResponseEntity
                                        .badRequest()
                                        .body(ResDTO.builder()
                                                        .code(1)
                                                        .message("잘못된 요청입니다.")
                                                        .build());
                }

                todoEntity.setDoneYn("Y".equals(todoEntity.getDoneYn()) ? "N" : "Y");
                todoEntity.setUpdateDate(LocalDateTime.now());

                try {
                        todoRepository.update(todoEntity);
                        return ResponseEntity
                                        .ok()
                                        .body(ResDTO.builder()
                                                        .code(0)
                                                        .message("수정에 성공하였습니다.")
                                                        .build());
                } catch (Exception e) {
                        e.printStackTrace();
                        return ResponseEntity
                                        .internalServerError()
                                        .body(ResDTO.builder()
                                                        .code(2)
                                                        .message("수정에 실패하였습니다.")
                                                        .build());
                }
        }

        @Transactional
        public HttpEntity<ResDTO<?>> updateDeleteYn(TodoDTO.ReqDelete reqDto) {
                TodoEntity todoEntity = todoRepository.findByIdx(reqDto.getIdx());

                if (todoEntity == null) {
                        return ResponseEntity
                                        .badRequest()
                                        .body(ResDTO.builder()
                                                        .code(1)
                                                        .message("잘못된 요청입니다.")
                                                        .build());
                }

                todoEntity.setDeleteYn("Y");
                todoEntity.setDeleteDate(LocalDateTime.now());

                try {
                        todoRepository.update(todoEntity);
                        return ResponseEntity
                                        .ok()
                                        .body(ResDTO.builder()
                                                        .code(0)
                                                        .message("삭제에 성공하였습니다.")
                                                        .build());
                } catch (Exception e) {
                        e.printStackTrace();
                        return ResponseEntity
                                        .internalServerError()
                                        .body(ResDTO.builder()
                                                        .code(2)
                                                        .message("삭제에 실패하였습니다.")
                                                        .build());
                }
        }

}
