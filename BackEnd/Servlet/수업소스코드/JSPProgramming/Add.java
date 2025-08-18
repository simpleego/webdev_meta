package com.simple.web;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/add")
public class Add extends HttpServlet {
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		String x_ = req.getParameter("x");
		String y_ = req.getParameter("y");
		String op = req.getParameter("op");
		
		System.out.println("x :"+x_);
		System.out.println("y :"+y_);
		System.out.println("op :"+op);
		
		int x=0;
		int y=0;
		int result=0;
		
		 x = Integer.parseInt(x_);
		 y = Integer.parseInt(y_);
		
		if(op.equals("+")) {
			result = x + y;			
		}else {
			result = x - y;			
		}
		
		// 웹브라우저에 출력(클라이언트에 결과를 전송 http기반으로)
		// res.getWriter().print("결과값"+result);
		// request 저장소를 통해 전달
		req.setAttribute("result", result);
		
		// Forward - 동일한 디렉토리 내 있기에 경로 지정 안함
		RequestDispatcher dispatcher
			= req.getRequestDispatcher("add.jsp");
		dispatcher.forward(req, res);
	}

}
