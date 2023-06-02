package com.example.my.module.user.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.example.my.module.user.entity.UserEntity;

@Repository
@Mapper
public interface UserRepository {

    UserEntity findById(String id);

    UserEntity findByIdAndDeleteYn(@Param("id") String id, @Param("deleteYn") Character deleteYn);

    Integer insert(UserEntity userEntity);

}
