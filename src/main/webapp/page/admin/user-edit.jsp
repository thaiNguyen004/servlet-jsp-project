<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: nguye
  Date: 23/03/2024
  Time: 6:22 CH
  To change this template use File | Settings | File Templates.
--%>
<title>User update</title>
<body>

<header>
    <%@ include file="/component/header.jsp" %>
    <%@ include file="/component/admin/admin-nav.jsp" %>
</header>

<div class="container-fluid">
    <div class="row">
        <!-- Left Column for Form -->
        <div class="col-md-6">
            <h2>User form update</h2>
            <form action="/admin/user/update" method="post">
                <div class="form-group">
                    <input type="text" value="${user.id}" class="form-control" name="id" hidden>
                </div>

                <div class="form-group">
                    <label>FirstName:</label>
                    <input type="text" class="form-control" name="firstName" value="${user.firstName}" placeholder="Enter firstName of user">
                    <span style="color: red">${violations_user.get('firstName')}</span>
                </div>

                <div class="form-group">
                    <label>lastName:</label>
                    <input type="text" class="form-control" name="lastName" value="${user.lastName}" placeholder="Enter lastName of user">
                    <span style="color: red">${violations_user.get('lastName')}</span>
                </div>

                <div class="form-group">
                    <label>username:</label>
                    <input type="text" class="form-control" name="username" value="${user.username}" placeholder="Enter username of user">
                    <span style="color: red">${violations_user.get('username')}</span>
                </div>

                <div class="form-group">
                    <label>email:</label>
                    <input type="email" class="form-control" name="email" value="${user.email}" placeholder="Enter email of user">
                    <span style="color: red">${violations_user.get('email')}</span>
                </div>

                <div class="form-group">
                    <label>password:</label>
                    <input type="password" class="form-control" name="password" value="${user.password}" placeholder="Enter password of user">
                    <span style="color: red">${violations_user.get('password')}</span>
                </div>

                <div class="form-group">
                    <label>role:</label>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="role" id="flexRadioDefault1" value="EMPLOYEE"
                        ${user.role eq 'EMPLOYEE' ? 'checked' : ''}>
                        <label class="form-check-label" for="flexRadioDefault1">EMPLOYEE</label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="role" id="flexRadioDefault2" value="ADMIN"
                        ${user.role eq 'ADMIN' ? 'checked' : ''}>
                        <label class="form-check-label" for="flexRadioDefault2">ADMIN</label>
                    </div>
                    <span style="color: red">${violations_user.get('role')}</span>
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
