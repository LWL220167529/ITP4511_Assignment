<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    <% if (session.getAttribute("username") == null) { 
        response.sendRedirect("login.jsp");
    } %>
        
    <%@ include file="header.jsp" %>
    
</body>
</html>