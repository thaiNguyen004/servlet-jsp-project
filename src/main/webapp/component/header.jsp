<%--
  Created by IntelliJ IDEA.
  User: nguye
  Date: 20/03/2024
  Time: 3:20 SA
  To change this template use File | Settings | File Templates.
--%>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
        <a class="navbar-brand" href="/home">Asm</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item p-1">
                    <a class="btn btn-primary" aria-current="page" href="/">Home</a>
                </li>
                <li class="nav-item p-1">
                    <a class="btn btn-danger" href="/admin">Admin</a>
                </li>
                <c:choose>
                    <c:when test="${loggedIn}">
                        <li class="nav-item p-1">
                            <form action="/logout" method="post">
                                <button class="btn btn-warning">Logout</button>
                            </form>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="nav-item p-1">
                            <a class="btn btn-warning" href="/login">Login</a>
                        </li>
                        <li class="nav-item p-1">
                            <a class="btn btn-success" href="/signup">Signup</a>
                        </li>
                    </c:otherwise>
                </c:choose>
                <li class="nav-item p-1">
                    <a class="btn btn-secondary" href="/my-cart">My cart</a>
                </li>
                <li class="nav-item p-1">
                    <a class="btn btn-secondary" href="/my-order">My order</a>
                </li>
            </ul>
        </div>
    </div>
</nav>
