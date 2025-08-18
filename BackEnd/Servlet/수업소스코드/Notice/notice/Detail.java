package com.simple.notice;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/notice/detail")
public class Detail extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		int id_ = Integer.parseInt(req.getParameter("id"));
		
		String url = "jdbc:mysql://localhost:3306/notice";
		String id = "root";
		String pw = "pjc0129";
		String sql = "select * from notice where id=?";
		
		// DB 커넥션 작업
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn =  DriverManager.getConnection(url,id,pw);
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id_);
			
			ResultSet rs = pstmt.executeQuery();
			rs.next();// 해당 ID값으로 이동하여 꺼낼 데이터의 주소를 가르킨다.
			
			// 모델화 작업(화면에 출력할 데이터 준비)
			String title = rs.getString("TITLE");
			Date regDate = rs.getDate("REGDATE");
			String writerId = rs.getString("WRITER_ID");
			String hit = rs.getString("HIT");
			String files = rs.getString("FILES");
			String content = rs.getString("CONTENT");
			
			// request 객체에 모델을 담음
			req.setAttribute("id", id_);
			req.setAttribute("title", title);
			req.setAttribute("regdate", regDate);
			req.setAttribute("writerId", writerId);
			req.setAttribute("hit", hit);
			req.setAttribute("files", files);
			req.setAttribute("content", content);
			
			rs.close();
			pstmt.close();
			conn.close();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
	}

}
