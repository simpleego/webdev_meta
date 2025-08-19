package com.simple.madang;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UserInsert extends HttpServlet{
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String id_ = req.getParameter("id");
		String name = req.getParameter("name");
		String addr = req.getParameter("addr");
		String tel = req.getParameter("tel");
		
		int userId = Integer.parseInt(id_);
		
		String url = "jdbc:mysql://localhost:3306/madangdb";
		String id = "root";
		String pw = "pjc0129";
		String sql = "insert into (id, name, addr, tel) values(?,?,?,?)";
		
		// DB 커넥션 작업
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn =  DriverManager.getConnection(url,id,pw);
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userId);
			pstmt.setString(2, name);
			pstmt.setString(3, addr);
			pstmt.setString(4, tel);
			
			pstmt.executeUpdate(sql);
			// 해당 ID값으로 이동하여 꺼낼 데이터의 주소를 가르킨다.
			
			pstmt.close();
			conn.close();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// View 처리 (모델을 화면처리 프로그램에게 전달)
		req.getRequestDispatcher("/WEB-INF/notice/user_list.jsp").forward(req, resp);
	}
	}
	

}
