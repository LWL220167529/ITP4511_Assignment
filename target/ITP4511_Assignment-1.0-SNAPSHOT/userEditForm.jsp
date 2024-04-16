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
        <link rel="stylesheet" href="css/form.css">
    </head>
    
    <body>
        <%@ include file="header.jsp" %>
        <% if (!role.equalsIgnoreCase("admin")) {
           
        response.sendRedirect("Equipment?action=getCampus&campus=" + campus);
        } %>
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
        <form method="post" action="User">
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
                    <td>Campus:</td>
                    <td>
                        <% campus = user.getCampus(); %>
                        <select name="campus" id="campus">
                            <option value="CW" <%= campus.equals("CW") ? "selected" : "" %>>Chai Wan</option>
                            <option value="LWL" <%= campus.equals("LWL") ? "selected" : "" %>>Lee Wai Lee </option>
                            <option value="ST" <%= campus.equals("ST") ? "selected" : "" %>>Sha Tin</option>
                            <option value="TM" <%= campus.equals("TM") ? "selected" : "" %>>Tuen Mun</option>
                            <option value="TY" <%= campus.equals("TY") ? "selected" : "" %>>Tsing Yi</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>Role:</td>
                    <td>
                        <% role = user.getRole(); %>
                        <select name="role">
                            <option value="user" <%= role.equals("user") ? "selected" : "" %>>User</option>
                            <option value="technician" <%= role.equals("technician") ? "selected" : "" %>>Technician</option>
                            <option value="staff" <%= role.equals("staff") ? "selected" : "" %>>Staff</option>
                            <option value="courier" <%= role.equals("courier") ? "selected" : "" %>>Courier</option>
                            <option value="admin" <%= role.equals("admin") ? "selected" : "" %>>Admin</option>
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
