package com.simple.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet implementation class Calc
 */
@WebServlet("/hello")
public class Calc extends HttpServlet {

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		
		out.println("Hello Servlet<br>");
		out.println("<h1>Hello Servlet</h1>");
		out.println("Hello Servlet");
		out.println("Hello Servlet");
		out.println("Hello Servlet");
		out.println("Hello Servlet");
		out.println("Hello Servlet");
		out.println("Hello Servlet");
		out.println("Hello Servlet");
		
	}
	
}
