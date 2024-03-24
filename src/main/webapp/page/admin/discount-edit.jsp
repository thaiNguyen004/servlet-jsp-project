<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="org.servlet.assignment.order.Discount" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.time.LocalDateTime" %>
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
        <div class="col-md-5">
            <h2>Discount form</h2>

            <%
                Discount discount = (Discount) request.getAttribute("discount");
                LocalDateTime startDate = discount.getStart();
                LocalDateTime endDate = discount.getEnd();
            %>

            <form action="/admin/discount/update" method="post">
                <input type="number" name="id" value="${discount.id}" hidden>
                <div class="form-group">
                    <label>Start <span class="badge bg-warning">(current is <%=startDate%>)</span>:</label>
                    <input type="datetime-local" class="form-control" name="start" placeholder="Start time of discount">
                    <span style="color: red">${violations_discount.get('start')}</span>
                </div>
                <div class="form-group">
                    <label>End <span class="badge bg-warning">(current is <%=endDate%>)</span>:</label>
                    <input type="datetime-local" class="form-control" name="end"  placeholder="End time of discount">
                    <span style="color: red">${violations_discount.get('end')}</span>
                </div>
                <div class="form-group">
                    <label>Value of discount:</label>
                    <input type="number" class="form-control" name="value" value="${discount.value}" placeholder="Enter value of discount">
                    <span style="color: red">${violations_discount.get('value')}</span>
                </div>
                <div class="form-group">
                    <label>Quantity of discount:</label>
                    <input type="number" class="form-control" name="quantity" value="${discount.quantity}" placeholder="Enter quantity of discount">
                    <span style="color: red">${violations_discount.get('quantity')}</span>
                </div>

                <button type="submit" class="btn btn-primary mt-2">Submit</button>
            </form>
        </div>

    </div>
</div>


<footer>
    <%@ include file="/component/footer.jsp" %>
</footer>

</body>