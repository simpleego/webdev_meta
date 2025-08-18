<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>BMI 계산기</title>
    <style>
        .result {
            margin-top: 20px;
            font-size: 1.2em;
        }
        .normal { color: green; font-weight: bold; }
        .underweight { color: blue; }
        .overweight { color: red; }
    </style>
</head>
<body>
    <h2>BMI 계산기</h2>
    <form action="bmi-calc" method="post">
        키(cm): <input type="text" name="height" value="<%= request.getAttribute("height") != null ? request.getAttribute("height") : "" %>"><br>
        몸무게(kg): <input type="text" name="weight" value="<%= request.getAttribute("weight") != null ? request.getAttribute("weight") : "" %>"><br>
        <button type="submit">계산하기</button>
    </form>

    <% if (request.getAttribute("bmi") != null) { %>
        <div class="result">
            <% String status = (String)request.getAttribute("status"); %>
            <% String bmiStr = (String)request.getAttribute("bmi"); %>
            
            <%
                String statusClass = "";
                if (status.equals("정상")) {
                    statusClass = "normal";
                } else if (status.equals("저체중")) {
                    statusClass = "underweight";
                } else {
                    statusClass = "overweight";
                }
            %>

            당신의 신체질량지수(BMI)는 **<%= bmiStr %>** 이며, <span class="<%= statusClass %>"><%= status %></span>입니다.
        </div>
    <% } %>
</body>
</html>