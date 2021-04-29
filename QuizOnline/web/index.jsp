<%-- 
    Document   : index
    Created on : Apr 16, 2021, 9:25:57 AM
    Author     : dell
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
    </head>
    <c:if test="${not empty requestScope.MESSAGE}">
        <script>
            window.alert('${requestScope.MESSAGE}');
        </script>
    </c:if>
    <body>
        <h1>Login</h1>
        <form action="/QuizOnline/login" method="POST">
            Email:<input name="email" type="email" required="true"/></br>
            Password:<input name="password" type="password" required="true"/>
            <input type="submit" value="Login"/>
        </form>
        <a href="/QuizOnline/registration.jsp">Dang ky</a>
    </body>
</html>
