<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <style>
        h1, p {
            text-align: center;
        }
        table {
            margin: auto;
        }
        td {
            padding: 5px;
            text-align: center;
        }
        label {
            display: inline-block;
            width: 100px;
            text-align: right;
        }
    </style>
</head>
<body>
    <%@ page import="ict.bean.User" %>
    <% if (request.getAttribute("error") != null) { %>
        <p style="color: red;"><%= request.getAttribute("error") %></p>
    <% } %>
    <% if (session.getAttribute("user") != null) { 
        User user = (User) session.getAttribute("user");
        response.sendRedirect("Equipment?action=getCampus&campus=" + user.getCampus());
    } 
    String message =
    (String) session.getAttribute("error"); if (message != null) { %>
    <script>
      alert("<%=message%>");
    </script>
    <% session.removeAttribute("error"); } %>
    <h1>Welcome to Hong Kong Institute of Professional Education</h1>
    <form action="Login" method="post">
        <input type="hidden" name="action" value="authenticate">
        <table>
            <tr>
                <td><label for="username">Username:</label></td>
                <td><input type="text" id="username" name="username" placeholder="name / email / phone"></td>
            </tr>
            <tr>
                <td><label for="password">Password:</label></td>
                <td><input type="password" id="password" name="password"></td>
            </tr>
            <tr>
                <td colspan="2"><input type="submit" value="Login"></td>
            </tr>
        </table>
    </form>
</body>
</html>