<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: nguye
  Date: 24/03/2024
  Time: 12:13 SA
  To change this template use File | Settings | File Templates.
--%>
<title>Discount management</title>
<body>

<header>
    <%@ include file="/component/header.jsp" %>
    <%@ include file="/component/admin/admin-nav.jsp" %>
</header>

<div class="container-fluid">
    <div class="row">
        <!-- Left Column for Form -->
        <div class="col-md-3">
            <h2>Discount form</h2>
            <form action="/admin/discount" method="post">
                <div class="form-group">
                    <label>Start:</label>
                    <input type="datetime-local" class="form-control" name="start" placeholder="Start time of discount">
                    <span style="color: red">${violations_discount.get('start')}</span>
                </div>
                <div class="form-group">
                    <label>End:</label>
                    <input type="datetime-local" class="form-control" name="end" placeholder="End time of discount">
                    <span style="color: red">${violations_discount.get('end')}</span>
                </div>
                <div class="form-group">
                    <label>Value of discount:</label>
                    <input type="number" class="form-control" name="value" placeholder="Enter value of discount">
                    <span style="color: red">${violations_discount.get('value')}</span>
                </div>
                <div class="form-group">
                    <label>Quantity of discount:</label>
                    <input type="number" class="form-control" name="quantity" placeholder="Enter quantity of discount">
                    <span style="color: red">${violations_discount.get('quantity')}</span>
                </div>

                <button type="submit" class="btn btn-primary mt-2">Submit</button>
            </form>
        </div>
        <!-- Right Column for Table -->
        <div class="col-md-9">
            <h2>Data Table</h2>
            <table class="table table-striped">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Start</th>
                    <th>End</th>
                    <th>Estimate</th>
                    <th>Value</th>
                    <th>Quantity</th>
                    <th>Created at</th>
                    <th>Updated at</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${discounts}" var="discount">
                    <tr>
                        <td>${discount.id}</td>
                        <td>${discount.start}</td>
                        <td>${discount.end}</td>
                        <td id="remainingTime-${discount.id}"></td>
                        <td>${discount.value}</td>
                        <td>${discount.quantity}</td>
                        <td>${discount.createdAt}</td>
                        <td>${discount.updatedAt}</td>
                        <td>
                            <a href="/admin/discount/edit?id=${discount.id}" class="btn btn-warning">Edit</a>
                            <a href="/admin/discount/delete?id=${discount.id}" class="btn btn-danger">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
                <!-- Add more rows here for dynamic data -->
                </tbody>
            </table>
        </div>
    </div>
</div>

<script>
    <c:forEach var="discount" items="${discounts}">
        var startDate_${discount.id} = new Date('${discount.start}');
        var endDate_${discount.id} = new Date('${discount.end}');

        setInterval(function() {
            var currentTime_${discount.id} = new Date();
            var timeDiff_${discount.id} = endDate_${discount.id}.getTime() - currentTime_${discount.id}.getTime();
            var timeStartDiff_${discount.id} = startDate_${discount.id}.getTime() -
                currentTime_${discount.id}.getTime();

            if (timeStartDiff_${discount.id} >= 0) {
                document.getElementById('remainingTime-${discount.id}').textContent = "Discount chua kich hoat";
                return;
            } else {
                if (timeDiff_${discount.id} <= 0) {
                    document.getElementById('remainingTime-${discount.id}').textContent = "Discount expired";
                    return;
                }
                var days_${discount.id} = Math.floor(timeDiff_${discount.id} / (1000 * 60 * 60 * 24));
                var hours_${discount.id} = Math.floor((timeDiff_${discount.id} % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
                var minutes_${discount.id} = Math.floor((timeDiff_${discount.id} % (1000 * 60 * 60)) / (1000 * 60));
                var seconds_${discount.id} = Math.floor((timeDiff_${discount.id} % (1000 * 60)) / 1000);

                document.getElementById('remainingTime-${discount.id}').textContent =
                    days_${discount.id} + 'd ' +
                    hours_${discount.id} + 'h ' +
                    minutes_${discount.id} + 'm ' +
                    seconds_${discount.id} + 's';
            }
        }, 1000);
    </c:forEach>
</script>


<div class="container-fluid">
    <div class="row">
        <h3 class="badge bg-success">Search orders by discount ID</h3>
        <nav class="navbar navbar-light bg-light">
            <div class="container-fluid">
                <form action="/admin/discount/search/order" method="GET">
                    <input type="text" class="form-control" name="id" placeholder="Enter discount id">
                    <span style="color: red">${search_value}</span>
                    <br>
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