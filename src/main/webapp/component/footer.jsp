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
<p>&copy; Copyright <%= currentYear %> ThaiNguyen &amp;</p>
</html>

