<%--
  Created by IntelliJ IDEA.
  User: nguye
  Date: 23/03/2024
  Time: 10:06 CH
  To change this template use File | Settings | File Templates.
--%>
<title>Product update</title>
<body>

<header>
    <%@ include file="/component/header.jsp" %>
    <%@ include file="/component/admin/admin-nav.jsp" %>
</header>

<div class="container-fluid">
    <div class="col-md-6">
        <h2>Order form update</h2>
        <form action="/admin/order/update" method="post">
            <div class="form-group">
                <input class="form-control" name="id" type="text" value="${order.id}" hidden>
            </div>
            <div class="form-group">
                <label>End price</label>
                <input class="form-control" type="text" value="${order.endPrice}" aria-label="Disabled input example" disabled readonly>
            </div>
            <div class="form-group">
                <label>Placed at</label>
                <input class="form-control" type="text" value="${order.createdAt}" aria-label="Disabled input example" disabled readonly>
            </div>
            <div class="form-group">
                <label>Address</label>
                <input class="form-control" type="text" value="${order.address}" name="address">
                <span style="color: red">${violations_order.get('address')}</span>
            </div>

            <div class="form-group">
                <select name="status" class="form-select" size="5" aria-label="size 5 select example">
                    <option value="SUCCESS" ${order.status eq 'SUCCESS' ? 'selected' : ''}>SUCCESS</option>
                    <option value="PENDING" ${order.status eq 'PENDING' ? 'selected' : ''}>PENDING</option>
                    <option value="FAIL" ${order.status eq 'FAIL' ? 'selected' : ''}>FAIL</option>
                    <option value="DELIVERY" ${order.status eq 'DELIVERY' ? 'selected' : ''}>DELIVERY</option>
                    <option value="CANCEL" ${order.status eq 'CANCEL' ? 'selected' : ''}>CANCEL</option>
                </select>
            </div>

            <button type="submit" class="btn btn-primary mt-2">Submit</button>
        </form>
    </div>
</div>

<footer>
    <%@ include file="/component/footer.jsp" %>
</footer>

</body>