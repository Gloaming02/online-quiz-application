
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="admin_nav.jsp" %>

<html>
<head>
    <title>Edit Question</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
</head>
<body class="container mt-4">

<h2 class="mb-4">Edit Question</h2>

<form method="post" action="/admin/questions/save" class="border p-4 rounded bg-light shadow-sm">
    <input type="hidden" name="questionId" value="${question.questionId}" />

    <!-- Category Dropdown -->
    <div class="mb-3">
        <label for="categoryId" class="form-label">Category</label>
        <select name="categoryId" id="categoryId" class="form-select">
            <c:forEach var="cat" items="${categories}">
                <option value="${cat.categoryId}" ${cat.categoryId == question.categoryId ? "selected" : ""}>
                        ${cat.name}
                </option>
            </c:forEach>
        </select>
    </div>

    <!-- Description -->
    <div class="mb-3">
        <label for="description" class="form-label">Description</label>
        <textarea name="description" id="description" class="form-control" rows="3">${question.description}</textarea>
    </div>

    <!-- Choices -->
    <label class="form-label">Choices</label>
    <c:forEach var="choice" items="${choices}" varStatus="loop">
        <div class="input-group mb-2">
            <div class="input-group-text">
                <input type="radio" name="correctChoiceIndex" value="${loop.index}"
                       <c:if test="${choice.correct}">checked</c:if> />
            </div>
            <input type="text" name="choiceDescriptions" class="form-control" value="${choice.description}" />
        </div>
    </c:forEach>

    <!-- Buttons -->
    <div class="mt-4">
        <button type="submit" class="btn btn-primary">Save</button>
        <a href="/admin/questions" class="btn btn-secondary ms-2">Cancel</a>
    </div>
</form>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
