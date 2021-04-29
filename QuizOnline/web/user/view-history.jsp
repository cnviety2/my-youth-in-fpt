<%-- 
    Document   : view-history
    Created on : Apr 20, 2021, 9:56:34 AM
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
    <body>
        <h1>Ket qua cua ban</h1>
        <a href="/QuizOnline/user/home.jsp">Quay lai</a>
        <c:forEach items="${requestScope.LIST_HISTORY}" var="historyObj">
            <h2>Ngay lam quiz: ${historyObj.history.createDate}</h2>
            <form action="/QuizOnline/user/view-history" method="POST">
                <select name="subjectID">
                    <option value="0"></option>
                    <option value="1">Prj311- Java Desktop</option>
                    <option value="2">Prj321- Java Web</option>
                </select>
                <input type="hidden" name="page" value="0"/>
                <input type="submit" value="Tim"/>
            </form>
            <p>Mon lam quiz:${historyObj.history.subjectID == 1 ? 'Prj311- Java Desktop' : 'Prj321- Java Web'}</p>
            <p>Diem:${historyObj.history.score}</p>
            <c:set var="no" value="${1}"/>
            <c:forEach items="${historyObj.answersOfStudent}" var="answerObj">
                <p>Cau so ${no}:${answerObj.questionContent}</p>
                <c:forEach items="${requestScope.LIST_ANSWER_FROM_DB}" var="answerFromDB">
                    <c:choose>
                        <c:when test="${answerFromDB.questionID eq answerObj.questionID && answerFromDB.id == answerObj.answerID}">
                            ${answerFromDB.answerContent}<input type="radio" readonly="true" disabled="true" checked="true"/></br>
                        </c:when>
                        <c:when test="${answerFromDB.questionID eq answerObj.questionID}">
                            ${answerFromDB.answerContent}<input type="radio" disabled="true" readonly="true"/></br>
                        </c:when>
                    </c:choose>
                </c:forEach>
                <c:set var="no" value="${no + 1}"/>
            </c:forEach>
        </c:forEach>
        <c:if test="${requestScope.TOTAL_PAGES > 1}">
            <!--            <form action="/QuizOnline/user/view-history" method="POST">
            <c:choose>
                <c:when test="${requestScope.PAGE == requestScope.TOTAL_PAGES - 1}">
                    <input type="hidden" value="${requestScope.PAGE - 1}" name="page"/>
                    <input type="submit" value="Quay lai"/>  
                </c:when>
                <c:otherwise>
                    <input type="hidden" value="${requestScope.PAGE + 1}" name="page"/>
                    <input type="submit" value="Tiep theo"/>    
                </c:otherwise>
            </c:choose>
        </form>-->
            <c:choose>
                <c:when test="${requestScope.PAGE == 0}">
                    <form action="/QuizOnline/user/view-history" method="POST">
                        <input type="hidden" value="${requestScope.PAGE + 1}" name="page"/>
                        <input type="hidden" value="${requestScope.SUBJECT_ID}" name="subjectID"/>
                        <input type="submit" value="Tiep theo"/>
                    </form>
                </c:when>
                <c:when test="${requestScope.PAGE == requestScope.TOTAL_PAGES -1}">
                    <form action="/QuizOnline/user/view-history" method="POST">
                        <input type="hidden" value="${requestScope.PAGE - 1}" name="page"/>
                        <input type="hidden" value="${requestScope.SUBJECT_ID}" name="subjectID"/>
                        <input type="submit" value="Quay lai"/>  
                    </form>
                </c:when>
                <c:otherwise>
                    <form action="/QuizOnline/user/view-history" method="POST">
                        <input type="hidden" value="${requestScope.PAGE - 1}" name="page"/>
                        <input type="hidden" value="${requestScope.SUBJECT_ID}" name="subjectID"/>
                        <input type="submit" value="Quay lai"/>  
                    </form>
                    <form action="/QuizOnline/user/view-history" method="POST">
                        <input type="hidden" value="${requestScope.PAGE + 1}" name="page"/>
                        <input type="hidden" value="${requestScope.SUBJECT_ID}" name="subjectID"/>
                        <input type="submit" value="Tiep theo"/>
                    </form>
                </c:otherwise>
            </c:choose>
        </c:if>
    </body>
</html>
