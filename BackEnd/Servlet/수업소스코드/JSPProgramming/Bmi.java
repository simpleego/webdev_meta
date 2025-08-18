package com.simple.web;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/bmi-calc")
public class Bmi extends HttpServlet {
	private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        double height = 0;
        double weight = 0;
        String status = "";
        double bmi = 0;

        try {
            // 1. 요청 파라미터 받기
            String heightStr = request.getParameter("height");
            String weightStr = request.getParameter("weight");
            
            // 2. 입력값 유효성 검사 및 변환
            height = Double.parseDouble(heightStr);
            weight = Double.parseDouble(weightStr);

            // 3. 비즈니스 로직 (BMI 계산)
            double heightM = height / 100.0;
            bmi = weight / (heightM * heightM);

            // 4. BMI 상태 판정
            if (bmi < 18.5) {
                status = "저체중";
            } else if (bmi < 23) {
                status = "정상";
            } else if (bmi < 25) {
                status = "비만전단계";
            } else if (bmi < 30) {
                status = "1단계 비만";
            } else if (bmi < 35) {
                status = "2단계 비만";
            } else {
                status = "3단계 비만";
            }
        } catch (NumberFormatException e) {
            // 에러 발생 시 처리
            status = "입력 오류";
        }

        // 5. 결과를 request 객체에 저장
        request.setAttribute("height", height);
        request.setAttribute("weight", weight);
        request.setAttribute("bmi", String.format("%.1f", bmi));
        request.setAttribute("status", status);

        // 6. JSP로 포워딩
        request.getRequestDispatcher("/bmi.jsp").forward(request, response);
    }
}
