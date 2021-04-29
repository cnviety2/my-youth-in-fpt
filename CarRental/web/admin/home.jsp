<%-- 
    Document   : home
    Created on : Apr 22, 2021, 3:06:34 PM
    Author     : dell
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Home Page</title>
    </head>
    <c:if test="${not empty requestScope.MESSAGE}">
        <script>
            window.alert('${requestScope.MESSAGE}');
        </script>
    </c:if>
    <body>
        <form action="/CarRental/logout">
            <input type="submit" value="Logout"/>
        </form>
        <h1>Hello ${sessionScope.USER.name}</h1>
    </body>
</html>
