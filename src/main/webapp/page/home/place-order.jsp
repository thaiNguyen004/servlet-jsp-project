<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: nguye
  Date: 24/03/2024
  Time: 9:27 CH
  To change this template use File | Settings | File Templates.
--%>
<title>Place order</title>
<body>
<header>
    <%@ include file="/component/header.jsp" %>
    <%@ include file="/component/admin/admin-nav.jsp" %>
</header>
<script>
    function updateTotalPrice(productId) {
        // Lấy giá trị số lượng sản phẩm từ trường input
        var quantity = parseInt(document.getElementById('quantity_' + productId).value);
        // Lấy giá trị giá sản phẩm từ DOM (nếu có)
        var price = parseFloat(document.getElementById('price_' + productId).innerText);

        // Tính toán tổng giá trị
        var totalPrice = quantity * price;

        // Cập nhật giá trị của trường tổng giá trị
        document.getElementById('totalPrice').value = totalPrice;
    }
</script>
<div class="container">
    <h2>Place Order</h2>
    <form action="/discount/check" method="post">
        <div class="form-group">
            <label for="discount">Discount</label>
            <input type="number" class="form-control" id="discount" name="discount">
            <span style="color: red">${violations_order.get('discount')}</span>
        </div>

        <%--hidden--%>
        <c:forEach items="${orderProducts}" var="entry">
            <input type="hidden" name="quantity_${entry.key.id}" min="1" value="${entry.value}">
            <input type="hidden" name="productIds" value="${entry.key.id}">
        </c:forEach>

        <button type="submit" class="btn btn-success">Check</button>
    </form>

    <form action="/order/buy" method="post">
        <h4>Order Information</h4>
        <h5>Products</h5>
            <%-- Accessing product and quantity from the map entry --%>
            <table>
                <thead>
                <tr>
                    <th>Product name</th>
                    <th>Product size</th>
                    <th>Product price</th>
                    <th>Quantity</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${orderProducts}" var="entry">
                    <tr>
                        <td>${entry.key.name}</td>
                        <td>${entry.key.size}</td>
                        <td>${entry.key.price}</td>
                        <td>
                            <input type="number" name="quantity_${entry.key.id}" min="1" value="${entry.value}">
                            <input type="hidden" name="productIds" value="${entry.key.id}">
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

        <div class="form-group">
            <label for="totalPrice">Total Price</label>
            <input type="number" class="form-control" id="totalPrice" name="totalPrice" value="${totalPrice}" readonly>
        </div>
        <div class="form-group">
            <label for="discountValue">discount Value</label>
            <input type="hidden" name="discountId" value="${discountId}">
            <input type="number" class="form-control" id="discountValue" name="discountValue" value="${discountValue}"
                   readonly>
        </div>
        <div class="form-group">
            <label for="endPrice">End Price</label>
            <input type="number" class="form-control" id="endPrice" name="endPrice" value="${endPrice}" readonly>
        </div>

        <div class="form-group">
            <label for="address">Address</label>
            <input type="text" class="form-control" id="address" name="address" required>
        </div>
        <div class="form-group">
            <label for="description">Description</label>
            <textarea class="form-control" id="description" name="description" rows="3"></textarea>
        </div>
        <select class="form-select" size="2" multiple aria-label="multiple select example" name="paymentMethod">
            <option value="CAST">CAST</option>
            <option value="ONLINE">ONLINE</option>
        </select>
        <span style="color: red">${violations_order.get('payment')}</span>
        <button type="submit" class="btn btn-primary">Place Order</button>
    </form>
</div>

</body>
