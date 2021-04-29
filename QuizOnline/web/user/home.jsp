<%-- 
    Document   : home
    Created on : Apr 16, 2021, 4:22:40 PM
    Author     : dell
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User Home Page</title>
    </head>
    <c:if test="${not empty requestScope.MESSAGE}">
        <script>
            window.alert('${requestScope.MESSAGE}');
        </script>
    </c:if>
    <body>
        <form action="/QuizOnline/logout">
            <input type="submit" value="Logout"/>
        </form>
        <h1>Take a quiz,${sessionScope.USER.name}?</h1>
        <form action="/QuizOnline/user/quiz" method="POST">
            <select name="quizSubject">
                <c:forEach items="${sessionScope.LIST_SUBJECT}" var="subject">
                    <option value="${subject.id}">${subject.subject}</option>
                </c:forEach>
            </select>
            <input type="submit" value="Lam quiz"/>
        </form></br>
        <form action="/QuizOnline/user/view-history" method="POST">
            <input type="hidden" name="page" value="0"/>
            <input type="submit" value="Xem lich su"/>
        </form>
    </body>
</html>
