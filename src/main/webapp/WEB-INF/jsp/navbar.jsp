

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />

<nav class="navbar navbar-expand-lg navbar-dark bg-dark mb-4">
    <div class="container-fluid">
        <a class="navbar-brand" href="/home">QuizApp</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
                aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item">
                    <a class="nav-link" href="/home">Home</a>
                </li>

                <c:if test="${not empty sessionScope.user}">
                    <c:if test="${sessionScope.hasOpenQuiz}">
                        <li class="nav-item">
                            <a class="nav-link" href="/quiz">Taking Quiz</a>
                        </li>
                    </c:if>
                    <li class="nav-item">
                        <a class="nav-link" href="/logout">Logout</a>
                    </li>
                </c:if>

                <c:if test="${empty sessionScope.user}">
                    <c:if test="${pageContext.request.requestURI ne '/WEB-INF/jsp/login.jsp'}">
                        <li class="nav-item">
                            <a class="nav-link" href="/login">Login</a>
                        </li>
                    </c:if>
                    <c:if test="${pageContext.request.requestURI ne '/WEB-INF/jsp/register.jsp'}">
                        <li class="nav-item">
                            <a class="nav-link" href="/register">Register</a>
                        </li>
                    </c:if>
                </c:if>

                <li class="nav-item">
                    <a class="nav-link" href="/contact">Contact Us</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
