<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>

<body>
    <a>
        <%@include file="templates/SecurityHeader.jsp" %>

        <a href="jeju/test">Test Page</a>
        <a href="jeju/adminOnly">
            <sec:authorize access="hasRole('ADMIN')">adminOnly Page
        </a></sec:authorize>
        <a href="login">
            <sec:authorize access="!isAuthenticated()">Login
        </a></sec:authorize>
        <a href="logout">
            <sec:authorize access="isAuthenticated()">Logout
        </a></sec:authorize>
        <sec:authorize access="hasRole('ADMIN')">
            <p>[이 부분은 운영자(ADMIN)에게만 나타남]</p>
        </sec:authorize>
        <sec:authorize access="hasRole('GUEST')">
            <p>[이 부분은 손님(GUEST)에게만 나타남]</p>
        </sec:authorize>
        <sec:authorize access="isAuthenticated()">
            <p>[이 부분은 로그인한 사용자(isAuthenticated) 모두에게 나타남]</p>
        </sec:authorize>
    </a>
</body>

</html>