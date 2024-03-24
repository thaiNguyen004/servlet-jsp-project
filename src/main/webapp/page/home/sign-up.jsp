<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: nguye
  Date: 24/03/2024
  Time: 2:07 SA
  To change this template use File | Settings | File Templates.
--%>
<title>Signup</title>
<body>
<header>
    <%@ include file="/component/header.jsp" %>
</header>


<div class="container my-5">

    <div class="row">
        <div class="col-md-3"></div>
        <div class="col-md-6 bg-dark text-light text-center border rounded-top border-bottom-0 border-dark p-1">
            <h5 class="mb-1 text-center">Register User</h5>
        </div>
        <div class="col-md-3"></div>
    </div>
    <div class="row">
        <div class="col-md-3"></div>
        <div class="col-md-6 border rounded-bottom border-dark p-3">
            <form action="/signup" method="POST">
                <c:if test="${signup_success}">
                    <div class="alert alert-success">
                        Registration completed. You can now <a href="/login">login</a>
                    </div>
                </c:if>
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
                    <label>confirm password:</label>
                    <input type="password" class="form-control" name="passwordConfirm"
                           placeholder="Enter password of user">
                    <span style="color: red">${violations_user.get('passwordConfirm')}</span>
                </div>

                <div class="form-group text-center mt-2">
                    <input type="submit" class="btn btn-dark center" value="Sign Up"/>
                    <p>Already have an account? <a href="/login">Sign in</a></p>
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
