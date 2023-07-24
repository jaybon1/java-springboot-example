package com.example.my.domain.auth.controller;

import com.example.my.common.dto.ResponseDTO;
import com.example.my.domain.auth.dto.ReqJoinDTO;
import com.example.my.domain.auth.dto.ReqLoginDTO;
import com.example.my.domain.auth.service.AuthServiceApiV1;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthControllerApiV1 {

    private final AuthServiceApiV1 authServiceApiV1;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody ReqLoginDTO dto, HttpSession session) {
        return authServiceApiV1.login(dto, session);
    }

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody ReqJoinDTO dto) {
        return authServiceApiV1.join(dto);
    }

//    @PostMapping("/logout")
//    public ResponseEntity<?> logout(HttpSession session) {
//
//        session.invalidate();
//
//        return new ResponseEntity<>(
//                ResponseDTO.builder()
//                        .code(0)
//                        .message("로그아웃에 성공하였습니다.")
//                        .build(),
//                HttpStatus.OK
//        );
//    }

}
