package com.example.my.domain.auth.service;

import com.example.my.common.dto.LoginUserDTO;
import com.example.my.common.dto.ResponseDTO;
import com.example.my.domain.auth.dto.ReqJoinDTO;
import com.example.my.domain.auth.dto.ReqLoginDTO;
import com.example.my.model.user.entity.UserEntity;
import com.example.my.model.user.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthServiceApiV1 {

    private final UserRepository userRepository;

    public ResponseEntity<?> login(ReqLoginDTO dto, HttpSession session){
        Optional<UserEntity> userEntityOptional = userRepository.findByIdAndDeleteDateIsNull(dto.getUser().getId());

        if (userEntityOptional.isEmpty()) {
            return new ResponseEntity<>(
                    ResponseDTO.builder()
                            .code(1)
                            .message("존재하지 않는 사용자입니다.")
                            .build(),
                    HttpStatus.BAD_REQUEST
            );
        }

        UserEntity userEntity = userEntityOptional.get();

        if (!userEntity.getPassword().equals(dto.getUser().getPassword())) {
            return new ResponseEntity<>(
                    ResponseDTO.builder()
                            .code(2)
                            .message("비밀번호가 일치하지 않습니다.")
                            .build(),
                    HttpStatus.BAD_REQUEST
            );
        }

        session.setAttribute("loginUserDTO", LoginUserDTO.of(userEntity));

        return new ResponseEntity<>(
                ResponseDTO.builder()
                        .code(0)
                        .message("로그인에 성공하였습니다.")
                        .build(),
                HttpStatus.OK
        );

    }

    @Transactional
    public ResponseEntity<?> join(ReqJoinDTO dto) {

        if (dto.getUser() == null){
            return new ResponseEntity<>(
                    ResponseDTO.builder()
                            .code(1)
                            .message("회원 정보를 입력해주세요.")
                            .build(),
                    HttpStatus.BAD_REQUEST
            );
        }

        if (dto.getUser().getId() == null || dto.getUser().getId().equals("")) {
            return new ResponseEntity<>(
                    ResponseDTO.builder()
                            .code(1)
                            .message("아이디를 입력해주세요.")
                            .build(),
                    HttpStatus.BAD_REQUEST
            );
        }

        if (dto.getUser().getPassword() == null || dto.getUser().getPassword().equals("")) {
            return new ResponseEntity<>(
                    ResponseDTO.builder()
                            .code(1)
                            .message("비밀번호를 입력해주세요.")
                            .build(),
                    HttpStatus.BAD_REQUEST
            );
        }

        Optional<UserEntity> userEntityOptional = userRepository.findById(dto.getUser().getId());

        if (userEntityOptional.isPresent()) {
            return new ResponseEntity<>(
                    ResponseDTO.builder()
                            .code(1)
                            .message("이미 존재하는 아이디입니다.")
                            .build(),
                    HttpStatus.BAD_REQUEST
            );
        }

        UserEntity userEntityForSaving = UserEntity.builder()
                .id(dto.getUser().getId())
                .password(dto.getUser().getPassword())
                .createDate(LocalDateTime.now())
                .build();

        userRepository.save(userEntityForSaving);

        return new ResponseEntity<>(
                ResponseDTO.builder()
                        .code(0)
                        .message("회원가입에 성공하였습니다.")
                        .build(),
                HttpStatus.OK
        );
    }


}
