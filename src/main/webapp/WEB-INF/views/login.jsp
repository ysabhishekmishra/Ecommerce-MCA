<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Login</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body class="auth-page">
    <div class="auth-box">
        <h2>Login</h2>

        <%-- Servlet ne request me "error" rakha ho to dikhao --%>
        <c:if test="${not empty error}">
            <div class="alert error"><c:out value="${error}" /></div>
        </c:if>

        <%-- URL ke ?registered=1 / ?loggedout=1 / ?error=login_required se messages --%>
        <c:if test="${param.registered == '1'}">
            <div class="alert">Register ho gaya, ab login karo</div>
        </c:if>
        <c:if test="${param.loggedout == '1'}">
            <div class="alert">Logout ho gaye</div>
        </c:if>
        <c:if test="${param.error == 'login_required'}">
            <div class="alert error">Pehle login karo</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/login" method="post">
            <label>Username</label>
            <input type="text" name="username" value="<c:out value='${enteredUsername}' />" required autofocus>

            <label>Password</label>
            <input type="password" name="password" required>

            <button type="submit">Login</button>
        </form>

        <p class="switch">Account nahi hai?
            <a href="${pageContext.request.contextPath}/register">Sign up</a>
        </p>
    </div>
</body>
</html>
