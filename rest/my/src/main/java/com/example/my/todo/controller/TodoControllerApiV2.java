package com.example.my.todo.controller;

import com.example.my.common.ResDTO;
import com.example.my.todo.dto.TodoDTO;
import com.example.my.todo.service.TodoServiceApiV2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/todo")
public class TodoControllerApiV2 {
    
    private final TodoServiceApiV2 todoServiceApiV2;

    @GetMapping
    public ResDTO<?> select() {
        log.info("리스트를 가져옵니다.");
        return todoServiceApiV2.findByDeleteYn('N');
    }

    @PostMapping
    public ResDTO<?> insert(@Validated @RequestBody TodoDTO.ReqBasic reqDto) {
        log.warn("할 일 (" + reqDto.getContent() + ") 추가를 요청합니다.");
        return todoServiceApiV2.insert(reqDto);
    }

    @PutMapping("/{idx}")
    public ResDTO<?> update(@PathVariable Integer idx) {
        log.warn(idx+"번 할 일 수정을 요청합니다.");
        return todoServiceApiV2.update(idx);
    }

    @DeleteMapping("/{idx}")
    public ResDTO<?> delete(@PathVariable Integer idx) {
        log.warn(idx+"번 할 일 삭제를 요청합니다.");
        return todoServiceApiV2.delete(idx);
    }
}
