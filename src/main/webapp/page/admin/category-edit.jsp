<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: nguye
  Date: 23/03/2024
  Time: 3:14 CH
  To change this template use File | Settings | File Templates.
--%>
<title>Category update</title>
<body>

<header>
    <%@ include file="/component/header.jsp" %>
    <%@ include file="/component/admin/admin-nav.jsp" %>
</header>

<div class="row">
    <!-- Left Column for Form -->
    <div class="col-md-4">
        <h2>Category update form</h2>
        <form action="/admin/category/update" method="post">
            <div class="form-group">
                <input type="text" class="form-control" name="id" value="${category.id}" hidden="">
            </div>
            <div class="form-group">
                <label>Name:</label>
                <input type="text" class="form-control" value="${category.name}" name="name" placeholder="Enter name of category">
                <span style="color: red">${violations_category.get('name')}</span>
            </div>
            <div class="form-group">
                <label>List products of category:</label>
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Price</th>
                        <th>Size name</th>
                        <th>Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${category.products}" var="product">
                        <tr>
                            <td>${product.id}</td>
                            <td>${product.name}</td>
                            <td>${product.price}</td>
                            <td>${product.size}</td>
                            <td>
                                <a href="/admin/category/delete-product?productId=${product.id}&categoryId=${category.id}" class="btn btn-danger">Delete</a>
                            </td>
                        </tr>
                    </c:forEach>
                    <!-- Add more rows here for dynamic data -->
                    </tbody>
                </table>
            </div>

            <button type="submit" class="btn btn-primary mt-2">Submit</button>
        </form>
    </div>
</div>

<footer>
    <%@ include file="/component/footer.jsp" %>
</footer>

</body>