
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="admin_nav.jsp" %>

<html>
<head>
    <title>Question Management</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
</head>
<body class="container mt-4">

<h2 class="mb-4">Question Management</h2>

<a href="/admin/questions/add" class="btn btn-success mb-3">Add Question</a>

<table class="table table-bordered table-striped align-middle">
    <thead class="table-dark">
    <tr>
        <th>ID</th>
        <th>Category</th>
        <th>Description</th>
        <th>Status</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="question" items="${questions}">
        <tr>
            <td>${question.questionId}</td>
            <td>${question.categoryName}</td>
            <td>${question.description}</td>
            <td>
                <span class="badge ${question.active ? 'bg-success' : 'bg-secondary'}">
                        ${question.active ? "Active" : "Suspended"}
                </span>
            </td>
            <td>
                <form method="post" action="/admin/questions/toggle/${question.questionId}" class="d-inline">
                    <input type="hidden" name="page" value="${currentPage}" />
                    <button type="submit" class="btn btn-sm ${question.active ? 'btn-warning' : 'btn-primary'}">
                            ${question.active ? "Suspend" : "Activate"}
                    </button>
                </form>
                <a href="/admin/questions/edit/${question.questionId}" class="btn btn-sm btn-secondary ms-2">Edit</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<!-- Pagination -->
<nav class="mt-4">
    <ul class="pagination justify-content-center">
        <c:if test="${currentPage > 1}">
            <li class="page-item">
                <a class="page-link" href="/admin/questions?page=${currentPage - 1}">Previous</a>
            </li>
        </c:if>

        <c:forEach begin="1" end="${totalPages}" var="i">
            <li class="page-item ${i == currentPage ? 'active' : ''}">
                <a class="page-link" href="/admin/questions?page=${i}">${i}</a>
            </li>
        </c:forEach>

        <c:if test="${currentPage < totalPages}">
            <li class="page-item">
                <a class="page-link" href="/admin/questions?page=${currentPage + 1}">Next</a>
            </li>
        </c:if>
    </ul>
</nav>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
