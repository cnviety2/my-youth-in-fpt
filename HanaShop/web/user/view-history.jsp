<%-- 
    Document   : view-history
    Created on : Apr 14, 2021, 3:47:44 PM
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
        <form action="/HanaShop/user">
            <input type="submit" value="Quay lai"/>
        </form>
        <h1>Lich su mua hang cua ban</h1>
        <form action="/HanaShop/user/history/search">
            <p>Ten san pham:</p>
            <input name="name" type="text"/></br>
            <p>Ngay mua</p>
            <select name="date">
                <option value="none"></option>
                <c:forEach items="${requestScope.LIST_HISTORY_PAYMENT_DATE}" var="date">
                    <option value="${date}">${date}</option>
                </c:forEach>
            </select>
            <input type="submit" value="Tim"/>   
        </form>    
        <c:forEach items="${requestScope.HISTORY}" var="historyPayment">
            <h1>Don hang ID:${historyPayment.id}</h1>
            <p>Ngay mua hang:${historyPayment.paymentDate}</p>
            <table border="1">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Ten</th>
                        <th>Gia tien</th>
                        <th>So luong</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${historyPayment.listHistoryFood}" var="historyFood">
                        <tr>
                            <td>${historyFood.id}</td>
                            <td>${historyFood.name}</td>
                            <td>${historyFood.price}</td>
                            <td>${historyFood.quantity}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <p>Tong tien cua don hang:${historyPayment.totalPrice}</p>
        </c:forEach>
    </body>
</html>
