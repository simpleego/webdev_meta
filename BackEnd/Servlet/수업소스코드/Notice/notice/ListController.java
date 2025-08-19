package com.simple.notice;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.simple.notice.entity.Notice;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/notice/list")
public class ListController extends HttpServlet {		
		
		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			
			String url = "jdbc:mysql://localhost:3306/notice";
			String id = "root";
			String pw = "pjc0129";
			String sql = "select * from notice";
			
			List<Notice> list = new ArrayList<>();	
			
			// DB 커넥션 작업
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection conn =  DriverManager.getConnection(url,id,pw);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);				
							
				// 반복처리
				// 객체화
				// 컬렉션에 저장				
				while(rs.next()) {
					
					// 모델화 작업(화면에 출력할 데이터 준비)
					int id_ = rs.getInt("ID");
					String title = rs.getString("TITLE");
					Date regDate = rs.getDate("REGDATE");
					String writerId = rs.getString("WRITER_ID");
					String hit = rs.getString("HIT");
					String files = rs.getString("FILES");
					String content = rs.getString("CONTENT");
					
					//  Notice 객체로 변환(구조화)
					Notice notice =  new Notice(id_, title, regDate, writerId, hit, files, content);
					
					list.add(notice);					
				}				
				
				rs.close();
				stmt.close();
				conn.close();
				
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			
			req.setAttribute("list", list);
			
			// View 처리 (모델을 화면처리 프로그램에게 전달)
			req.getRequestDispatcher("/notice/list.jsp").forward(req, resp);
			
		}
}
