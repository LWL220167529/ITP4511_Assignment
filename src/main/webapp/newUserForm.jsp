<%-- 
    Document   : newUserForm
    Created on : 7 Apr 2024, 4:53:13 pm
    Author     : User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>New User</title>
        <link rel="stylesheet" type="text/css" href="css/form.css" />
    </head>
    <body>
        <%@ include file="header.jsp" %>
        <form method="post" action="UserController">
            <input type="hidden" name="action" value="add">
            <table>
                <tr>
                    <td><label for="username">Username:</label></td>
                    <td><input type="text" name="username" id="username"></td>
                </tr>
                <tr>
                    <td><label for="password">Password:</label></td>
                    <td><input type="password" name="password" id="password"></td>
                </tr>
                <tr>
                    <td><label for="phone">Phone:</label></td>
                    <td><input type="text" name="phone" id="phone"></td>
                </tr>
                <tr>
                    <td><label for="email">Email:</label></td>
                    <td><input type="text" name="email" id="email"></td>
                </tr>
                <tr>
                    <td><label for="role">Role:</label></td>
                    <td>
                        <select name="role" id="role">
                            <option value="user">User</option>
                            <option value="Technician">Technician</option>
                            <option value="Staff">Staff</option>
                            <option value="Courier">Courier</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td colspan="2"><center><input type="submit" value="Add"></center></td>
                </tr>
            </table>
        </form>
    </body>
</html>
