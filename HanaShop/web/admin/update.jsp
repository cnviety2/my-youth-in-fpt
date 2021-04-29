<%-- 
    Document   : update
    Created on : Apr 13, 2021, 8:47:25 AM
    Author     : dell
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Page</title>
    </head>
    <body>
        <h1>Update food</h1>
        <form onSubmit="return confirm('Thực hiện update ?')" enctype="multipart/form-data" action="/HanaShop/admin/update?id=<c:out value="${requestScope.FOOD.id}"/>" method="POST">
            ID:<input type="text" readonly="true" value="${requestScope.FOOD.id}" /><br/>
            Tên:<input name="name" type="text" required="true" value="${requestScope.FOOD.name}"/><br/>
            Ảnh:<input type="file" accept="image/png, image/jpeg" name="image"></br>
            Giá:<input  name="price" type="number" required="true" value="${requestScope.FOOD.price}"/></br>
            Loại:<select name="category">
                <c:forEach items="${requestScope.LIST_CATE}" var="cate">
                    <c:choose >
                        <c:when test="${requestScope.FOOD.category eq cate.code}">
                            <option value="${cate.code}" selected="true">${cate.name}</option>
                        </c:when>
                        <c:otherwise>
                            <option value="${cate.code}">${cate.name}</option>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </select></br>
            Số lượng:<input name="quantity" type="number" required="true" value="${requestScope.FOOD.quantity}"/></br>
            <input type="submit" value="Update"/>
        </form>
            <form action="/HanaShop/admin">
                <input type="submit" value="Quay lai"/>
            </form>
    </body>
</html>
