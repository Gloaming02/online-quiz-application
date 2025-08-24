
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Quiz Question</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body class="container mt-4">

<%@ include file="navbar.jsp" %>

<h2 class="mb-4">Question ${pageIndex + 1} of ${totalQuestions}</h2>

<form method="post" action="/quiz/page" class="mb-4">
    <input type="hidden" name="quizId" value="${quizId}" />
    <input type="hidden" name="pageIndex" value="${pageIndex}" />
    <input type="hidden" name="questionId" value="${question.questionId}" />

    <div class="mb-3">
        <p class="fs-5 fw-bold">${question.description}</p>
        <div class="form-check">
            <c:forEach var="choice" items="${question.choiceList}">
                <div class="form-check mb-2">
                    <input class="form-check-input" type="radio" name="choiceId" value="${choice.choiceId}"
                           id="choice${choice.choiceId}"
                           <c:if test="${choice.choiceId == selectedChoiceId}">checked</c:if> />
                    <label class="form-check-label" for="choice${choice.choiceId}">
                            ${choice.description}
                    </label>
                </div>
            </c:forEach>
        </div>
    </div>

    <div class="d-flex justify-content-between mt-4">

        <div>
            <c:if test="${pageIndex > 0}">
                <button type="submit" name="nav" value="prev" class="btn btn-outline-secondary">
                    ← Previous
                </button>
            </c:if>
        </div>

        <div>
            <c:if test="${pageIndex + 1 < totalQuestions}">
                <button type="submit" name="nav" value="next" class="btn btn-primary">
                    Next →
                </button>
            </c:if>
        </div>

        <div>
            <c:if test="${pageIndex + 1 == totalQuestions}">
                <button type="submit" name="nav" value="submit" class="btn btn-success">
                    Submit Quiz
                </button>
            </c:if>
        </div>
    </div>
</form>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
