package com.example.my.model.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.my.model.user.entity.UserSocialEntity;

public interface UserSocialRepository extends JpaRepository<UserSocialEntity, Long> {
    Optional<UserSocialEntity> findByProviderAndProviderIdAndDeleteDateIsNull(String provider, String providerId);
}
