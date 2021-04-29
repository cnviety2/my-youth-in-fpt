<%-- 
    Document   : home
    Created on : Apr 12, 2021, 9:09:50 AM
    Author     : dell
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib  prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
        <form action="/HanaShop/logout">
            <input type="submit" value="Logout"/>
        </form>
        <h1>Hello <c:out value="${sessionScope.USER.userID}"/></h1>
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
                    <th>So luong</th>
                    <th>Trang thai</th>
                    <th>Ngay tao</th>
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
                        <td>${food.quantity}</td>
                        <td>
                            <c:if test="${food.status == true}">
                                <c:out value="Dang ban"/>
                            </c:if>
                            <c:if test="${food.status == false}">
                                <c:out value="Ngung ban"/>
                            </c:if>
                        </td>
                        <td>
                            <fmt:formatDate value="${food.createDate}" type="date" pattern="yy-MMM-dd"/>
                        </td>
                        <td><a href="/HanaShop/admin/update?id=<c:out value="${food.id}"/>">Update</a></td>
                        <td>
                            <form onSubmit="return confirm('Thực hiện delete ?')" action="/HanaShop/admin/delete">
                                <input type="submit" value="Delete" />
                                <input type="hidden" value="<c:out value="${food.id}"/>" name="id"/>
                            </form>
                        </td>
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
        <h1>Thêm mới thức ăn</h1>
        <form action="/HanaShop/admin/add" method="POST" enctype="multipart/form-data">
            Tên:<input type="text" required="true" name="name"/></br>
            Giá:<input type="number" required="true" name="price"/></br>
            Loại:<select name="category">
                <c:forEach items="${requestScope.LIST_CATE}" var="cate">
                    <option value="${cate.code}">${cate.name}</option>
                </c:forEach>
            </select></br>
            Ảnh:<input type="file" accept="image/png, image/jpeg" name="image"></br>
            <input type="submit" value="Thêm mới"/>
        </form>
    </body>
</html>
