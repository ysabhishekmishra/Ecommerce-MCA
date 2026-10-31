<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Error</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body class="auth-page">
    <div class="auth-box">
        <h2>Kuch galat ho gaya</h2>
        <p>Server me error aaya. Database se connect nahi ho paya ho to ye check karo:</p>
        <ul>
            <li>MySQL chalu hai?</li>
            <li><code>sql/schema.sql</code> chala diya?</li>
            <li><code>DBUtil.java</code> me user/password sahi hai?</li>
            <li><code>mysql-connector-j</code> jar <code>WEB-INF/lib</code> me hai?</li>
        </ul>
        <p>Asli wajah Tomcat ke Console tab me likhi hoti hai.</p>
        <p class="switch"><a href="${pageContext.request.contextPath}/login">Login par wapas</a></p>
    </div>
</body>
</html>
