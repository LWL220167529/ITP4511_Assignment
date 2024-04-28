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
        <% if (!role.equalsIgnoreCase("admin") && headerUser.getId() != user.getId()) {
           
        response.sendRedirect("");
        } %>
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
                <% if (role.equalsIgnoreCase("admin")) { %>
                <tr>
                    <td>Campus:</td>
                    <td>
                        <% campus = user.getCampus(); %>
                        <% role = user.getRole(); %>
                        <select name="campus" id="campus">
                            <option value="CW" <%= campus.equals("CW") && !role.equals("courier") ? "selected" : "" %>>Chai Wan</option>
                            <option value="LWL" <%= campus.equals("LWL") && !role.equals("courier")  ? "selected" : "" %>>Lee Wai Lee </option>
                            <option value="ST" <%= campus.equals("ST") && !role.equals("courier")  ? "selected" : "" %>>Sha Tin</option>
                            <option value="TM" <%= campus.equals("TM") && !role.equals("courier")  ? "selected" : "" %>>Tuen Mun</option>
                            <option value="TY" <%= campus.equals("TY") && !role.equals("courier")  ? "selected" : "" %>>Tsing Yi</option>
                            <option value="CW" <%= role.equals("courier") ? "selected" : "" %>>Other</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>Role:</td>
                    <td>
                        <select name="role">
                            <option value="user" <%= role.equals("user") ? "selected" : "" %>>User</option>
                            <option value="technician" <%= role.equals("technician") ? "selected" : "" %>>Technician</option>
                            <option value="staff" <%= role.equals("staff") ? "selected" : "" %>>Staff</option>
                            <option value="courier" <%= role.equals("courier") ? "selected" : "" %>>Courier</option>
                            <option value="admin" <%= role.equals("admin") ? "selected" : "" %>>Admin</option>
                        </select>
                    </td>
                </tr>
                <% } %>
                <tr>
                    <td colspan="2"><center><input type="submit" value="Update"></center></td>
                </tr>
            </table>
        </form>
    </body>
    <% } %>
</html>
