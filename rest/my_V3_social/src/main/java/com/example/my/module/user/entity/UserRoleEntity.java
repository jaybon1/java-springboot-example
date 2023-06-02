package com.example.my.module.user.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "idx", callSuper = false)
public class UserRoleEntity {
    private Integer idx;
    private Integer userIdx;
    private String role;
    private LocalDateTime createDate;
}
