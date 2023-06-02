package com.example.my.module.user.service;

import java.time.LocalDateTime;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.my.common.dto.ResDTO;
import com.example.my.common.exception.BadRequestException;
import com.example.my.module.user.dto.AuthDTO;
import com.example.my.module.user.entity.UserEntity;
import com.example.my.module.user.entity.UserRoleEntity;
import com.example.my.module.user.repository.UserRepository;
import com.example.my.module.user.repository.UserRoleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthServiceApiV1 {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    @Transactional
    public HttpEntity<?> join(AuthDTO.ReqBasic reqDto) {

        UserEntity userEntityForCheck = userRepository.findById(reqDto.getId());

        if (userEntityForCheck != null) {
            throw new BadRequestException("이미 사용 중인 아이디 입니다.");
        }

        userRepository.insert(reqDto.toEntity());

        UserEntity userEntity = userRepository.findById(reqDto.getId());

        userRoleRepository.insert(
                UserRoleEntity.builder()
                        .userIdx(userEntity.getIdx())
                        .role("USER")
                        .createDate(LocalDateTime.now())
                        .build());

        return new ResponseEntity<>(
                ResDTO.builder()
                        .code(0)
                        .message("회원가입에 성공했습니다.")
                        .build(),
                HttpStatus.OK);
    }

}
