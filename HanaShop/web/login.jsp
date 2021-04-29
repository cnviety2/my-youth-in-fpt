<%-- 
    Document   : welcome
    Created on : Apr 12, 2021, 7:48:47 AM
    Author     : dell
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <c:if test="${not empty sessionScope.USER}">
        <c:choose>
            <c:when test="${sessionScope.USER.role eq 'ROLE_USER'}" >
                <c:redirect url="user/home.jsp"/>
            </c:when>
            <c:when test="${sessionScope.USER.role eq 'ROLE_ADMIN'}" >
                <c:redirect url="admin/home.jsp"/>
            </c:when>
        </c:choose>
    </c:if>
    <c:set var="ggClientID" value="675619263245-lbgorqknh49lceku47t5kq1k96rd5l02.apps.googleusercontent.com" />
    <body class="containter">
        <h1>Login</h1>
        <form action="/HanaShop/login" method="POST">
            UserID:<input type="text" name="userID" required="true"/></br>
            Password:<input type="password" name="password" required="true"/>
            <input type="submit" value="Login"/>
        </form>
        <div class="row">
            <div class="col-md-3">
                <a class="btn btn-outline-dark" href="https://accounts.google.com/o/oauth2/auth?scope=email&redirect_uri=http://localhost:8080/HanaShop/login-google&response_type=code
                   &client_id=${ggClientID}&approval_prompt=force" role="button" style="text-transform:none">
                    <img width="20px" style="margin-bottom:3px; margin-right:5px" alt="Google sign-in" src="https://upload.wikimedia.org/wikipedia/commons/thumb/5/53/Google_%22G%22_Logo.svg/512px-Google_%22G%22_Logo.svg.png" />
                    Login with Google
                </a>
            </div>
        </div>
        <a href="/HanaShop/index.jsp">Trang chu</a>
    </body>
    <link rel="stylesheet" type="text/scss" href="newcss.css"/>
    <link rel="stylesheet" type="text/css" href="//fonts.googleapis.com/css?family=Open+Sans" />
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css">
    <!-- Thư viện jquery đã nén phục vụ cho bootstrap.min.js  -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <!-- Thư viện popper đã nén phục vụ cho bootstrap.min.js -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.6/umd/popper.min.js"></script>
    <!-- Bản js đã nén của bootstrap 4, đặt dưới cùng trước thẻ đóng body-->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js"></script>
</html>
