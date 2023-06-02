package com.example.my.config.security;

import java.util.List;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.my.module.user.entity.UserEntity;
import com.example.my.module.user.repository.UserRepository;
import com.example.my.module.user.repository.UserRoleRepository;

import lombok.RequiredArgsConstructor;

@Service // UserDetailsService타입으로 메모리에 뜬다 (덮어씌워짐)
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findByIdAndDeleteYn(username, 'N');

        if (userEntity == null){
            throw new UsernameNotFoundException("아이디를 정확히 입력해주세요.");
        }
        
        List<String> roleList = userRoleRepository.findRoleByUserIdx(userEntity.getIdx());

        if (roleList.size() < 1) {
            throw new AuthenticationCredentialsNotFoundException("권한이 없습니다.");
        }

        return new CustomUserDetails(userEntity, roleList);
    }
}
