<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: nguye
  Date: 24/03/2024
  Time: 11:25 CH
  To change this template use File | Settings | File Templates.
--%>
<title>My order</title>
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
            <svg class="bd-placeholder-img" width="200" height="250" xmlns="http://www.w3.org/2000/svg" role="img"
                 aria-label="Placeholder: Thumbnail" preserveAspectRatio="xMidYMid slice" focusable="false"><title>
                Placeholder</title>
                <rect width="100%" height="100%" fill="#55595c"></rect>
                <text x="50%" y="50%" fill="#eceeef" dy=".3em">Thumbnail</text>
            </svg>
        </div>
    </div>
</div>

<h3>Cart</h3>
<div class="col-md-12">
    <table class="table table-striped">
        <thead>
        <tr>
            <th>OrderID</th>
            <th>Products</th>
            <th>Status</th>
            <th>Payment</th>
            <th>Address</th>
            <th>End price</th>
            <th>Created at</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${orders}" var="order">
            <tr>
                <td>${order.id}</td>
                <td>
                    <select class="form-select" aria-label="Default select example">
                        <c:forEach items="${order.lineItems}" var="line_item">
                            <option>${line_item.product.name}</option>
                        </c:forEach>
                    </select>
                </td>
                <td>${order.status}</td>
                <td>${order.payment.status}</td>
                <td>${order.address}</td>
                <td>${order.endPrice}</td>
                <td>${order.createdAt}</td>
                <th><a href="/order/edit?id=${order.id}" class="btn btn-warning">Update</a></th>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<footer>
    <%@ include file="/component/footer.jsp" %>
</footer>
</body>