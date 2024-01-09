<%--
  Created by IntelliJ IDEA.
  User: lee
  Date: 1/9/24
  Time: 9:57 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>학생 조회</title>
</head>
<body>
    <table>
        <tbody>
        <tr>
            <th scope="row">아이디</th>
            <td>${student.id}</td>
        </tr>
        <tr>
            <th scope="row">이름</th>
            <td>${student.name}</td>
        </tr>
        <tr>
            <th scope="row">이메일</th>
            <td>${student.email}</td>
        </tr>
        <tr>
            <th scope="row">점수</th>
            <td>${student.score}</td>
        </tr>
        <tr>
            <th scope="row">성적</th>
            <td>${student.grade}</td>
        </tr>
        </tbody>
    </table>
</body>
</html>
