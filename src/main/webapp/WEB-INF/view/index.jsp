<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>학생 정보 관리</title>
</head>
<body>
    <c:forEach var="element" items="${studentMap}">
        <c:set var="id" value="${element.key}"/>
        <c:set var="student" value="${element.value}"/>
        <div>
            <span>${student.name}</span>
            <span><a href="/student/${id}?hideScore=yes">성적 제외 조회</a></span>
            <span><a href="/student/${id}">성적 포함 조회</a></span>
        </div>
    </c:forEach>
</body>
</html>
