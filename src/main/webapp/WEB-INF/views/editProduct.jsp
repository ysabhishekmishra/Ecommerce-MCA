<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Edit Product</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <nav class="navbar">
        <span class="brand">ProductApp</span>
        <div>
            <span class="user">Hi, <c:out value="${sessionScope.username}" /></span>
            <a href="${pageContext.request.contextPath}/products">All Products</a>
            <a class="logout" href="${pageContext.request.contextPath}/logout">Logout</a>
        </div>
    </nav>

    <div class="container">
        <h2>Edit Product #${formId}</h2>

        <c:if test="${not empty error}">
            <div class="alert error"><c:out value="${error}" /></div>
        </c:if>

        <div class="card narrow">
            <form action="${pageContext.request.contextPath}/editProduct" method="post">
                <input type="hidden" name="id" value="${formId}">

                <label>Name</label>
                <input type="text" name="name" maxlength="100" value="<c:out value='${formName}' />" required autofocus>

                <label>Price</label>
                <input type="number" step="0.01" min="0" name="price" value="<c:out value='${formPrice}' />" required>

                <label>Quantity</label>
                <input type="number" min="0" name="quantity" value="<c:out value='${formQuantity}' />" required>

                <button type="submit">Update</button>
                <a class="btn gray" href="${pageContext.request.contextPath}/products">Cancel</a>
            </form>
        </div>
    </div>
</body>
</html>
