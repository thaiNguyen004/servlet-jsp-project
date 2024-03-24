<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: nguye
  Date: 24/03/2024
  Time: 8:49 SA
  To change this template use File | Settings | File Templates.
--%>
<title>${product.name}</title>
<body>
<header>
    <%@ include file="/component/header.jsp" %>
</header>

<div class="container mb-5">
    <h2>${product.name}</h2>
    <p>${product.price}</p>
    <p>${product.quantity}</p>
    <form action="/cart/add" method="post">
        <input type="text" value="${product.id}" name="productId" hidden>
        <div class="form-group">
            <label>Quantity:</label>
            <input type="text" class="form-control" name="quantity" placeholder="Enter name of product">
            <span style="color: red">${violations_cart.get('quantity')}</span>
        </div>
        <button type="submit" class="btn btn-primary">Add to cart</button>
    </form>
    <a class="btn btn-success" href="/order/add?productId=${product.id}">Buy now</a>
</div>

<footer>
    <%@ include file="/component/footer.jsp" %>
</footer>
</body>