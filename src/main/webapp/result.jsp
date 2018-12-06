<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>Selected items</title>
    <meta charset="UTF-8">
</head>
<body>
<center>
    <h1>
        <% out.println(request.getParameter("Type") + 'S');%>
    </h1>
        <%
List result= (List) request.getAttribute("items");
Iterator it = result.iterator();
out.println("<br>We have <br><br>");
while(it.hasNext()){
out.println(it.next()+"<br>");
}
%>
</body>
</html>