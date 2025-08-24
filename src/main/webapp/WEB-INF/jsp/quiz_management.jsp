
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Quiz Results</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body class="container mt-4">
<%@ include file="admin_nav.jsp" %>

<h2 class="mb-4">Quiz Results</h2>

<form method="get" action="${pageContext.request.contextPath}/admin/quizzes" class="row gy-2 gx-3 align-items-center mb-4">
    <div class="col-auto">
        <label class="form-label" for="categoryId">Category:</label>
        <select class="form-select" name="categoryId" id="categoryId">
            <option value="">All</option>
            <c:forEach var="cat" items="${categories}">
                <option value="${cat.categoryId}" ${cat.categoryId == selectedCategory ? "selected" : ""}>${cat.name}</option>
            </c:forEach>
        </select>
    </div>

    <div class="col-auto">
        <label class="form-label" for="userId">User:</label>
        <select class="form-select" name="userId" id="userId">
            <option value="">All</option>
            <c:forEach var="u" items="${users}">
                <option value="${u.userId}" ${u.userId == selectedUser ? "selected" : ""}>${u.firstname} ${u.lastname}</option>
            </c:forEach>
        </select>
    </div>

    <input type="hidden" name="sortBy" value="${sortBy}"/>
    <div class="col-auto mt-4">
        <button type="submit" class="btn btn-primary">Filter</button>
    </div>
</form>

<table class="table table-bordered table-striped">
    <thead class="table-light">
    <tr>
        <th>Taken Time</th>
        <th>
            <a href="?sortBy=categoryName&categoryId=${selectedCategory}&userId=${selectedUser}" class="text-decoration-none">
                Category
            </a>
        </th>
        <th>
            <a href="?sortBy=fullName&categoryId=${selectedCategory}&userId=${selectedUser}" class="text-decoration-none">
                User Full Name
            </a>
        </th>
        <th>Number of Questions</th>
        <th>Score</th>
        <th>Action</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="r" items="${results}">
        <tr>
            <td>${r.takenTime}</td>
            <td>${r.categoryName}</td>
            <td>${r.fullName}</td>
            <td>${r.numQuestions}</td>
            <td>${r.score}</td>
            <td>
                <a href="${pageContext.request.contextPath}/quiz/result?quizId=${r.quizResultId}" class="btn btn-sm btn-outline-info">
                    View Result
                </a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<c:if test="${totalPages > 1}">
    <nav>
        <ul class="pagination">
            <c:forEach begin="1" end="${totalPages}" var="i">
                <li class="page-item ${i == currentPage ? 'active' : ''}">
                    <a class="page-link"
                       href="?page=${i}&categoryId=${selectedCategory}&userId=${selectedUser}&sortBy=${sortBy}">
                            ${i}
                    </a>
                </li>
            </c:forEach>
        </ul>
    </nav>
</c:if>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
