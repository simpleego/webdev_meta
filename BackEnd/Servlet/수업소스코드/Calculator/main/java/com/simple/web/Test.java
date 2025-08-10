package com.simple.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet implementation class Test
 */
@WebServlet("/test")
public class Test extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 사용자에게 보내는 방식을 결정
		response.setCharacterEncoding("UTF-8");
		// Content 해석 방식을 알려줌
		response.setContentType("text/html; charset=UTF-8");		
		PrintWriter out = response.getWriter();
		
		int cnt=3;
		String cnt_ = request.getParameter("cnt");
		
		if(cnt_ != null && !cnt_.equals("")) {			
			cnt = Integer.parseInt(cnt_);
		}
		
		for (int i = 0; i <cnt; i++) {
			out.println("<h1>"+(i+1)+" : 안녕 서블릿</h1>");		
		}
		
	}

}
