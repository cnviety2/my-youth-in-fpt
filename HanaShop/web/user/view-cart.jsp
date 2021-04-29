<%-- 
    Document   : view-cart
    Created on : Apr 14, 2021, 7:17:45 AM
    Author     : dell
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Cart Page</title>
    </head>
    <c:if test="${not empty requestScope.MESSAGE}">
        <script>
            window.alert('${requestScope.MESSAGE}');
        </script>
    </c:if>
    <body>
        <h1>Your cart</h1>
        <table border="1">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Ten san pham</th>
                    <th>Gia</th>
                    <th>So luong</th>
                </tr>
            </thead>
            <tbody>
                <c:set var="total" value="${0}"/>
                <c:set var="row" value="${0}"/>
                <c:forEach items="${sessionScope.CART}" var="cart">
                    <tr>
                        <td>${cart.id}</td>
                        <td>${cart.name}</td>
                        <td>${cart.price}</td>
                        <td>${cart.amount}</td>
                        <td>
                            <form action="/HanaShop/user/cart/update" method="POST">
                                <input type="hidden" name="action" value="inc"/>
                                <input type="hidden" name="id" value="${cart.id}"/>
                                <input type="hidden" name="amount" value="${cart.amount}"/>
                                <input type="submit" value="+"/>
                            </form>
                            <form action="/HanaShop/user/cart/update" method="POST">
                                <input type="hidden" name="action" value="dec"/>
                                <input type="hidden" name="id" value="${cart.id}"/>
                                <input type="hidden" name="amount" value="${cart.amount}"/>
                                <input type="submit" value="-"/>
                            </form>
                        </td>
                        <td>
                            <form onSubmit="return confirm('Thực hiện delete ?')" action="/HanaShop/user/cart" method="POST">
                                <input type="hidden" value="${cart.id}" name="idDele"/>
                                <input type="hidden" value="delete" name="action"/>
                                <input type="submit" value="Xoa khoi gio hang"/>
                            </form>
                        </td>
                    </tr>
                    <c:set var="total" value="${ total + (cart.price * cart.amount)}"/>
                    <c:set var="row" value="${row + 1}"/>
                </c:forEach>
            </tbody>
        </table>
        <h1>Tong tien:${total}</h1>
        <form onSubmit="return confirm('Đơn hàng của bạn là ${total}VNĐ,thực hiện thanh toán ?')" action="/HanaShop/user/cart" method="POST">
            <input type="hidden" name="action" value="pay"/>
            <input type="hidden" name="totalPrice" value="${total}"/>
            <input type="submit" value="Thanh toan"/>
        </form>
        <form action="/HanaShop/user">
            <input type="submit" value="Quay lai"/>
        </form>
    </body>
</html>
