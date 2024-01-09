<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>학생 정보 관리</title>
</head>
<body>
    <c:forEach var="element" items="${studentMap}">
        <c:set var="studentId" value="${element.key}"/>
        <c:set var="student" value="${element.value}"/>
        <div>
            <span>${student.name}</span>
            <span><a href="/student/${studentId}">성적 포함 조회</a></span>
            <span><a href="/student/${studentId}?hideScore=yes">성적 제외 조회</a></span>
        </div>
    </c:forEach>

    <div>
        <button onclick="location.href='/student/register'">등록</button>
    </div>
</body>
</html>
