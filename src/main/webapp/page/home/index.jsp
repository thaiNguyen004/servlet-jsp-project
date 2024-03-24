<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: nguye
  Date: 20/03/2024
  Time: 1:40 SA
  To change this template use File | Settings | File Templates.
--%>
<title>Home page</title>
<body>
<header>
    <%@ include file="/component/header.jsp" %>
</header>
<%@ include file="/component/home/banner.jsp" %>

<div class="container mb-5">
    <div class="row">
        <div class="col-12">
            <h1>Home page</h1>
        </div>
    </div>

    <div class="row">
        <c:forEach var="product" items="${products}">
            <div class="col-2">
                <div class="card">
                    <img src="..." class="card-img-top" alt="...">
                    <div class="card-body" style="font-size: 14px">
                        <h5 class="card-title">${product.name}</h5>
                        <p class="card-text">${product.size}</p>
                        <p class="card-text">${product.price}</p>
                        <p class="card-text">${product.quantity}</p>
                        <a href="/product/detail-info?id=${product.id}" class="btn btn-primary">Detail</a>
                        <a href="/product/detail-info?id=${product.id}" class="btn btn-success">Add to cart</a>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
    <nav class="mt-3">
        <ul class="pagination">
            <li class="page-item ${currentPage eq 0 ? 'disabled' : ''}">
                <a class="page-link" href="/home/pagination?page=${currentPage-1}" tabindex="-1">Previous</a>
            </li>
            <li class="page-item ${currentPage eq 0 ? 'active' : ''}"><a class="page-link"
                                                                     href="/home/pagination?page=0">0</a></li>
            <li class="page-item ${currentPage eq 1 ? 'active' : ''}"><a class="page-link"
                                                                         href="/home/pagination?page=1">1</a></li>
            <li class="page-item ${currentPage eq 2 ? 'active' : ''}"><a class="page-link"
                                                                         href="/home/pagination?page=2">2</a></li>
            <li class="page-item ${products.size() < 10 ? 'disabled' : ''}">
                <a class="page-link" href="/home/pagination?page=${currentPage+1}">Next</a>
            </li>
        </ul>
    </nav>

</div>

<footer>
    <%@ include file="/component/footer.jsp" %>
</footer>
</body>