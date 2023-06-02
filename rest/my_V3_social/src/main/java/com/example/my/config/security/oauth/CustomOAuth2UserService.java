package com.example.my.config.security.oauth;

import com.example.my.config.security.CustomUserDetails;
import com.example.my.module.user.entity.UserEntity;
import com.example.my.module.user.entity.UserRoleEntity;
import com.example.my.module.user.repository.UserRepository;
import com.example.my.module.user.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Primary
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        System.out.println("getClientRegistration: " + userRequest.getClientRegistration());
        System.out.println("getAccessToken: " + userRequest.getAccessToken().getTokenValue());
        System.out.println("getAttributes: " + super.loadUser(userRequest).getAttributes());

        OAuth2User oAuth2User = super.loadUser(userRequest);

        OAuth2UserInfo oAuth2UserInfo = null;

        if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {
            oAuth2UserInfo = new GoogleOAuth2UserInfo(oAuth2User.getAttributes());
        } else if (userRequest.getClientRegistration().getRegistrationId().equals("kakao")) {
            oAuth2UserInfo = new KakaoOAuth2UserInfo(oAuth2User.getAttributes());
        } else {
            System.out.println("지원하지 않은 로그인 서비스 입니다.");
        }

        UserEntity userEntity = userRepository.findByProviderAndProviderIdAndDeleteYn(oAuth2UserInfo.getProvider(), oAuth2UserInfo.getProviderId(), 'N');

        if (userEntity == null) {
            userRepository.insert(
                    UserEntity.builder()
                            .id(oAuth2UserInfo.getProvider() + oAuth2UserInfo.getProviderId())
                            .pw(passwordEncoder.encode(UUID.randomUUID().toString()))
                            .provider(oAuth2UserInfo.getProvider())
                            .providerId(oAuth2UserInfo.getProviderId())
                            .deleteYn('N')
                            .createDate(LocalDateTime.now())
                            .build()
            );
        }

        userEntity = userRepository.findByProviderAndProviderIdAndDeleteYn(oAuth2UserInfo.getProvider(), oAuth2UserInfo.getProviderId(), 'N');

        userRoleRepository.insert(
                UserRoleEntity.builder()
                        .userIdx(userEntity.getIdx())
                        .role("USER")
                        .createDate(LocalDateTime.now())
                        .build());

        List<String> roleList = userRoleRepository.findRoleByUserIdx(userEntity.getIdx());

        if (roleList.size() < 1) {
            throw new AuthenticationCredentialsNotFoundException("권한이 없습니다.");
        }

        return new CustomUserDetails(userEntity, oAuth2User.getAttributes(), roleList);
    }
}
