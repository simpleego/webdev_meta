package com.simple.web;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/calc_cookie")
public class CalcCookie extends HttpServlet{
	
	static int count=0;
	String  name;	
	
	public CalcCookie() {
		name="홍길동";
		System.out.println("생성자 호출됩니다..");
	}


	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		// Application 저장소 준비
		//ServletContext application =  req.getServletContext();
		
		// Session 저장소 준비
		// HttpSession session = req.getSession();
		
		//CalcCookie.count++;
		count++;
		
		System.out.println(name);
		
		// Cookie 저장소 준비
		Cookie[] cookies =  req.getCookies();
		
		res.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");
		
		PrintWriter out = res.getWriter();
		
		// 클라이언트 웹브라우저로부터 전송되는 값을 수신
		// 웹브라우저에서 전송된 모든 내용은 서버에 전송되고
		// 서버의 서블릿 컨테이너에 보관된다.
		// 그 중에서 HttpServletRequest req객체에 저장된다.
		
		String value_ = req.getParameter("value");
		String operator = req.getParameter("operator");
		
		int value = 0;
		
		if(!value_.equals("")) {
			value = Integer.parseInt(value_);
		}
		
		// 계산 
		if(operator.equals("=")) {
			// 두개의 정수를 저장소에서 꺼내서 계산
			//int x = (int) application.getAttribute("value");
			// int x = (int) session.getAttribute("value");
			int x=0;
			for (Cookie cookie : cookies) {
				if(cookie.getName().equals("value")) {
					x = Integer.parseInt(cookie.getValue());
					break;
				}
			}
			
			int y = value;
			
			// String op = (String) application.getAttribute("op");
			// String op = (String) session.getAttribute("op");
			String op="";
			for (Cookie cookie : cookies) {
				if(cookie.getName().equals("op")) {
					op = cookie.getValue();
					break;
				}
			}			
			
			
			int result=0;
			
			if(op.equals("+")) {
				result = x + y;
			}else if(op.equals("-")){
				result = x - y;
			}else if(op.equals("x")){
				result = x * y;
			}else if(op.equals("/")){
				if(y != 0 ) {
					result = x / y;	
				}else {
					out.print("0으로 나눌 수 없습니다.");
					return;
				}
			}
			System.out.println("x:"+x);
			System.out.println("y:"+y);
			System.out.println("op:"+op);
			
			out.print(x+op+y+"="+result);			
			
		}else {
			
			// application 저장소에 변수값을 저장
			// application.setAttribute("value", value);
			// application.setAttribute("op", operator);	
			// session.setAttribute("value", value);
			// session.setAttribute("op", operator);	
			
			Cookie valueCookie =  new Cookie("value", String.valueOf(value));
			Cookie opCookie =  new Cookie("op", operator);
			
			// path 설정
			valueCookie.setPath("/calc_cookie");
			opCookie.setPath("/calc_cookie");
						
			// maxAge 설정 - 24시간 유지
			valueCookie.setMaxAge(60 * 60);
			opCookie.setMaxAge(24 * 60 * 60);
			
			res.addCookie(valueCookie);
			res.addCookie(opCookie);
			
			System.out.println("value : "+value);
			System.out.println("operator : "+operator);
			
			// Redirection
			res.sendRedirect("calc_cookie.html");
		}	
		
		System.out.println("===> "+count);
		
		
	}	

}
