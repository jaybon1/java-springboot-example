package com.example.my.config.security.auth;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.example.my.config.security.auth.oauth2.OAuth2UserInfo;
import com.example.my.model.user.entity.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Getter
public class CustomUserDetails implements UserDetails, OAuth2User {

    private final User user;
    private final Map<String, Object> attributes;

    public static CustomUserDetails of(UserEntity userEntity) {
        return CustomUserDetails.builder()
                .user(User.fromEntity(userEntity))
                .build();
    }

    public static CustomUserDetails of(UserEntity userEntity, OAuth2UserInfo oAuth2UserInfo) {
        return CustomUserDetails.builder()
                .user(User.fromEntity(userEntity))
                .attributes(oAuth2UserInfo.getAttributes())
                .build();
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    public static class User {
        private Long idx;
        private String id;
        private String password;
        private List<String> roleList;

        public static User fromEntity(UserEntity userEntity) {
            return User.builder()
                    .idx(userEntity.getIdx())
                    .id(userEntity.getId())
                    .password(userEntity.getPassword())
                    .roleList(
                            userEntity.getUserRoleEntityList()
                                    .stream()
                                    .map(userRoleEntity -> userRoleEntity.getRole())
                                    .toList())
                    .build();
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoleList()
                .stream()
                .map(role -> (GrantedAuthority) () -> "ROLE_" + role)
                .toList();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Map<String, Object> getAttributes() {
        // 소셜 로그인 정보
        return attributes;
    }

    @Override
    public String getName() {
        // 소셜 로그인 유저명
        return null;
    }
}
