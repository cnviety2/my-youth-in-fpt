<%-- 
    Document   : view-cart
    Created on : Apr 23, 2021, 6:33:41 PM
    Author     : dell
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cart Page</title>
    </head>
    <c:if test="${not empty requestScope.MESSAGE}">
        <script>
            window.alert('${requestScope.MESSAGE}');
        </script>
    </c:if>
    <body>
        <a href="/CarRental/user">Quay lai</a>
        <h1>Gio hang cua ban</h1>
        <c:if test="${not empty sessionScope.DISCOUNT_FLAG}">
            <form onSubmit="return confirm('Ban muon huy giam gia ?')" action="/CarRental/user/remove-discount" method="POST">
                <input type="submit" value="Huy giam gia"/>
            </form>
        </c:if>
        <table border="1">
            <thead>
                <tr>
                    <th>Name</th>
                    <th>Category</th>
                    <th>Price</th>
                    <th>Quantity</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${sessionScope.CART}" var="car">
                    <tr>
                        <td>${car.name}</td>
                        <td>${car.category}</td>
                        <td>${car.price}</td>
                        <td>${car.quantity}</td>
                        <c:if test="${empty sessionScope.DISCOUNT_FLAG}">
                            <td>
                                <form action="/CarRental/user/update-cart" method="POST">
                                    <input type="hidden" value="${car.id}" name="id"/>
                                    <input type="hidden" value="inc" name="action"/>
                                    <input type="submit" value="+"/>
                                </form>
                                <form action="/CarRental/user/update-cart" method="POST">
                                    <input type="hidden" value="${car.id}" name="id"/>
                                    <input type="hidden" value="dec" name="action"/>
                                    <input type="submit" value="-"/>
                                </form>
                            </td>
                            <td>
                                <form onSubmit="return confirm('Thực hiện delete ?')" action="/CarRental/user/cart" method="POST">
                                    <input type="hidden" name="id" value="${car.id}"/>
                                    <input type="hidden" name="action" value="delete"/>
                                    <input type="submit" value="Xoa khoi gio hang"/>
                                </form>
                            </td>
                        </c:if>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <c:if test="${sessionScope.CART_QUANTITY > 0 && empty sessionScope.DISCOUNT_FLAG}">
            <form action="/CarRental/user/discount" method="POST">
                Ma giam:<input type="text" name="discountCode"/>
                <input type="submit" value="Ap dung ma giam"/>
            </form>
        </c:if>
        <h1>Tong tien:${sessionScope.TOTAL}VNĐ</h1>
        <c:if test="${sessionScope.TOTAL > 0}">
            <form action="/CarRental/user/cart" method="POST">
                <input type="hidden" name="action" value="pay"/>
                <input type="submit" value="Thanh toan"/>
            </form>
        </c:if>
    </body>
</html>
