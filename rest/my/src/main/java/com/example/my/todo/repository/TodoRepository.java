package com.example.my.todo.repository;

import com.example.my.todo.entity.TodoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface TodoRepository {
        
        List<TodoEntity> findByDeleteYn(Character deleteYn);

        TodoEntity findByIdx(Integer idx);

        Integer insert(TodoEntity todoEntity);

        Integer update(TodoEntity todoEntity);

}
