package com.simple.web;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/notice-reg")
public class NoticeReg extends HttpServlet {
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// 사용자에게 보내는 방식을 결정
		//res.setCharacterEncoding("UTF-8");
		//req.setCharacterEncoding("UTF-8");
		// Content 해석 방식을 알려줌
		res.setContentType("text/html; charset=UTF-8");		
		PrintWriter out = res.getWriter();
		
		// 클라이언트로 부터 전달되는 파라미터를 받기
		String title = req.getParameter("title");
		String content = req.getParameter("content");
		
		out.print("제목: "+title+"<br>");
		out.print("<p> 내용 :"+content+"</p>");		
		
	}

}
