package com.example.server.todo.controller;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceDocumentation;
import com.example.server.todo.dto.TodoDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@Transactional
public class TodoControllerApiV2Test {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testSelectSuccess() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v2/todo"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(
                        MockMvcRestDocumentationWrapper.document("투두리스트 가져오기 성공", ResourceDocumentation.resource())
                );
    }

    @Test
    public void testSaveSuccess() throws Exception {

        TodoDTO.ReqBasic reqDto = TodoDTO.ReqBasic.builder()
                .content("연습이요!")
                .build();

        String reqDtoJson = objectMapper.writeValueAsString(reqDto);

        mockMvc.perform(RestDocumentationRequestBuilders.post("/api/v2/todo")
                        .content(reqDtoJson)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(
                        MockMvcRestDocumentationWrapper.document("할 일 추가하기 성공", ResourceDocumentation.resource())
                );
    }

    @Test
    public void testSaveFail() throws Exception {

        TodoDTO.ReqBasic reqDto = TodoDTO.ReqBasic.builder()
                .content(null)
                .build();

        String reqDtoJson = objectMapper.writeValueAsString(reqDto);

        mockMvc.perform(RestDocumentationRequestBuilders.post("/api/v2/todo")
                        .content(reqDtoJson)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(
                        MockMvcRestDocumentationWrapper.document("할 일 추가하기 실패", ResourceDocumentation.resource())
                );
    }

    @Test
    public void testUpdateSuccess() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders.put("/api/v2/todo/{idx}", 1)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(
                        MockMvcRestDocumentationWrapper.document("할 일 수정하기 성공", ResourceDocumentation.resource())
                );
    }

    @Test
    public void testUpdateFail() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders.put("/api/v2/todo/{idx}", -1)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(
                        MockMvcRestDocumentationWrapper.document("할 일 수정하기 실패", ResourceDocumentation.resource())
                );
    }

    @Test
    public void testDeleteSuccess() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders.delete("/api/v2/todo/{idx}", 1)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(
                        MockMvcRestDocumentationWrapper.document("할 일 삭제하기 성공", ResourceDocumentation.resource())
                );
    }

    @Test
    public void testDeleteFail() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders.delete("/api/v2/todo/{idx}", -1)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(
                        MockMvcRestDocumentationWrapper.document("할 일 삭제하기 실패", ResourceDocumentation.resource())
                );
    }
}
