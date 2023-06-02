package com.example.project.todo.service;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import com.example.project.common.ResDTO;
import com.example.project.todo.dto.TodoDTO;
import com.example.project.todo.entity.TodoEntity;
import com.example.project.todo.repository.TodoRepository;

public class TodoApiV1Service {

    public ResDTO<?> select(String deleteYn) {
    	return ResDTO.builder()
    			.code(0)
    			.message("조회에 성공하였습니다.")
    			.data(TodoRepository.findByDeleteYn(deleteYn)
                        .stream()
                        .map(TodoDTO.ResBasic::fromEntity)
                        .collect(Collectors.toList()))
    			.build();
    }


    public ResDTO<?> insert(TodoDTO.ReqBasic reqDto) {
    	
    	if (reqDto.getContent() == null || reqDto.getContent().equals("")) {
            return ResDTO.builder()
                    .code(1)
                    .message("잘못된 요청입니다.")
                    .build();
		}

        TodoEntity todoEntity = reqDto.toEntity();

        try {
            TodoRepository.insert(todoEntity);
            return ResDTO.builder()
                    .code(0)
                    .message("등록에 성공하였습니다.")
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResDTO.builder()
                    .code(2)
                    .message("등록에 실패하였습니다.")
                    .build();
        }
    }

    public ResDTO<?> updateDoneYn(TodoDTO.ReqUpdate reqDto) {

        TodoEntity todoEntity = TodoRepository.findByIdx(reqDto.getIdx());
        
        System.out.println(todoEntity);

        if (todoEntity == null) {
                return ResDTO.builder()
                        .code(1)
                        .message("잘못된 요청입니다.")
                        .build();
        }

        todoEntity.setDoneYn("Y".equals(todoEntity.getDoneYn()) ? "N" : "Y");
        todoEntity.setUpdateDate(LocalDateTime.now());

        try {
            TodoRepository.update(todoEntity);
            return ResDTO.builder()
                    .code(0)
                    .message("수정에 성공하였습니다.")
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResDTO.builder()
                    .code(2)
                    .message("수정에 실패하였습니다.")
                    .build();
        }
    }

    public ResDTO<?> updateDeleteYn(TodoDTO.ReqDelete reqDto) {
            TodoEntity todoEntity = TodoRepository.findByIdx(reqDto.getIdx());

            if (todoEntity == null) {
                    return ResDTO.builder()
                            .code(1)
                            .message("잘못된 요청입니다.")
                            .build();
            }

            todoEntity.setDeleteYn("Y");
            todoEntity.setDeleteDate(LocalDateTime.now());

            try {
                    TodoRepository.update(todoEntity);
                    return ResDTO.builder()
                            .code(0)
                            .message("삭제에 성공하였습니다.")
                            .build();
            } catch (Exception e) {
                    e.printStackTrace();
                    return ResDTO.builder()
                            .code(2)
                            .message("삭제에 실패하였습니다.")
                            .build();
            }
    }


}
