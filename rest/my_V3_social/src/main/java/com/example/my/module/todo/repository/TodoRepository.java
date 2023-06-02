package com.example.my.module.todo.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.example.my.module.todo.entity.TodoEntity;

import java.util.List;

@Repository
@Mapper
public interface TodoRepository {

    List<TodoEntity> findByDeleteYn(Character deleteYn);

    List<TodoEntity> findByUserIdxAndDeleteYn(
            @Param("userIdx") Integer userIdx,
            @Param("deleteYn") Character deleteYn
    );

    TodoEntity findByIdx(Integer idx);

    Integer insert(TodoEntity todoEntity);

    Integer update(TodoEntity todoEntity);

}
