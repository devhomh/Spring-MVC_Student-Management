<%--
  Created by IntelliJ IDEA.
  User: lee
  Date: 1/9/24
  Time: 9:34 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>학생 정보 관리</title>
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
        <th scope="row">나이</th>
        <td>${student.age}</td>
    </tr>
    <tr>
        <th scope="row">등록일</th>
        <td>${student.createAt}</td>
    </tr>
    </tbody>
</table>
</body>
</html>
