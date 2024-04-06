<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <style>
        h1, p {
            text-align: center;
        }
        table {
            margin: auto;
        }
        td {
            padding: 5px;
        }
        label {
            display: inline-block;
            width: 100px;
            text-align: right;
        }
    </style>
</head>
<body>
    <% if (request.getAttribute("error") != null) { %>
        <p style="color: red;"><%= request.getAttribute("error") %></p>
    <% } %>
    <h1>Welcome to Hong Kong Institute of Professional Education</h1>
    <form action="LoginController" method="post">
        <input type="hidden" name="action" value="authenticate">
        <table>
            <tr>
                <td><label for="username">Username:</label></td>
                <td><input type="text" id="username" name="username"></td>
            </tr>
            <tr>
                <td><label for="password">Password:</label></td>
                <td><input type="password" id="password" name="password"></td>
            </tr>
            <tr>
                <td colspan="2"><center><input type="submit" value="Login"></center></td>
            </tr>
        </table>
    </form>
</body>
</html>