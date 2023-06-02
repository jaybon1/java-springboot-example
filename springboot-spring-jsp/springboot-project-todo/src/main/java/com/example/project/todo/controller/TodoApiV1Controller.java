package com.example.project.todo.controller;

import com.example.project.common.ResDTO;
import com.example.project.todo.dto.TodoDTO;
import com.example.project.todo.service.TodoApiV1Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/todoList")
public class TodoApiV1Controller {

    private final TodoApiV1Service todoApiV1Service;

    @GetMapping
    public HttpEntity<ResDTO<?>> select() {
        return todoApiV1Service.select("N");
    }

    @PostMapping
    public HttpEntity<ResDTO<?>> insert(@RequestBody TodoDTO.ReqBasic reqDto) {
        log.info(reqDto.getContent() + "를 등록합니다.");
        return todoApiV1Service.insert(reqDto);
    }

    @PutMapping
    public HttpEntity<ResDTO<?>> update(@RequestBody TodoDTO.ReqUpdate reqDto) {
        log.info(reqDto.getIdx() + "번 할 일을 수정합니다.");
        return todoApiV1Service.updateDoneYn(reqDto);
    }

    @DeleteMapping
    public HttpEntity<ResDTO<?>> delete(@RequestBody TodoDTO.ReqDelete reqDto) {
        log.error(reqDto.getIdx() + "번 할 일을 삭제합니다.");
        return todoApiV1Service.updateDeleteYn(reqDto);
    }

}
