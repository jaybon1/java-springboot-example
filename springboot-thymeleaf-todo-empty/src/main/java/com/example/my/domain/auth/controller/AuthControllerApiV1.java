package com.example.my.domain.auth.controller;

import com.example.my.domain.auth.dto.ReqJoinDTO;
import com.example.my.domain.auth.dto.ReqLoginDTO;
import com.example.my.domain.auth.service.AuthServiceApiV1;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthControllerApiV1 {

    private final AuthServiceApiV1 authServiceApiV1;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody ReqLoginDTO dto, HttpSession session) {
        // TODO : 서비스에서 로그인하기
        return null;
    }

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody ReqJoinDTO dto) {
        // TODO : 서비스에서 회원가입하기
        return null;
    }

}
