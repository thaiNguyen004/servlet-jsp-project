<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: nguye
  Date: 20/03/2024
  Time: 3:41 SA
  To change this template use File | Settings | File Templates.
--%>
<title>Product management</title>
<body>

<header>
    <%@ include file="/component/header.jsp" %>
    <%@ include file="/component/admin/admin-nav.jsp" %>
</header>

<div class="container-fluid">
    <div class="row">
        <!-- Left Column for Form -->
        <div class="col-md-6">
            <h2>Product form</h2>
            <form action="/admin/product" method="post">
                <div class="form-group">
                    <label>Name:</label>
                    <input type="text" class="form-control" name="name" placeholder="Enter name of product">
                    <span style="color: red">${violations_product.get('name')}</span>
                </div>
                <div class="form-group">
                    <label>Size:</label>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="size" id="flexRadioDefault1" value="S">
                        <label class="form-check-label" for="flexRadioDefault1">S</label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="size" id="flexRadioDefault2" value="M">
                        <label class="form-check-label" for="flexRadioDefault2">M</label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="size" id="flexRadioDefault3" value="L">
                        <label class="form-check-label" for="flexRadioDefault3">L</label>
                    </div>
                    <span style="color: red">${violations_product.get('size')}</span>
                </div>
                <div class="form-group">
                    <label>Price:</label>
                    <input type="number" class="form-control" name="price" placeholder="Enter price, min = 10000">
                    <span style="color: red">${violations_product.get('price')}</span>
                </div>
                <div class="form-group">
                    <label>Category:</label>
                    <select class="form-select" aria-label="Default select example" name="category_id">
                        <c:forEach items="${categories}" var="category">
                            <option value="${category.id}">${category.name}</option>
                        </c:forEach>
                    </select>
                    <span style="color: red">${violations_product.get('category')}</span>
                </div>

                <button type="submit" class="btn btn-primary mt-2">Submit</button>
            </form>
        </div>
        <!-- Right Column for Table -->
        <div class="col-md-6">
            <h2>Data Table</h2>
            <table class="table table-striped">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Price</th>
                    <th>Size name</th>
                    <th>Category name</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${products}" var="product">
                    <tr>
                        <td>${product.id}</td>
                        <td>${product.name}</td>
                        <td>${product.price}</td>
                        <td>${product.size}</td>
                        <td><a href="/admin/view/category?id=${product.category.id}">${product.category.name}</a></td>
                        <td>
                            <a href="/admin/product/edit?id=${product.id}" class="btn btn-warning">Edit</a>
                            <a href="/admin/product/delete?id=${product.id}" class="btn btn-danger">Delete</a>
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