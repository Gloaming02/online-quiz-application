<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%--<html>--%>
<%--<head>--%>
<%--    <title>Login</title>--%>
<%--</head>--%>
<%--<body>--%>
<%--<%@ include file="navbar.jsp" %>--%>
<%--<div>--%>
<%--    <h2>Login</h2>--%>
<%--    <form method="post" action="/login">--%>
<%--        <div>--%>
<%--            <label>Email</label>--%>
<%--            <input type="text" name="email" required />--%>
<%--        </div>--%>
<%--        <div>--%>
<%--            <label>Password</label>--%>
<%--            <input type="password" name="password" required />--%>
<%--        </div>--%>
<%--        <div>--%>
<%--            <button type="submit">Login</button>--%>
<%--        </div>--%>
<%--    </form>--%>

<%--    <p style="color:red;">${error}</p>--%>
<%--    <p>Don't have an account? <a href="/register">Register here</a></p>--%>
<%--</div>--%>
<%--</body>--%>
<%--</html>--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<%@ include file="navbar.jsp" %>

<div class="container mt-5" style="max-width: 500px;">
    <h2 class="mb-4 text-center">Login</h2>

    <form method="post" action="/login" class="border p-4 rounded shadow-sm bg-light">
        <div class="mb-3">
            <label for="email" class="form-label">Email</label>
            <input type="text" class="form-control" id="email" name="email" required />
        </div>

        <div class="mb-3">
            <label for="password" class="form-label">Password</label>
            <input type="password" class="form-control" id="password" name="password" required />
        </div>

        <div class="d-grid">
            <button type="submit" class="btn btn-primary">Login</button>
        </div>

        <c:if test="${not empty error}">
            <div class="alert alert-danger mt-3" role="alert">
                    ${error}
            </div>
        </c:if>
    </form>

    <p class="mt-3 text-center">Don't have an account?
        <a href="/register">Register here</a>
    </p>
</div>

</body>
</html>

