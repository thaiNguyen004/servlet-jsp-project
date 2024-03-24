<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: nguye
  Date: 23/03/2024
  Time: 9:51 CH
  To change this template use File | Settings | File Templates.
--%>
<title>Order management</title>
<body>

<header>
    <%@ include file="/component/header.jsp" %>
    <%@ include file="/component/admin/admin-nav.jsp" %>
</header>

<div class="container-fluid">
    <div class="row">
        <h3 class="badge bg-success">Filter order by username</h3>
        <nav class="navbar navbar-light bg-light">
            <div class="container-fluid">
                <form class="d-flex" action="/admin/order/filter" method="GET">
                    <input class="form-control me-2" type="search" name="username" placeholder="Search order by username"
                           aria-label="Search">
                    <button class="btn btn-outline-success" type="submit">Search</button>
                </form>
            </div>
        </nav>
    </div>

    <div class="row">
        <div class="col-md-16">
            <h2>Data Table</h2>
            <table class="table table-striped">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Line Items</th>
                    <th>Payments</th>
                    <td>Total price</td>
                    <td>Discount id</td>
                    <th>End price</th>
                    <th>Address</th>
                    <th>Description</th>
                    <th>Created At</th>
                    <th>Updated At</th>
                    <th>Status</th>
                    <th>User</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${orders}" var="order">
                    <tr>
                        <td>${order.id}</td>
                        <td>
                            <c:forEach items="${order.lineItems}" var="lineItem">
                                <p><a href="/admin/view/product?id=${lineItem.product.id}">${lineItem.product.name}</a> x ${lineItem.quantity}</p>
                            </c:forEach>
                        </td>
                        <td>
                            <c:forEach items="${order.payments}" var="payment">
                                <p><a href="/admin/view/payment?id=${payment.id}">${payment.amount}</a> x ${payment.method}</p>
                            </c:forEach>
                        </td>
                        <td>${order.totalPrice}</td>
                        <td>${order.discount.id}</td>
                        <td>${order.endPrice}</td>
                        <td>${order.address}</td>
                        <td>${order.description}</td>
                        <td>${order.createdAt}</td>
                        <td>${order.updatedAt}</td>
                        <th>${order.status}</th>
                        <th><a href="/admin/view/user?id=${order.user.id}">${order.user.username}</a></th>
                        <td>
                            <a href="/admin/order/edit?id=${order.id}" class="btn btn-warning">Edit</a>
                        </td>
                    </tr>
                </c:forEach>
                <!-- Add more rows here for dynamic data -->
                </tbody>
            </table>
        </div>
    </div>
</div>

<footer>
    <%@ include file="/component/footer.jsp" %>
</footer>

</body>