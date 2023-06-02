package com.example.my.todo.dto;

import com.example.my.todo.entity.TodoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class TodoDTO {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReqBasic {

        @NotBlank(message = "할 일 내용은 빈 값이 될 수 없습니다.")
        private String content;

        public TodoEntity toEntity(){
            return TodoEntity.builder()
            .content(content)
            .doneYn('N')
            .deleteYn('N')
            .createDate(LocalDateTime.now())
            .build();
        }
    }


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResBasic {
        private List<Todo> todoList;

        // 해당 static class 내부에서만 사용하는 클래스
        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        static class Todo {
            Integer idx;
            String content;
            Character doneYn;
        }

        // 엔티티에서 DTO로 변환, 팩토리 메소드
        public static ResBasic fromEntityList(List<TodoEntity> todoEntityList) {
            // map 기존 리스트 데이터를 다른 타입의 리스트 데이터로 변경
            List<Todo> todoList = todoEntityList.stream().map(todoEntity -> {
                return Todo.builder()
                    .idx(todoEntity.getIdx())
                    .content(todoEntity.getContent())
                    .doneYn(todoEntity.getDoneYn())
                    .build();
            }).collect(Collectors.toList());

            return new ResBasic(todoList);
        }
    }
}
