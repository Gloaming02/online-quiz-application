
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quiz Result</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>

    <style>
        .correct { color: green; font-weight: bold; }
        .user-choice { color: blue; font-style: italic; }
    </style>
</head>
<body class="container mt-4">

<%@ include file="navbar.jsp" %>

<div class="mb-4">
    <h2 class="text-center">Quiz Result</h2>

    <div class="border rounded p-4 bg-light">
        <p><strong>User:</strong> ${quizInfo.fullName}</p>
        <p><strong>Quiz:</strong> ${quizInfo.quizName}</p>
        <p><strong>Start:</strong> ${quizInfo.startTime}</p>
        <p><strong>End:</strong> ${quizInfo.endTime}</p>
        <p><strong>Score:</strong> ${correctCount} / ${total}</p>
        <p><strong>Status:</strong>
            <c:choose>
                <c:when test="${passed}">
                    <span class="text-success fw-bold">Passed</span>
                </c:when>
                <c:otherwise>
                    <span class="text-danger fw-bold">Failed</span>
                </c:otherwise>
            </c:choose>
        </p>
    </div>
</div>

<c:forEach var="result" items="${resultList}">
    <div class="mb-4">
        <h5 class="fw-bold">Question:</h5>
        <p>${result.question}</p>
        <ul class="list-group">
            <c:forEach var="choice" items="${result.choices}">
                <li class="list-group-item">
                    <c:choose>
                        <c:when test="${choice.description == result.correctAnswer && choice.description == result.userChoice}">
                            <span class="correct">✔ Correct</span>
                            <span class="user-choice"> (Your Choice)</span>
                        </c:when>
                        <c:when test="${choice.description == result.correctAnswer}">
                            <span class="correct">✔ Correct</span>
                        </c:when>
                        <c:when test="${choice.description == result.userChoice}">
                            <span class="user-choice"> (Your Choice)</span>
                        </c:when>
                    </c:choose>
                        ${choice.description}
                </li>
            </c:forEach>
        </ul>
    </div>
</c:forEach>

<hr/>

<div class="text-center mt-4">
    <c:choose>
        <c:when test="${sessionScope.user.admin}">
            <a class="btn btn-outline-secondary" href="${pageContext.request.contextPath}/admin/quizzes">← Back to Quiz Management</a>
        </c:when>
        <c:otherwise>
            <a class="btn btn-primary" href="${pageContext.request.contextPath}/home">Take Another Quiz</a>
        </c:otherwise>
    </c:choose>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
