<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: nguye
  Date: 23/03/2024
  Time: 1:06 SA
  To change this template use File | Settings | File Templates.
--%>
<title>Product management</title>
<body>

<header>
    <%@ include file="/component/header.jsp" %>
    <%@ include file="/component/admin/admin-nav.jsp" %>
</header>

<div class="container-fluid">
    <div class="col-md-6">
        <h2>Product form update</h2>
        <form action="/admin/product/update" method="post">
            <div class="form-group">
                <input type="text" class="form-control" name="id" value="${product.id}" hidden="">
            </div>

            <div class="form-group">
                <label>Name:</label>
                <input type="text" class="form-control" name="name" value="${product.name}"
                       placeholder="Enter name of product">
                <span style="color: red">${violations_product.get('name')}</span>
            </div>
            <div class="form-group">
                <label>Size:</label>
                <div class="form-check">
                    <input class="form-check-input" type="radio" name="size"
                           id="flexRadioDefault1" value="S" ${product.size eq 'S' ? 'checked' : ''}>
                    <label class="form-check-label" for="flexRadioDefault1">S</label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="radio" name="size"
                           id="flexRadioDefault2" value="M" ${product.size eq 'M' ? 'checked' : ''}>
                    <label class="form-check-label" for="flexRadioDefault2">M</label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="radio" name="size"
                           id="flexRadioDefault3" value="L" ${product.size eq 'L' ? 'checked' : ''}>
                    <label class="form-check-label" for="flexRadioDefault3">L</label>
                </div>
                <span style="color: red">${violations_product.get('size')}</span>
            </div>
            <div class="form-group">
                <label>Price:</label>
                <input type="text" class="form-control" name="price" value="${product.price}">
                <span style="color: red">${violations_product.get('price')}</span>
            </div>
            <div class="form-group">
                <label>Category:</label>
                <select class="form-select" aria-label="Default select example" name="category_id">
                    <c:forEach items="${categories}" var="category">
                        <option value="${category.id}"
                            ${product.category.id eq category.id ? 'selected' : ''}>${category.name}</option>
                    </c:forEach>
                </select>
                <span style="color: red">${violations_product.get('category')}</span>
            </div>

            <button type="submit" class="btn btn-primary mt-2">Submit</button>
        </form>
    </div>
</div>

<footer>
    <%@ include file="/component/footer.jsp" %>
</footer>

</body>