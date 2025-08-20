package com.simple.madang;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.simple.madang.entity.Book;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/book/list")
public class BookList extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String url = "jdbc:mysql://localhost:3306/madangdb";
		String id = "root";
		String pw = "pjc0129";
		String sql = "select * from book";

		List<Book> list = new ArrayList<>();

		// DB 커넥션 작업
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, id, pw);
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			// 반복처리
			// 객체화
			// 컬렉션에 저장
			while (rs.next()) {

				// 모델화 작업(화면에 출력할 데이터 준비)
				int id_ = rs.getInt("BOOKID");
				String name = rs.getString("BOOKNAME");
				String publisher = rs.getString("PUBLISHER");
				int price = rs.getInt("PRICE");

				// Notice 객체로 변환(구조화)
				Book book = new Book(id_, name, publisher, price);

				System.out.println("도서목록: " + book);
				list.add(book);
			}

			rs.close();
			stmt.close();
			conn.close();

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		req.setAttribute("list", list);

		System.out.println("도서목록: " + list);

		// View 처리 (모델을 화면처리 프로그램에게 전달)
		req.getRequestDispatcher("/book_list.jsp").forward(req, resp);
	}

}
