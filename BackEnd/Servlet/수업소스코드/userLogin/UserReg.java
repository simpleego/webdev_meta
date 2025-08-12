package com.simple.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/user_reg")
public class UserReg extends HttpServlet {
	
	static Map<String, User> userDB = new HashMap<>();
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// 클라이언트로부터 전송된 데이터를 request 객체에서 꺼내서
		// DB에 저장 또는 기타 용도로 사용
		req.setCharacterEncoding("utf-8");
		res.setCharacterEncoding("utf-8");			
		res.setContentType("text/html; charset=utf-8");
		
		
		PrintWriter out = res.getWriter();
		
		String id = req.getParameter("id");
		String password = req.getParameter("password");
		String name = req.getParameter("name");
		String tel = req.getParameter("tel");
		String addr = req.getParameter("addr");
		
		User user = new User(id, password, name, tel, addr);
		
		userDB.put(id, user);		
		
		System.out.println("user : "+user);
		System.out.println("users : "+userDB);
		
	}

}
