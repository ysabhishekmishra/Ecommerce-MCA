<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Products</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <nav class="navbar">
        <span class="brand">ProductApp</span>
        <div>
            <%-- session me jo "username" rakha tha wo EL se: ${sessionScope.username} --%>
            <span class="user">Hi, <c:out value="${sessionScope.username}" /></span>
            <a class="logout" href="${pageContext.request.contextPath}/logout">Logout</a>
        </div>
    </nav>

    <div class="container">
        <h2>Welcome, <c:out value="${sessionScope.username}" /></h2>

        <%-- Redirect ke baad URL ke ?msg=... se message --%>
        <c:choose>
            <c:when test="${param.msg == 'added'}"><div class="alert">Product add ho gaya</div></c:when>
            <c:when test="${param.msg == 'updated'}"><div class="alert">Product update ho gaya</div></c:when>
            <c:when test="${param.msg == 'deleted'}"><div class="alert">Product delete ho gaya</div></c:when>
            <c:when test="${param.msg == 'notfound'}"><div class="alert error">Product nahi mila</div></c:when>
        </c:choose>

        <%-- Galat input par servlet "error" bhejta hai --%>
        <c:if test="${not empty error}">
            <div class="alert error"><c:out value="${error}" /></div>
        </c:if>

        <%-- ========== ADD PRODUCT FORM ========== --%>
        <div class="card">
            <h3>Add Product</h3>
            <form action="${pageContext.request.contextPath}/products" method="post" class="inline-form">
                <input type="text" name="name" placeholder="Name" maxlength="100"
                       value="<c:out value='${enteredName}' />" required>
                <input type="number" step="0.01" min="0" name="price" placeholder="Price"
                       value="<c:out value='${enteredPrice}' />" required>
                <input type="number" min="0" name="quantity" placeholder="Quantity"
                       value="<c:out value='${enteredQuantity}' />" required>
                <button type="submit">Add</button>
            </form>
        </div>

        <%-- ========== ALL PRODUCTS ========== --%>
        <h3>All Products (${products.size()})</h3>
        <table>
            <thead>
                <tr><th>ID</th><th>Name</th><th>Price</th><th>Qty</th><th>Action</th></tr>
            </thead>
            <tbody>
                <c:if test="${empty products}">
                    <tr><td colspan="5" class="empty">Koi product nahi hai</td></tr>
                </c:if>

                <%-- servlet ne DB se list laake request me "products" rakhi thi, loop me ek-ek "p" milta hai --%>
                <c:forEach var="p" items="${products}">
                    <tr>
                        <td>${p.id}</td>
                        <td><c:out value="${p.name}" /></td>
                        <td><fmt:formatNumber value="${p.price}" minFractionDigits="2" maxFractionDigits="2" /></td>
                        <td>
                            <c:choose>
                                <c:when test="${p.quantity == 0}"><span class="tag out">Out of stock</span></c:when>
                                <c:when test="${p.quantity < 5}"><span class="tag low">${p.quantity} (kam stock)</span></c:when>
                                <c:otherwise>${p.quantity}</c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <a class="btn" href="${pageContext.request.contextPath}/editProduct?id=${p.id}">Edit</a>
                            <form action="${pageContext.request.contextPath}/deleteProduct" method="post" class="del-form"
                                  onsubmit="return confirm('Ye product delete karna hai?');">
                                <input type="hidden" name="id" value="${p.id}">
                                <button type="submit" class="danger">Delete</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>
