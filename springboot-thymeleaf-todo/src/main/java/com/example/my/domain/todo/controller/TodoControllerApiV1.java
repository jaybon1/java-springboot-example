package com.example.my.domain.todo.controller;

import com.example.my.common.dto.LoginUserDTO;
import com.example.my.common.dto.ResponseDTO;
import com.example.my.domain.todo.dto.ReqTodoTableInsertDTO;
import com.example.my.domain.todo.dto.ReqTodoTableUpdateDoneYnDTO;
import com.example.my.domain.todo.dto.ResTodoTableDTO;
import com.example.my.domain.todo.service.TodoServiceApiV1;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/todo")
public class TodoControllerApiV1 {

    private final TodoServiceApiV1 todoServiceApiV1;

    @GetMapping
    public ResponseEntity<?> getTodoTableData(HttpSession session) {

        if (session.getAttribute("loginUserDTO") == null) {
            return new ResponseEntity<>(
                    ResponseDTO.builder()
                            .code(1)
                            .message("로그인이 필요합니다.")
                            .build(),
                    HttpStatus.BAD_REQUEST
            );
        }

        return todoServiceApiV1.getTodoTableData((LoginUserDTO) session.getAttribute("loginUserDTO"));
    }

    @PostMapping
    public ResponseEntity<?> insertTodoTableData(
            @RequestBody ReqTodoTableInsertDTO dto,
            HttpSession session
    ) {

        if (session.getAttribute("loginUserDTO") == null) {
            return new ResponseEntity<>(
                    ResponseDTO.builder()
                            .code(1)
                            .message("로그인이 필요합니다.")
                            .build(),
                    HttpStatus.BAD_REQUEST
            );
        }

        LoginUserDTO loginUserDTO = (LoginUserDTO) session.getAttribute("loginUserDTO");

        return todoServiceApiV1.insertTodoTableData(dto, loginUserDTO);
    }

    @PutMapping("/{todoIdx}")
    public ResponseEntity<?> updateTodoTableData(
            @PathVariable Long todoIdx,
            @RequestBody ReqTodoTableUpdateDoneYnDTO dto,
            HttpSession session
    ) {

        if (session.getAttribute("loginUserDTO") == null) {
            return new ResponseEntity<>(
                    ResponseDTO.builder()
                            .code(1)
                            .message("로그인이 필요합니다.")
                            .build(),
                    HttpStatus.BAD_REQUEST
            );
        }

        LoginUserDTO loginUserDTO = (LoginUserDTO) session.getAttribute("loginUserDTO");

        return todoServiceApiV1.updateTodoTableData(todoIdx, dto, loginUserDTO);
    }

    @DeleteMapping("/{todoIdx}")
    public ResponseEntity<?> deleteTodoTableData(
            @PathVariable Long todoIdx,
            HttpSession session
    ) {

        if (session.getAttribute("loginUserDTO") == null) {
            return new ResponseEntity<>(
                    ResponseDTO.builder()
                            .code(1)
                            .message("로그인이 필요합니다.")
                            .build(),
                    HttpStatus.BAD_REQUEST
            );
        }

        LoginUserDTO loginUserDTO = (LoginUserDTO) session.getAttribute("loginUserDTO");

        return todoServiceApiV1.deleteTodoTableData(todoIdx, loginUserDTO);
    }


}
