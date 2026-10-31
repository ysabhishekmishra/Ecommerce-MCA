<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Sign Up</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body class="auth-page">
    <div class="auth-box">
        <h2>Sign Up</h2>

        <c:if test="${not empty error}">
            <div class="alert error"><c:out value="${error}" /></div>
        </c:if>

        <form action="${pageContext.request.contextPath}/register" method="post">
            <label>Username</label>
            <input type="text" name="username" maxlength="50" value="<c:out value='${enteredUsername}' />" required autofocus>

            <label>Email</label>
            <input type="email" name="email" maxlength="100" value="<c:out value='${enteredEmail}' />" required>

            <label>Password</label>
            <input type="password" name="password" maxlength="100" required minlength="4">

            <button type="submit">Register</button>
        </form>

        <p class="switch">Pehle se account hai?
            <a href="${pageContext.request.contextPath}/login">Login</a>
        </p>
    </div>
</body>
</html>
