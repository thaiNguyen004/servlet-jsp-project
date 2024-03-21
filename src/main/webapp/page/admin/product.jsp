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
    <%@include file="/component/admin/product-form.jsp"%>
    <hr style="color: red; border-width: 2px;">
    <%@include file="/component/admin/size-form.jsp"%>
</div>

<footer>
    <%@ include file="/component/footer.jsp" %>
</footer>

</body>