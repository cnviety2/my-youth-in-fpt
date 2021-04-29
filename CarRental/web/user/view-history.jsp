<%-- 
    Document   : view-history
    Created on : Apr 24, 2021, 8:14:42 PM
    Author     : dell
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>History Page</title>
    </head>
    <c:if test="${not empty requestScope.MESSAGE}">
        <script>
            window.alert('${requestScope.MESSAGE}');
        </script>
    </c:if>
    <body>
        <a href="/CarRental/user">Quay lai</a>
        <h1>Lich su thue xe cua ban</h1>
        <form action="/CarRental/user/search-history">
            Ten xe:<input type="text" name="carName"/>
            Ngay thue:<select name="orderDate">
                <option value=""></option>
                <c:forEach items="${sessionScope.LIST_ORDER_DATE}" var="orderDate">
                    <option value="${orderDate}">${orderDate}</option>
                </c:forEach>
            </select>
            <input type="submit" value="Tim"/>
        </form>
        <c:if test="${requestScope.LIST_HISTORY_SIZE > 0}">
            <c:forEach items="${requestScope.LIST_HISTORY}" var="historyObj">
                <h2>Don hang id:${historyObj.id}</h2>
                <p>Ngay thue xe:${historyObj.orderDate}</p>
                <c:choose>
                    <c:when test="${historyObj.status == true}">
                        <p>Trang thai don hang:van con hoat dong</p>
                        <form onSubmit="return confirm('Ban muon huy thue xe?')" action="/CarRental/user/update-history" method="POST">
                            <input type="submit" value="Huy thue xe"/>
                            <input type="hidden" name="id" value="${historyObj.id}"/>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <p>Trang thai don hang:don hang da bi huy</p>
                    </c:otherwise>
                </c:choose>
                <table border="1">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Expired date</th>
                            <th>Status</th>
                            <th>Quantity</th>
                            <th>Price</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${historyObj.listCar}" var="car">
                            <tr>
                                <td>${car.carID}</td>
                                <td>${car.carName}</td>
                                <td>${car.expiredDate}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${car.expired == true}">
                                            Xe da het han thue
                                        </c:when>
                                        <c:otherwise>
                                            Xe van con han thue
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>${car.quantity}</td>
                                <td>${car.price}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:forEach>
        </c:if>
    </body>
</html>
