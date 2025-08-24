

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin Home</title>
    <!-- 引入 Bootstrap CDN -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body class="bg-light">

<div class="container mt-4">
    <!-- 导航栏 -->
    <jsp:include page="admin_nav.jsp"/>

    <h1 class="mb-4">Admin Dashboard</h1>

    <div class="card shadow-sm mb-4">
        <div class="card-header bg-primary text-white">
            <h5 class="mb-0">Statistics</h5>
        </div>
        <div class="card-body">
            <ul class="list-group list-group-flush">
                <li class="list-group-item">User count: <strong>${userCount}</strong></li>
                <li class="list-group-item">Most popular category: <strong>${mostPopularCategory}</strong></li>
            </ul>
        </div>
    </div>
</div>

</body>
</html>
