package com.example.project.common;

import javax.servlet.http.HttpServletRequest;

public class PathParser {
	public static String getReqeustPath(HttpServletRequest request) {
		String requestURI = request.getRequestURI();
	    String contextPath = request.getContextPath();
	    String path = requestURI.substring(contextPath.length());
		return path;
	}
}
