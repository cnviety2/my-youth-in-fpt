<%-- 
    Document   : index
    Created on : Apr 23, 2021, 9:28:50 AM
    Author     : dell
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home Page</title>
    </head>
    <c:if test="${not empty requestScope.MESSAGE}">
        <script>
            window.alert('${requestScope.MESSAGE}');
        </script>
    </c:if>
    <body>
        <h1>Car renting</h1>
        <a href="/CarRental/login.jsp" >Dang nhap</a>
        <form action="/CarRental/search">
            Ten xe:<input type="text" name="name" value="${requestScope.SEARCH_NAME}"/>
            Loai xe:<input type="text" name="category" value="${requestScope.SEARCH_CATEGORY}"/>
            So ngay thue:<input type="number" name="rentalDate" value="${requestScope.SEARCH_RENTAL_DATE}"/>
            So xe con lai:<input type="number" name="quantity" value="${requestScope.SEARCH_QUANTITY}"/>
            <input type="submit" name="Tim"/>
        </form>
        <table border="1">
            <thead>
                <tr>
                    <th>Name</th>
                    <th>Color</th>
                    <th>Year</th>
                    <th>Category</th>
                    <th>Price</th>
                    <th>Quantity</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${requestScope.LIST_CAR}" var="car">
                    <tr>
                        <td>${car.name}</td>
                        <td>${car.color}</td>
                        <td>${car.year}</td>
                        <td>${car.category}</td>
                        <td>${car.price}</td>
                        <td>${car.quantity}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <c:forEach begin="0" end="${requestScope.TOTAL_PAGES}" varStatus="loop">
            <c:choose>
                <c:when test="${requestScope.STANDING_PAGE == loop.index}">
                    ${loop.index + 1}
                </c:when>
                <c:otherwise>
                    <a href="/CarRental/search?name=${requestScope.SEARCH_NAME}&category=${requestScope.SEARCH_CATEGORY}&rentalDate=${requestScope.SEARCH_RENTAL_DATE}&quantity=${requestScope.SEARCH_QUANTITY}&page=${loop.index}">${loop.index + 1}</a>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </body>
</html>
