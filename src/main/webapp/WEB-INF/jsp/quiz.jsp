
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Quiz</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body class="container mt-4">
<%@ include file="navbar.jsp" %>

<h2 class="mb-4">Category: ${category.name}</h2>

<form method="post" action="/quiz/submit">
    <input type="hidden" name="quizId" value="${quizId}"/>

    <c:forEach var="question" items="${questions}">
        <div class="mb-4 p-3 border rounded shadow-sm">
            <p><strong>Q${question.questionId}:</strong> ${question.description}</p>

            <c:forEach var="choice" items="${question.choiceList}">
                <div class="form-check">
                    <input class="form-check-input" type="radio"
                           name="question_${question.questionId}"
                           value="${choice.choiceId}" id="q${question.questionId}_c${choice.choiceId}" />
                    <label class="form-check-label" for="q${question.questionId}_c${choice.choiceId}">
                            ${choice.description}
                    </label>
                </div>
            </c:forEach>
        </div>
    </c:forEach>

    <button type="submit" class="btn btn-primary">Submit Quiz</button>
</form>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

