package com.example.project;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.project.common.Controller;
import com.example.project.common.DBConn;
import com.example.project.common.PathParser;
import com.example.project.todo.controller.TodoApiV1Controller;
import com.example.project.todo.controller.TodoController;
import com.example.project.todo.service.TodoApiV1Service;
import com.example.project.todo.service.TodoService;


@WebServlet("/")
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private TodoService todoService = new TodoService();
	private TodoApiV1Service todoApiV1Service = new TodoApiV1Service();
	
	private Map<String, Controller> controllerMap = new HashMap<>();
	
	public FrontController() throws SQLException {
		
		// DB 메모리에 띄움
		DBConn.init();
		
		// 컨트롤러 주입함
	    controllerMap.put(TodoController.PATH, new TodoController(todoService));
	    controllerMap.put(TodoApiV1Controller.PATH, new TodoApiV1Controller(todoApiV1Service));
	    
	}
	


	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String path = PathParser.getReqeustPath(request);
		System.out.println("요청 path : " + path);
		
		if (path.startsWith("/static")) {
			getStaticResource(request, response, path);
			return;
		}
		
	    getDynamicResoruce(request, response, path);

	}

	private Controller getController(String path) {
		
		Optional<String> optionalKey = controllerMap.keySet().stream().filter(key -> path.startsWith(key)).findFirst();
		
		if (optionalKey.isEmpty()) return null;
		
		return controllerMap.get(optionalKey.get());
	}

	private void getDynamicResoruce(HttpServletRequest request, HttpServletResponse response, String path)
			throws IOException, ServletException {
		Controller controller = getController(path);
	    
	    if (controller == null) {
	    	response.sendError(HttpServletResponse.SC_NOT_FOUND);
		    return;
	    }
	    
	    controller.handleRequest(request, response);
	}
	
	private void getStaticResource(HttpServletRequest request, HttpServletResponse response, String path) throws IOException, ServletException {
		InputStream inputStream = getServletContext().getResourceAsStream(path);
		if (inputStream == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		response.setContentType(getServletContext().getMimeType(path));
		OutputStream outputStream = response.getOutputStream();
		byte[] buffer = new byte[inputStream.available()];
		int bytesRead;
		while ((bytesRead = inputStream.read(buffer)) != -1) {
		    outputStream.write(buffer, 0, bytesRead);
		}
		inputStream.close();
		outputStream.close();
	}

}
