package com.example.project.todo.controller;

import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.project.common.Controller;
import com.example.project.common.JsonConverter;
import com.example.project.common.ResDTO;
import com.example.project.todo.dto.TodoDTO;
import com.example.project.todo.service.TodoApiV1Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TodoApiV1Controller implements Controller {
	public static String PATH = "/api/v1/todoList";
	
	private final TodoApiV1Service todoApiV1Service;
	
	public TodoApiV1Controller(TodoApiV1Service todoApiV1Service) {
		this.todoApiV1Service = todoApiV1Service;
	}

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
//		  String queryString = request.getParameter("name");
//        String pathVariable = request.getRequestURI().replace(PATH + "/", "");
//        String requestBody = request.getReader().lines().collect(Collectors.joining());
		
		System.out.println(request.getMethod());
		
		if (request.getMethod().equals("GET")) {
			
			ResDTO<?> resDTO = ResDTO.builder()
				.code(-1)
				.message("허용되지 않은 메소드 입니다.")
				.build();

	        sendJsonResponse(response, HttpServletResponse.SC_METHOD_NOT_ALLOWED, resDTO);
			
		} else if (request.getMethod().equals("POST")) {
			
			String json = request.getReader().lines().collect(Collectors.joining());
			TodoDTO.ReqBasic todoDtoReqBasic = JsonConverter.fromJson(json, TodoDTO.ReqBasic.class);
			
			ResDTO<?> resDTO = todoApiV1Service.insert(todoDtoReqBasic);
			
			if (resDTO.getCode().equals(0)) {
				sendJsonResponse(response, HttpServletResponse.SC_OK, resDTO);
			} else if (resDTO.getCode().equals(1)) {
				sendJsonResponse(response, HttpServletResponse.SC_BAD_REQUEST, resDTO);
			} else {
				sendJsonResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, resDTO);
			}
			
		} else if (request.getMethod().equals("PUT")) {
			
			String json = request.getReader().lines().collect(Collectors.joining());
			TodoDTO.ReqUpdate todoDtoReqUpdate = JsonConverter.fromJson(json, TodoDTO.ReqUpdate.class);
			
			ResDTO<?> resDTO = todoApiV1Service.updateDoneYn(todoDtoReqUpdate);
			
			if (resDTO.getCode().equals(0)) {
				sendJsonResponse(response, HttpServletResponse.SC_OK, resDTO);
			} else if (resDTO.getCode().equals(1)) {
				sendJsonResponse(response, HttpServletResponse.SC_BAD_REQUEST, resDTO);
			} else {
				sendJsonResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, resDTO);
			}
			
			
		} else if (request.getMethod().equals("DELETE")) {
			
			String json = request.getReader().lines().collect(Collectors.joining());
			TodoDTO.ReqDelete todoDtoReqDelete = JsonConverter.fromJson(json, TodoDTO.ReqDelete.class);
			
			ResDTO<?> resDTO = todoApiV1Service.updateDeleteYn(todoDtoReqDelete);
			
			if (resDTO.getCode().equals(0)) {
				sendJsonResponse(response, HttpServletResponse.SC_OK, resDTO);
			} else if (resDTO.getCode().equals(1)) {
				sendJsonResponse(response, HttpServletResponse.SC_BAD_REQUEST, resDTO);
			} else {
				sendJsonResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, resDTO);
			}
			
		}
		
	}

	private void sendJsonResponse(HttpServletResponse response, int status, Object object) throws IOException, JsonProcessingException {
		response.setStatus(status);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		response.getWriter().print(JsonConverter.toJson(object));
	}

}
