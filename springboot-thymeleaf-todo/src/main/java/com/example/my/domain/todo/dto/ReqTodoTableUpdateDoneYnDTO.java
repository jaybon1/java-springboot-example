package com.example.my.domain.todo.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ReqTodoTableUpdateDoneYnDTO {

    @Valid
    @NotNull(message = "내용을 양식에 맞게 입력해주세요.")
    private Todo todo;

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    public static class Todo {
        @NotBlank(message = "완료 여부를 입력해주세요.")
        private Character doneYn;
    }

}
