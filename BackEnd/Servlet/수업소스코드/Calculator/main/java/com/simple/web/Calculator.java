package com.simple.web;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/calc")
public class Calculator extends HttpServlet {
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		res.setCharacterEncoding("utf-8");
		res.setContentType("text/html; charset=UTF-8");
		
		PrintWriter out = res.getWriter();
		
		String num1_ = req.getParameter("num1");
		String num2_ = req.getParameter("num2");
		String operator = req.getParameter("operator");
		
		int num1 = Integer.parseInt(num1_);
		int num2 = Integer.parseInt(num2_);
		int result = 0;
		String op="";
		
		if(operator.equals("+")) {
			result = num1+num2;		
			op="덧셈";
		}else if(operator.equals("-")) {
			result = num1-num2;	
			op="뺄셈";
		}else if(operator.equals("x")) {
			result = num1*num2;	
			op="곱셈";
		}else if(operator.equals("/")) {
			if(num2 != 0)
				result = num1/num2;	
			op="나눗셈";
		}
		
		// 계산된 결과를 클라이언트로 전송
		out.print("<p>"+op+"결과는 "+result+"</p>");
	}

}
