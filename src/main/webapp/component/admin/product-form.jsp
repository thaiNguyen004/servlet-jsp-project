<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: nguye
  Date: 20/03/2024
  Time: 4:25 SA
  To change this template use File | Settings | File Templates.
--%><%--Product form--%>
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
                <select name="size_id" class="form-select" aria-label="Default select example">
                    <c:forEach items="${sizes}" var="size">
                        <option value="${size.id}">${size.sizeName}</option>
                    </c:forEach>
                </select>
                <span style="color: red">${violations_product.get('size')}</span>
            </div>
            <%--<div class="mb-3">
                <label for="formFile" class="form-label">Picture of product</label>
                <input class="form-control" name="picture" type="file" id="formFile" accept="image/jpg,image.png">
                <span style="color: red">${violations_product.get('picture')}</span>
            </div>--%>
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
                <th>Picture ID</th>
                <th>Category name</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${products}" var="product">
                <tr>
                    <td>${product.id}</td>
                    <td>${product.name}</td>
                    <td>${product.price}</td>
                    <td><a href="/admin/view/size?id=${product.size.id}">${product.size.sizeName}</a></td>
                    <td>
                        <c:if test="${product.pictures.size() == 0}">
                            Nothing!
                        </c:if>
                        <c:if test="${product.pictures.size() != 0}">
                            <select class="form-select" aria-label="Default select example">
                                <c:forEach items="${product.pictures}" var="picture">
                                    <option value="${picture.id}">${picture.id}</option>
                                </c:forEach>
                            </select>
                        </c:if>
                    </td>
                    <td><a href="/admin/view/category?id=${product.category.id}">${product.category.name}</a></td>
                </tr>
            </c:forEach>
            <!-- Add more rows here for dynamic data -->
            </tbody>
        </table>
    </div>
</div>

