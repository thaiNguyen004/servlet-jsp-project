<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: nguye
  Date: 22/03/2024
  Time: 5:02 SA
  To change this template use File | Settings | File Templates.
--%>
<title>Category management</title>
<body>

<header>
    <%@ include file="/component/header.jsp" %>
    <%@ include file="/component/admin/admin-nav.jsp" %>
</header>

<div class="row">
    <!-- Left Column for Form -->
    <div class="col-md-4">
        <h2>Category form</h2>
        <form action="/admin/category" method="post">
            <div class="form-group">
                <label>Name:</label>
                <input type="text" class="form-control" name="name" placeholder="Enter name of category">
                <span style="color: red">${violations_category.get('name')}</span>
            </div>

            <button type="submit" class="btn btn-primary mt-2">Submit</button>
        </form>
    </div>
    <!-- Right Column for Table -->
    <div class="col-md-8">
        <h2>Data Table</h2>
        <table class="table table-striped">
            <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Created at</th>
                <th>Updated at</th>
                <th>Products</th>
                <td>Actions</td>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${categories}" var="category">
                <tr>
                    <td>${category.id}</td>
                    <td>${category.name}</td>
                    <td>${category.createdAt}</td>
                    <td>${category.updatedAt}</td>
                    <td>
                        <c:if test="${category.products.size() == 0}">
                            Nothing!
                        </c:if>
                        <c:if test="${category.products.size() != 0}">
                            <select class="form-select" aria-label="Default select example">
                                <c:forEach items="${category.products}" var="product">
                                    <option value="${product.id}">${product.name}</option>
                                </c:forEach>
                            </select>
                        </c:if>
                    </td>
                    <td>
                    <td>
                        <a href="/admin/category/edit?id=${category.id}" class="btn btn-warning">Edit</a>
                        <a href="/admin/category/delete?id=${category.id}" class="btn btn-danger">Delete</a>
                    </td>
                </tr>
            </c:forEach>
            <!-- Add more rows here for dynamic data -->
            </tbody>
        </table>
    </div>
</div>

<footer>
    <%@ include file="/component/footer.jsp" %>
</footer>

</body>