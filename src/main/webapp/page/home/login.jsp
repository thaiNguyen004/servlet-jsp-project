<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: nguye
  Date: 24/03/2024
  Time: 2:14 SA
  To change this template use File | Settings | File Templates.
--%>
<title>Login page</title>
<body>
<header>
    <%@ include file="/component/header.jsp" %>
</header>

<div class="container my-5">
    <div class="row">
        <div class="col-md-3"></div>
        <div class="col-md-6 bg-dark text-light text-center border rounded-top border-bottom-0 border-dark p-1">
            <h5 class="mb-1 text-center">Login</h5>
        </div>
        <div class="col-md-3"></div>
    </div>
    <div class="row">
        <div class="col-md-3"></div>
        <div class="col-md-6 border rounded-bottom border-dark p-3">
            <c:if test="${loginError}">
                <div class="alert alert-danger">Wrong Username or Password</div>
            </c:if>
            <form action="/login" method="post">
                <div class="form-group">
                    <label>Username</label>
                    <input type="text" class="form-control" name="username" placeholder="Enter Username" required
                           autofocus>
                </div>
                <div class="form-group">
                    <label>Password</label>
                    <input type="password" class="form-control" name="password" placeholder="Enter Password" required
                           autofocus>
                </div>
                <div class="text-center mt-3">
                    <button type="submit" class="btn btn-dark">Submit</button>
                    <a class="btn btn-success" href="/signup">Register</a>
                </div>
            </form>
        </div>
        <div class="col-md-3"></div>
    </div>
</div>

<footer>
    <%@ include file="/component/footer.jsp" %>
</footer>

</body>
