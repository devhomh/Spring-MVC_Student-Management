<%--
  Created by IntelliJ IDEA.
  User: lee
  Date: 1/9/24
  Time: 9:58 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<c:choose>
    <c:when test="${empty student}">
        <c:set var="action" value="/student/register" />
    </c:when>
    <c:otherwise>
        <c:set var="action" value="/student/modify" />
    </c:otherwise>
</c:choose>
<form method="POST" action="${action}">
    <label for="id">ID</label>
    <input id="id" name="id" placeholder="abcd" type="text" value="${student.id}" <c:if test="${student != null}">disabled</c:if> required/>
    <br/>
    <label for="name">이름</label>
    <input id="name" name="name" placeholder="홍길동" type="text" value="${student.name}" required/>
    <br/>
    <label for="email">이메일</label>
    <input id="email" name="email" placeholder="abc@aaa.com" type="email" value="${student.email}" required/>
    <br/>
    <label for="score">점수</label>
    <input id="score" name="score" type="number" value="${student.score}" required/>
    <br/>
    <label for="comment">성적</label>
    <input id="comment" name="comment" type="text" value="${student.comment}" required/>
    <br/>
    <button type="submit">
        <c:choose>
            <c:when test="${empty student}">
                등록
            </c:when>
            <c:otherwise>
                수정
            </c:otherwise>
        </c:choose>
    </button>
</form>
</body>
</html>
