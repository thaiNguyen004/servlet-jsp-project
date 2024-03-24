<%--
  Created by IntelliJ IDEA.
  User: nguye
  Date: 20/03/2024
  Time: 1:42 SA
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.util.GregorianCalendar, java.util.Calendar" %>
<%
    GregorianCalendar currentDate = new GregorianCalendar();
    int currentYear = currentDate.get(Calendar.YEAR);
%>
<p class="text-center">&copy; Copyright <%= currentYear %> <a href="https://github.com/thaiNguyen004">ThaiNguyen github</a></p>
</html>

