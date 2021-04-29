<%-- 
    Document   : home
    Created on : Apr 16, 2021, 5:36:43 PM
    Author     : dell
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <form action="/QuizOnline/logout">
            <input type="submit" value="Logout"/>
        </form>
        <h1>Search question</h1>
        <form action="/QuizOnline/admin/search">
            Noi dung:<input type="text" name="searchContent" value="${requestScope.SEARCH_CONTENT}"/>
            Subject:<select name="searchSubject">
                <option value="none"></option>
                <c:forEach items="${sessionScope.LIST_SUBJECTS}" var="subject">
                    <c:choose>
                        <c:when test="${requestScope.SEARCH_SUBJECT eq subject.subject}">
                            <option value="${subject.id}" selected="true">${subject.subject}</option>
                        </c:when>
                        <c:otherwise>
                            <option value="${subject.id}">${subject.subject}</option>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </select>
            Status:<select name="searchStatus">
                <option value="none"></option>
                <option value="true">Active</option>
                <option value="false">No active</option>
            </select>
            <input type="submit" value="Tim"/>
        </form></br>
        <h1>Result:</h1>
        <table border="1">
            <thead>
                <tr>
                    <th>No</th>
                    <th>Question ID</th>
                    <th>Question Content</th>
                    <th>Answer A</th>
                    <th>Answer B</th>
                    <th>Answer C</th>
                    <th>Answer D</th>
                </tr>
            </thead>
            <tbody>
                <c:set var="no" value="${1}"/>
                <c:forEach items="${requestScope.SEARCH_RESULT}" var="result">
                    <tr>
                        <td>${no}</td>
                        <td>${result.question.id}</td>
                        <td>${result.question.questionContent}</td>
                        <c:forEach items="${result.listAnswers}" var="answer">
                            <c:choose>
                                <c:when test="${answer.isCorrect == true}">
                                    <td>${answer.answerContent}*</td>
                                </c:when>
                                <c:otherwise>
                                    <td>${answer.answerContent}</td>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                        <td>
                            <form action="/QuizOnline/admin/get-update-page" method="POST">
                                <input type="hidden" name="idUpdate" value="${result.question.id}"/>
                                <input type="submit" value="Update"/>
                            </form>
                        </td>
                        <c:if test="${result.question.status != 'false'}">
                            <td>
                                <form onSubmit="return confirm('Thực hiện delete ?')" action="/QuizOnline/admin/delete" method="POST">
                                    <input type="hidden" name="idDelete" value="${result.question.id}"/>
                                    <input type="submit" value="Delete"/>
                                </form>
                            </td>
                        </c:if>
                    </tr>
                    <c:set var="no" value="${no + 1}"/>
                </c:forEach>
            </tbody>
        </table>
        <c:forEach begin="0" end="${requestScope.TOTAL_PAGES}" varStatus="loop">
            <c:choose>
                <c:when test="${requestScope.STANDING_PAGE == loop.index}">
                    ${loop.index + 1}
                </c:when>
                <c:otherwise>
                    <a href="/QuizOnline/admin/search?searchContent=${requestScope.SEARCH_CONTENT}&searchSubject=${requestScope.SEARCH_SUBJECT}&searchStatus=${requestScope.SEARCH_STATUS}&page=${loop.index}">${loop.index+1}</a>
                </c:otherwise>
            </c:choose>
        </c:forEach>
        <h1>Add new question</h1>
        <form action="/QuizOnline/admin/add-new-question" method="POST">
            <select name="subject">
                <c:forEach items="${sessionScope.LIST_SUBJECTS}" var="subject">
                    <option value="${subject.id}">${subject.subject}</option>
                </c:forEach>
            </select></br>
            Noi dung cau hoi:<input type="text" name="questionContent" required="true"/></br>
            Cau tra loi 1:<input type="text" name="answer1" required="true"/><input type="radio" name="correctAnswer" value="answer1"/></br>
            Cau tra loi 2:<input type="text" name="answer2" required="true"/><input type="radio" name="correctAnswer" value="answer2"/></br>
            Cau tra loi 3:<input type="text" name="answer3" required="true"/><input type="radio" name="correctAnswer" value="answer3"/></br>
            Cau tra loi 4:<input type="text" name="answer4" required="true"/><input type="radio" name="correctAnswer" value="answer4"/></br>
            <input type="submit" value="Add"/>
        </form>
    </body>
</html>
