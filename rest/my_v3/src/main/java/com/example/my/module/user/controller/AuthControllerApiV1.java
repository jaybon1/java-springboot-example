package com.example.my.module.user.controller;

import org.springframework.http.HttpEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.my.module.user.dto.AuthDTO;
import com.example.my.module.user.service.AuthServiceApiV1;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthControllerApiV1 {

    private final AuthServiceApiV1 authServiceApiV1;

    @PostMapping("/join")
    public HttpEntity<?> join(@Validated @RequestBody AuthDTO.ReqBasic reqDto) {
        return authServiceApiV1.join(reqDto);
    }
    
}
