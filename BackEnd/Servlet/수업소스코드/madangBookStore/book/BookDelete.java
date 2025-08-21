package com.simple.madang;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/book/delete")
public class BookDelete extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");

		int bookid = Integer.parseInt(req.getParameter("id"));

		String url = "jdbc:mysql://localhost:3306/madangdb";
		String id = "root";
		String pw = "pjc0129";
		String sql = "delete from book where bookid = ?";

		// DB 커넥션 작업
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, id, pw);
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bookid);

			pstmt.executeUpdate();
			// 해당 ID값으로 이동하여 꺼낼 데이터의 주소를 가르킨다.

			pstmt.close();
			conn.close();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		resp.sendRedirect("/book/list");
	}

}
