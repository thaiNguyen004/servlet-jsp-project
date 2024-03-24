<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%--
  Created by IntelliJ IDEA.
  User: nguye
  Date: 24/03/2024
  Time: 7:11 CH
  To change this template use File | Settings | File Templates.
--%>

<title>My cart</title>
<body>
<header>
    <%@ include file="/component/header.jsp" %>
</header>

<div class="col-md-6">
    <div class="row g-0 border rounded overflow-hidden flex-md-row mb-4 shadow-sm h-md-250 position-relative">
        <div class="col p-4 d-flex flex-column position-static">
            <strong class="d-inline-block mb-2 text-primary">User profile</strong>
            <h3 class="mb-0">Full name: ${user.firstName} </h3>
            <div class="mb-1 text-muted">Role: ${user.role}</div>
            <p class="card-text mb-auto">Username: ${user.username}</p>
        </div>
        <div class="col-auto d-none d-lg-block">
            <svg class="bd-placeholder-img" width="200" height="250" xmlns="http://www.w3.org/2000/svg" role="img" aria-label="Placeholder: Thumbnail" preserveAspectRatio="xMidYMid slice" focusable="false"><title>Placeholder</title><rect width="100%" height="100%" fill="#55595c"></rect><text x="50%" y="50%" fill="#eceeef" dy=".3em">Thumbnail</text></svg>

        </div>
    </div>
</div>

<h3>Cart</h3>
<div class="col-md-6">
    <form action="/order/place" method="post">
        <table class="table table-striped">
            <thead>
            <tr>
                <th>Pick</th>
                <th>Product name</th>
                <th>Product size</th>
                <th>Product price</th>
                <th>Product category</th>
                <th>Quantity</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${cartItems}" var="cart_item">
                <tr>
                    <td><input type="checkbox" name="pickedProducts" value="${cart_item.product.id}"></td>
                    <td><a href="/product/detail-info?id=${cart_item.product.id}">
                            ${cart_item.product.name}
                    </a></td>
                    <td>${cart_item.product.size}</td>
                    <td>${cart_item.product.price}</td>
                    <td><a href="/home/category?id=${cart_item.product.category.id}">
                            ${cart_item.product.category.name}
                    </a></td>
                    <td>
                        <input type="number" name="quantity_${cart_item.product.id}" value="${cart_item.quantity}" min="1">
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <button type="submit">Place Order</button>
    </form>
</div>

<footer>
    <%@ include file="/component/footer.jsp" %>
</footer>
</body>
