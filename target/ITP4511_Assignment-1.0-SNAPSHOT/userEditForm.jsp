<%-- 
    Document   : userEditForm
    Created on : 7 Apr 2024, 4:13:58 pm
    Author     : User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="ict.bean.User"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User Edit Form</title>
        <% User user = (User) session.getAttribute("userForm"); 
            if (user == null) { 
        %>
        <script>
            alert("User not found");
            window.location.href = "home.jsp";
            
        </script>
        <%
            } else {
        %>
        <link rel="stylesheet" type="text/css" href="css/form.css" />
    </head>
    
    <body>
        <%@ include file="header.jsp" %>
        <form method="post" action="UserController">
            <input type="hidden" name="action" value="update">
            <input type="hidden" name="id" value="<%= user.getId() %>">
            <table>
                <tr>
                    <td>Username:</td>
                    <td><input type="text" name="username" value="<%= user.getUsername() %>"></td>
                </tr>
                <tr>
                    <td>Phone:</td>
                    <td><input type="text" name="phone" value="<%= user.getPhone() %>"></td>
                </tr>
                <tr>
                    <td>Email:</td>
                    <td><input type="text" name="email" value="<%= user.getEmail() %>"></td>
                </tr>
                <tr>
                    <td>Role:</td>
                    <td>
                        <select name="role">
                            <option value="user" <%= user.getRole().equals("user") ? "selected" : "" %>>User</option>
                            <option value="technician" <%= user.getRole().equals("technician") ? "selected" : "" %>>Technician</option>
                            <option value="staff" <%= user.getRole().equals("staff") ? "selected" : "" %>>Staff</option>
                            <option value="courier" <%= user.getRole().equals("courier") ? "selected" : "" %>>Courier</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td colspan="2"><center><input type="submit" value="Update"></center></td>
                </tr>
            </table>
        </form>
    </body>
    <% } %>
</html>
