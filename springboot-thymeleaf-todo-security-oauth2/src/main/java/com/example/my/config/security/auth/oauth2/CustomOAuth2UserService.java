package com.example.my.config.security.auth.oauth2;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.my.common.exception.BadRequestException;
import com.example.my.config.security.auth.CustomUserDetails;
import com.example.my.model.user.entity.UserEntity;
import com.example.my.model.user.entity.UserRoleEntity;
import com.example.my.model.user.entity.UserSocialEntity;
import com.example.my.model.user.repository.UserRepository;
import com.example.my.model.user.repository.UserRoleRepository;
import com.example.my.model.user.repository.UserSocialRepository;

import lombok.RequiredArgsConstructor;

@Service
@Primary
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final UserSocialRepository userSocialRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        OAuth2UserInfo oAuth2UserInfo = null;

        if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {
            oAuth2UserInfo = new GoogleOAuth2UserInfo(oAuth2User.getAttributes());
        } else if (userRequest.getClientRegistration().getRegistrationId().equals("kakao")) {
            oAuth2UserInfo = new KakaoOAuth2UserInfo(oAuth2User.getAttributes());
        } else {
            throw new BadRequestException("지원하지 않은 로그인 서비스 입니다.");
        }

        Optional<UserSocialEntity> userSocialEntityOptional = userSocialRepository
                .findByProviderAndProviderIdAndDeleteDateIsNull(
                        oAuth2UserInfo.getProvider(),
                        oAuth2UserInfo.getProviderId());

        UserSocialEntity userSocialEntity = null;

        if (userSocialEntityOptional.isEmpty()) {
            UserEntity userEntityForSaving = UserEntity.builder()
                    .id(oAuth2UserInfo.getProvider() + oAuth2UserInfo.getProviderId())
                    .password(passwordEncoder.encode(UUID.randomUUID().toString()))
                    .createDate(LocalDateTime.now())
                    .build();

            UserEntity userEntity = userRepository.save(userEntityForSaving);

            UserRoleEntity userRoleEntityForSaving = UserRoleEntity.builder()
                    .createDate(LocalDateTime.now())
                    .userEntity(userEntity)
                    .role("USER")
                    .build();

            userRoleRepository.save(userRoleEntityForSaving);

            UserSocialEntity userSocialEntityForSaving = UserSocialEntity.builder()
                    .userEntity(userEntity)
                    .provider(oAuth2UserInfo.getProvider())
                    .providerId(oAuth2UserInfo.getProviderId())
                    .createDate(LocalDateTime.now())
                    .build();

            userSocialEntity = userSocialRepository.save(userSocialEntityForSaving);

        } else {

            userSocialEntity = userSocialEntityOptional.get();

        }

        CustomUserDetails customUserDetails = CustomUserDetails.of(userSocialEntity.getUserEntity(), oAuth2UserInfo);
        return customUserDetails;

    }
}
