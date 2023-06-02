package com.example.my.module.user.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.example.my.module.user.entity.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class AuthDTO {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReqBasic {

        @NotBlank(message = "아이디를 입력해주세요.")
        @Size(min = 3, message = "아이디는 3자 이상 입력해주세요.")
        private String id;

        @NotBlank(message = "비밀번호를 입력해주세요.")
        @Size(min = 3, message = "비밀번호는 3자 이상 입력해주세요.")
        // @Pattern(regexp = "^(?=.*[\\p{Punct}])(?=.*[a-z]).{3,}$", message = "비밀번호는 특수문자, 영어소문자를 포함해서 3자 이상 입력해주세요.")
        private String pw;

        public UserEntity toEntity() {
            return UserEntity.builder()
                    .id(id)
                    .pw(pw)
                    .deleteYn('N')
                    .createDate(LocalDateTime.now())
                    .build();
        }

    }

}
