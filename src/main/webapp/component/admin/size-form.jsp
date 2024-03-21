<%--
  Created by IntelliJ IDEA.
  User: nguye
  Date: 20/03/2024
  Time: 4:27 SA
  To change this template use File | Settings | File Templates.
--%>
<div class="row">
    <!-- Left Column for Form -->
    <div class="col-md-6">
        <h2>Size form</h2>
        <form action="/admin/size" method="post">
            <div class="form-group">
                <label>Size options:</label>
                <select name="sizeName" class="form-select" aria-label="Default select example">
                    <option selected disabled value>Choose size!</option>
                    <option value="X">X</option>
                    <option value="XL">XL</option>
                    <option value="XXL">XXL</option>
                </select>
                <span style="color: red">
                    ${violations_size.get('sizeName')}
                </span>
            </div>
            <div class="form-group">
                <label>Width:</label>
                <input type="number" class="form-control" name="width"
                       placeholder="Enter width of size, min 45, max 60">
                <span style="color: red">
                    ${violations_size.get('width')}
                    </span>
            </div>
            <div class="form-group">
                <label>Length:</label>
                <input type="number" class="form-control" name="length"
                       placeholder="Enter width of size, min 63, max 70">
                <span style="color: red">
                    ${violations_size.get('length')}
                </span>
            </div>
            <button type="submit" class="btn btn-primary mt-2">Submit</button>
        </form>
    </div>
    <!-- Right Column for Table -->
    <div class="col-md-6">
        <h2>Data Table</h2>
        <table class="table table-striped">
            <thead>
            <tr>
                <th>ID</th>
                <th>Size name</th>
                <th>Width</th>
                <th>Length</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${sizes}" var="size">
                <tr>
                    <td>${size.id}</td>
                    <td>${size.sizeName}</td>
                    <td>${size.width}</td>
                    <td>${size.length}</td>
                </tr>
            </c:forEach>
            <!-- Add more rows here for dynamic data -->
            </tbody>
        </table>
    </div>
</div>