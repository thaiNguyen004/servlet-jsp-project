<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: nguye
  Date: 23/03/2024
  Time: 6:14 CH
  To change this template use File | Settings | File Templates.
--%>
<title>User management</title>
<body>

<header>
    <%@ include file="/component/header.jsp" %>
    <%@ include file="/component/admin/admin-nav.jsp" %>
</header>

<div class="container-fluid">
    <div class="row">
        <!-- Left Column for Form -->
        <div class="col-md-6">
            <h2>User form</h2>
            <form action="/admin/user" method="post">
                <div class="form-group">
                    <label>FirstName:</label>
                    <input type="text" class="form-control" name="firstName" placeholder="Enter firstName of user">
                    <span style="color: red">${violations_user.get('firstName')}</span>
                </div>

                <div class="form-group">
                    <label>lastName:</label>
                    <input type="text" class="form-control" name="lastName" placeholder="Enter lastName of user">
                    <span style="color: red">${violations_user.get('lastName')}</span>
                </div>

                <div class="form-group">
                    <label>username:</label>
                    <input type="text" class="form-control" name="username" placeholder="Enter username of user">
                    <span style="color: red">${violations_user.get('username')}</span>
                </div>

                <div class="form-group">
                    <label>email:</label>
                    <input type="email" class="form-control" name="email" placeholder="Enter email of user">
                    <span style="color: red">${violations_user.get('email')}</span>
                </div>

                <div class="form-group">
                    <label>password:</label>
                    <input type="password" class="form-control" name="password" placeholder="Enter password of user">
                    <span style="color: red">${violations_user.get('password')}</span>
                </div>

                <div class="form-group">
                    <label>role:</label>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="role" id="flexRadioDefault1"
                               value="EMPLOYEE">
                        <label class="form-check-label" for="flexRadioDefault1">EMPLOYEE</label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="role" id="flexRadioDefault2" value="ADMIN">
                        <label class="form-check-label" for="flexRadioDefault2">ADMIN</label>
                    </div>
                    <span style="color: red">${violations_user.get('role')}</span>
                </div>

                <button type="submit" class="btn btn-primary mt-2">Submit</button>
            </form>
        </div>
        <!-- Right Column for Table -->
        <div class="col-md-14">
            <h2>Data Table</h2>
            <table class="table table-striped">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>first name</th>
                    <th>last name</th>
                    <th>username</th>
                    <th>email</th>
                    <td>password</td>
                    <td>role</td>
                    <th>active at</th>
                    <th>active</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${users}" var="user">
                    <tr>
                        <td>${user.id}</td>
                        <td>${user.firstName}</td>
                        <td>${user.lastName}</td>
                        <td>${user.username}</td>
                        <td>${user.email}</td>
                        <td>${user.password}</td>
                        <td>${user.role}</td>
                        <td>${user.activeAt}</td>

                        <td>
                            <c:choose>
                                <c:when test="${user.active}">
                                    <a href="/admin/user/deactivate?id=${user.id}"
                                       class="btn btn-secondary">Deactivate</a>
                                </c:when>
                                <c:otherwise>
                                    <a href="/admin/user/activate?id=${user.id}" class="btn btn-primary">Activate</a>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <a href="/admin/user/edit?id=${user.id}" class="btn btn-warning">Edit</a>
                            <c:choose>
                                <c:when test="${user.lock}">
                                    <a href="/admin/user/unlock?id=${user.id}" class="btn btn-primary">Unlock</a>
                                </c:when>
                                <c:otherwise>
                                    <a href="/admin/user/lock?id=${user.id}" class="btn btn-secondary">Lock</a>
                                </c:otherwise>
                            </c:choose>
                            <a href="/admin/user/delete?id=${user.id}" class="btn btn-danger">Delete</a>
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