<%-- 
    Document   : user
    Created on : 6 Apr 2024, 11:47:41 pm
    Author     : User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="ict.bean.User, ict.db.UserDB, java.util.List"%>
<%@taglib uri="/WEB-INF/tlds/user.tld" prefix="ict"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User</title>
        <link rel="stylesheet" type="text/css" href="css/user.css" />
    </head>
    <body>
        <%@ include file="header.jsp" %>
        <table class="showUser" border="1">
            <tr>
                <th colspan="5"><a href="newUserForm.jsp">New User</a></th>
            </tr>
            <tr>
                <th>Username</th>
                <th>Phone</th>
                <th>Email</th>
                <th>Role</th>
                <th>Edit</th>
            </tr>
            <% 
                String dbUrl = request.getServletContext().getInitParameter("dbUrl");
                String dbUser = request.getServletContext().getInitParameter("dbUser");
                String dbPassword = request.getServletContext().getInitParameter("dbPassword");
        
                UserDB db = new UserDB(dbUrl, dbUser, dbPassword);
                List<User> users = (List<User>) db.getUsers();
                
            %>
            
            <ict:showUser users="<%=users%>" tagType="table" />
        </table>
    </body>
</html>
