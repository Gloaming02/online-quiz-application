

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="true" %>
<html>
<head>
    <title>Home</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<%@ include file="navbar.jsp" %>

<div class="container mt-4">
    <h2 class="mb-3">Welcome, <span class="text-primary">${sessionScope.user.firstname}</span>!</h2>
    <p class="lead">You are now logged in.</p>

    <h3 class="mt-4">Quiz Categories</h3>
    <ul class="list-group mb-4">
        <c:forEach var="category" items="${categories}">
            <li class="list-group-item d-flex justify-content-between align-items-center">
                    ${category.name}
                <form action="/quiz/start" method="get" class="mb-0">
                    <input type="hidden" name="cid" value="${category.categoryId}" />
                    <button type="submit" class="btn btn-sm btn-success">Start Quiz</button>
                </form>
            </li>
        </c:forEach>
    </ul>

    <h3>Recent Quiz Results</h3>
    <table class="table table-striped table-bordered">
        <thead class="table-dark">
        <tr>
            <th>Quiz Name</th>
            <th>Category</th>
            <th>Start Time</th>
            <th>End Time</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="quiz" items="${recentQuizzes}">
            <tr>
                <td>
                    <a href="/quiz/result?quizId=${quiz.quizId}">${quiz.name}</a>
                </td>
                <td>${quiz.categoryName}</td>
                <td>${quiz.timeStart}</td>
                <td>${quiz.timeEnd}</td>
                <td>
                    <a href="/quiz/result?quizId=${quiz.quizId}" class="btn btn-sm btn-outline-primary">View Result</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

</body>
</html>
