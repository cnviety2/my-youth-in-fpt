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
    <!-- reCAPTCHA Libary -->
    <script src='https://www.google.com/recaptcha/api.js?hl=en'></script>
    <body>
        <a href="/CarRental/index.jsp">Quay lai trang chu</a>
        <h1>Login</h1>
        <form action="/CarRental/login" method="POST">
            Email:<input name="email" type="email" required="true"/></br>
            Password:<input name="password" type="password" required="true"/>
            <!-- reCAPTCHA -->
            <div class="g-recaptcha"
                 data-sitekey="6Lc50bcaAAAAADx4y-ObuICDheUfty9DwOzXkPMZ"></div>
            <input type="submit" value="Login"/>
        </form>
        <a href="/CarRental/registration.jsp">Dang ky</a>
    </body>
</html>
