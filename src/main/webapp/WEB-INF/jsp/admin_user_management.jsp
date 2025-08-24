
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="admin_nav.jsp" %>
<html>
<head>
    <title>User Management</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container mt-4">

<h2 class="mb-4">User Management</h2>

<table class="table table-bordered table-hover">
    <thead class="table-dark">
    <tr>
        <th>ID</th>
        <th>Email</th>
        <th>Name</th>
        <th>Status</th>
        <th>Action</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="user" items="${users}">
        <tr>
            <td>${user.userId}</td>
            <td>${user.email}</td>
            <td>${user.firstname} ${user.lastname}</td>
            <td>
                <span class="badge bg-${user.active ? 'success' : 'secondary'}">
                        ${user.active ? 'Active' : 'Suspended'}
                </span>
            </td>
            <td>
                <form method="post" action="/admin/users/toggle/${user.userId}">
                    <input type="hidden" name="currentPage" value="${currentPage}" />
                    <button type="submit" class="btn btn-${user.active ? 'warning' : 'primary'} btn-sm">
                            ${user.active ? 'Suspend' : 'Activate'}
                    </button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<!-- Pagination Controls -->
<nav>
    <ul class="pagination justify-content-center">
        <c:if test="${currentPage > 1}">
            <li class="page-item">
                <a class="page-link" href="/admin/users?page=${currentPage - 1}">Previous</a>
            </li>
        </c:if>
        <c:forEach begin="1" end="${totalPages}" var="i">
            <li class="page-item ${i == currentPage ? 'active' : ''}">
                <a class="page-link" href="/admin/users?page=${i}">${i}</a>
            </li>
        </c:forEach>
        <c:if test="${currentPage < totalPages}">
            <li class="page-item">
                <a class="page-link" href="/admin/users?page=${currentPage + 1}">Next</a>
            </li>
        </c:if>
    </ul>
</nav>

</body>
</html>
