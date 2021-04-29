<%-- 
    Document   : index
    Created on : Apr 12, 2021, 9:13:37 AM
    Author     : dell
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <c:if test="${empty requestScope.MESSAGE}">
        <script>
            window.alert('Hay login de thuc hien chuc nang mua hang');
        </script>
    </c:if>
    <body>
        <h1>Trang chu</h1>
        <a href="/HanaShop/login">Login</a>
        <form action="/HanaShop/search">
            Tìm kiếm:<input id="txtSearchName" type="text" name="searchName"/>
            Giá:<input type="range" value="0" id="rangeSelect" name="price" min="0" max="20000"/>
            <span id="txtPrice"></span>
            Loại:<select id="listCate" name="category">
                <option value="none"></option>
                <c:forEach items="${requestScope.LIST_CATE}" var="cate">
                    <option value="${cate.code}">${cate.name}</option>
                </c:forEach>
            </select>
            <input type="submit" value="Tìm"/>
            <span>
                <input id="radioName" type="radio" value="nameSearch" checked="true" name="radioChoice"/>Tên
                <input id="radioPrice" type="radio" value="priceSearch" name="radioChoice"/>Giá tiền
                <input id="radioCategory" type="radio" value="categorySearch" name="radioChoice"/>Loại
            </span>
        </form>
        <script>
            let rangeSelect = document.getElementById('rangeSelect');
            let txtPrice = document.getElementById('txtPrice');
            let txtSearchName = document.getElementById('txtSearchName');
            let listCate = document.getElementById('listCate');
            rangeSelect.oninput = function () {
                txtPrice.innerHTML = rangeSelect.value + " VNĐ";
                document.getElementById('radioPrice').checked = true;
            };
            txtSearchName.oninput = function () {
                document.getElementById('radioName').checked = true;
            }
            listCate.onchange = function () {
                document.getElementById('radioCategory').checked = true;
            }

        </script>
        <h1>List sản phẩm</h1>
        <table border="1">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Anh</th>
                    <th>Ten</th>
                    <th>Gia</th>
                    <th>Loai</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${requestScope.SEARCH_OBJECT.listFood}" var="food">
                    <tr>
                        <td>${food.id}</td>
                        <td><img style="height: 100px;width: 100px" src="/HanaShop/<c:out value="${food.image}"/>" /></td>
                        <td>${food.name}</td>
                        <td>${food.price}</td>
                        <td>${food.category}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <c:forEach begin="0" end="${requestScope.PAGE_QUANTITIES}" varStatus="loop">
            <c:choose>
                <c:when test="${not empty requestScope.SEARCH_OBJECT.radioChoice}">
                    <a href="/HanaShop/search?page=${loop.index}&radioChoice=<c:out value="${requestScope.SEARCH_OBJECT.radioChoice}"/>&<c:out value="${requestScope.SEARCH_OBJECT.typeOfSearch}" />=<c:out value="${requestScope.SEARCH_OBJECT.searchValue}"/>">${loop.index+1}</a>
                </c:when>
                <c:otherwise>
                    <a href="/HanaShop/search?page=${loop.index}">${loop.index+1}</a>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </body>
</html>
