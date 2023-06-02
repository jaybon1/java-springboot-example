package com.example.server.todo.repository;

import com.example.server.todo.entity.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, Integer> {

    TodoEntity findByIdx(Integer idx);

    List<TodoEntity> findAllByDeleteYn(Character deleteYn);

}
