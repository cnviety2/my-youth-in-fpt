<%-- 
    Document   : quiz
    Created on : Apr 19, 2021, 9:48:00 AM
    Author     : dell
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quiz</title>
    </head>
    <c:if test="${not empty requestScope.MESSAGE}">
        <script>
            window.alert('${requestScope.MESSAGE}');
        </script>
    </c:if>

    <body>
        <h1 id="tiktoktiktok">Thoi gian con lai:</h1>
        <c:choose>
            <c:when test="${sessionScope.QUESTION_NUMBER < sessionScope.QUIZ_TOTAL_QUESTIONS}">
                <form id="formQuiz" action="/QuizOnline/user/next-question" method="POST">
                    <input type="hidden" id="remainSeconds" name="remainSeconds"/>
                    <input type="hidden" value="${sessionScope.QUESTION.question.id}" name="questionID"/>
                    <c:set var="no" value="${sessionScope.QUESTION_NUMBER}"/>
                    <h1>Câu ${no}:${sessionScope.QUESTION.question.questionContent}</h1>
                    <c:set var="no2" value="${1}"/>
                    <c:forEach items="${sessionScope.QUESTION.listAnswers}" var="answer">
                        <div>${no2}.${answer.answerContent}<input type="radio" name="answerID" value="${answer.id}"/>
                        </div>
                        <c:set var="no2" value="${no2 + 1}"/>
                    </c:forEach>
                    <p>---------</p>
                    <input type="hidden" value="${sessionScope.QUESTION_NUMBER}" name="questionNumber"/>
                    <input type="submit" value="Cau tiep theo"/>
                </form>
            </c:when>
            <c:otherwise>
                <form id="formQuiz" onSubmit="return confirm('Bạn có muốn nộp bài?')" action="/QuizOnline/user/quiz-submit">
                    <input type="hidden" id="remainSeconds" name="remainSeconds" value="${sessionScope.REMAIN_SECONDS}"/>
                    <input type="hidden" value="${sessionScope.QUESTION.question.id}" name="questionID"/>
                    <c:set var="no" value="${sessionScope.QUESTION_NUMBER}"/>
                    <h1>Câu ${no}:${sessionScope.QUESTION.question.questionContent}</h1>
                    <c:set var="no2" value="${1}"/>
                    <c:forEach items="${sessionScope.QUESTION.listAnswers}" var="answer">
                        <div>${no2}.${answer.answerContent}<input type="radio" name="answerID" value="${answer.id}"/>
                        </div>
                        <c:set var="no2" value="${no2 + 1}"/>
                    </c:forEach>
                    <p>---------</p>
                    <input type="hidden" value="${sessionScope.QUESTION_NUMBER}" name="questionNumber"/>
                    <input type="submit" value="Nop bai"/>
                </form>
            </c:otherwise>
        </c:choose>
    </body>
    <script>

        let remainSeconds = ${sessionScope.REMAIN_SECONDS};
        let tiktoktiktok = document.getElementById('tiktoktiktok');
        let remainSecondsInput = document.getElementById('remainSeconds');
        let formQuiz = document.getElementById('formQuiz');
        function tiktok(seconds) {
            let id = setInterval(() => {
                if (seconds === 0) {
                    tiktoktiktok.innerHTML = 'Thoi gian con lai:' + seconds;
                    remainSecondsInput.setAttribute('value', seconds);
                    clearInterval(id);
                    formQuiz.submit();
                } else {
                    tiktoktiktok.innerHTML = 'Thoi gian con lai:' + seconds;
                    remainSecondsInput.setAttribute('value', seconds);
                    seconds--;
                }
            }, 1000);
        }
        tiktok(remainSeconds);
    </script>
</html>
