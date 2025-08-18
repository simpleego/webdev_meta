package com.simple.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/spag")
public class Spag extends HttpServlet {
		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			
			String num_ =  req.getParameter("n");
			int num = 0;
			
			if(!num_.equals("")) {
				num = Integer.parseInt(num_);						
			}
			
			String result="짝수";
			
			if(num %2 != 0) {
				result = "홀수";
			}
			
			// model 값을 저장소에 저장하고 클라이언트에 전송
			// view를 담당하고 있는 jsp에 전달
			
			req.setAttribute("result", result);
			
			// 배열 전달
			String[] names = {"newlec", "dragon"};
			req.setAttribute("names", names);
			
			// Map 전달
			Map<String, Object> notice = new HashMap<>();
			notice.put("id", 1);
			notice.put("title", "EL GOOD");
			notice.put("name", "EL best");
			
			req.setAttribute("notice", notice);					
			
			RequestDispatcher dispatcher  = req.getRequestDispatcher("spag.jsp");
			dispatcher.forward(req, resp);			
			
		}
}
