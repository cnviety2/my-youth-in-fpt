<%-- 
    Document   : update
    Created on : Apr 18, 2021, 4:07:09 PM
    Author     : dell
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Question Page</title>
    </head>
    <body>
        <c:set value="${requestScope.UPDATE_QUESTION_OBJECT}" var="questionObject"/>
        <h1>Update question</h1>
        <a href="/QuizOnline/admin/home.jsp">Quay lai</a>
        <form action="/QuizOnline/admin/update-question" method="POST">
            <input type="hidden" value="${requestScope.UPDATE_QUESTION_ID}" name="questionIDUpdate"/>
            <select name="subjectUpdate">
                <c:forEach items="${sessionScope.LIST_SUBJECTS}" var="subject">
                    <c:choose>
                        <c:when test="${questionObject.question.subjectID == subject.id}">
                            <option value="${subject.id}" selected="true">${subject.subject}</option>
                        </c:when>
                        <c:otherwise>
                            <option value="${subject.id}">${subject.subject}</option>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </select></br>
            Noi dung cau hoi:<input type="text" name="questionContentUpdate" required="true" value="${questionObject.question.questionContent}"/></br>
            <c:set var="no" value="${1}"/>
            <c:forEach items="${questionObject.listAnswers}" var="answer">
                <c:choose>
                    <c:when test="${answer.isCorrect == true}">
                        Cau tra loi ${no}:<input type="text" name="answer${no}Update" value="${answer.answerContent}" required="true"/><input type="radio" checked="true" name="correctAnswerUpdate" value="answer${no}"/></br>
                    </c:when>
                    <c:otherwise>
                        Cau tra loi ${no}:<input type="text" name="answer${no}Update" value="${answer.answerContent}" required="true"/><input type="radio" name="correctAnswerUpdate" value="answer${no}"/></br>
                    </c:otherwise>
                </c:choose>
                <input type="hidden" value="${answer.id}" name="answerID${no}Update"/>
                <c:set var="no" value="${no + 1}"/>
            </c:forEach>
            <input type="submit" value="Update"/>
        </form>
    </body>
</html>
