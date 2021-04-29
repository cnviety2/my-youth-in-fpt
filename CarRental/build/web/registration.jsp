<%-- 
    Document   : registration
    Created on : Apr 17, 2021, 7:52:21 AM
    Author     : dell
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registration Page</title>
    </head>
    <c:if test="${not empty requestScope.MESSAGE}">
        <script>
            window.alert('${requestScope.MESSAGE}');
        </script>
    </c:if>
    <body>
        <h1>Dang ky</h1>
        <a href="/CarRental/login.jsp">Quay lai</a>
        <form action="/CarRental/registration" method="POST">
            Ten:<input name="name" type="text" required="true" value="${requestScope.name}"/></br>
            Email:<input name="email" type="email" required="true" value="${requestScope.email}"/></br>
            Password:<input name="password" type="password" required="true"/></br>
            Nhap lai password:<input name="rePassword" type="password" required="true"/></br>
            So dien thoai:<input name="phone" type="number" required="true"/></br>
            Dia chi:<input name="address" type="text" required="true"/></br>
            <input type="submit" value="Dang ky"/>
        </form>
    </body>
</html>
