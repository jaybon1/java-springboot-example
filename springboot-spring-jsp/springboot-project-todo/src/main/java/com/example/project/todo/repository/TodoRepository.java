package com.example.project.todo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.project.todo.entity.TodoEntity;

@Repository
@Mapper
public interface TodoRepository {
        
        List<TodoEntity> findByDeleteYn(String deleteYn);

        List<TodoEntity> findByfindByDeleteYnAndDoneYn(String deleteYn, String doneYn);

        TodoEntity findByIdx(Integer idx);

        Integer insert(TodoEntity todoEntity);

        Integer update(TodoEntity todoEntity);

}
