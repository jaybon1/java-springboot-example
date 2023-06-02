package com.example.project.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import com.example.project.todo.entity.TodoEntity;

public class TodoDTO {
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReqBasic {
        private String content;

        public TodoEntity toEntity() {
            return TodoEntity.builder()
                    .content(this.content)
                    .doneYn("N")
                    .deleteYn("N")
                    .createDate(LocalDateTime.now())
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReqUpdate {
        private Integer idx;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReqDelete {
        private Integer idx;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResBasic {
        private Integer idx;
        private String content;
        private String doneYn;
        private String deleteYn;

        public static ResBasic fromEntity(TodoEntity todoEntity) {
            return ResBasic.builder()
                    .idx(todoEntity.getIdx())
                    .content(todoEntity.getContent())
                    .doneYn(todoEntity.getDoneYn())
                    .deleteYn(todoEntity.getDeleteYn())
                    .build();
        }
    }
}
