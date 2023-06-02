package com.example.project.todo.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.project.common.Controller;
import com.example.project.todo.dto.TodoDTO;
import com.example.project.todo.service.TodoService;

public class TodoController implements Controller {
	public static String PATH = "/todoList";
	
	private final TodoService todoService;
	
	public TodoController(TodoService todoService) {
		this.todoService = todoService;
	}

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		if (request.getMethod().equals("GET")) {
			
			List<TodoDTO.ResBasic> todoList = todoService.findByDeleteYn("N");
			Long todoSize = todoList.stream().filter(todoDto -> todoDto.getDoneYn().equals("N")).count();
			
			request.setAttribute("todoList", todoList);
			request.setAttribute("todoSize", todoSize);
			
			RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/views/todoList2.jsp");
			
			rd.forward(request, response);
			return;
		}
		
	}

}
