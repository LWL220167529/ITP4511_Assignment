<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reserve Form</title>
    <link rel="stylesheet" href="css/form.css">
    <link rel="stylesheet" href="css/table.css">
</head>
<body>
    <%@ include file="header.jsp" %>
    <%@ page import="ict.bean.CampusDevice" %>
    <% 
        CampusDevice device = (CampusDevice) request.getAttribute("device");
    %>
    <from method="post">
        <input type="hidden" name="action" value="reserve">
        <input type="hidden" name="id" value="<%= device.getId() %>" %>
        <table width="75%" height="75%">
            <tr>
                <th colspan="2" ><img src="<%= (device.getDeviceImage() != null) ? "img/" + device.getDeviceImage() : "http://travelmen.org/static/images/404.png" %>" /></td>
            </tr>
            <tr>
                <td><label for="deviceName">Device Name:</label></td>
                <td><input type="text" id="deviceName" name="deviceName" value="<%= device.getDeviceName() %>" readonly></td>
            </tr>
            <tr>
                <td><label for="campus">Campus:</label></td>
                <td><input type="text" id="campus" name="campus" value="<%= device.getCampus() %>" readonly></td>
            </tr>
            <tr>
                <td><label for="quantity">Quantity:</label></td>
                <td><input type="number" id="quantity" name="quantity" min="1" max="<%= device.getQuantity() %>" value="1"></td>
            </tr>
            <tr>
                <td><label for="date">Date:</label></td>
                <td><input type="date" id="date" name="date"></td>
            </tr>
            <tr>
                <td colspan="2"><center><input type="submit" value="Reserve"></center></td>
            </tr>
        </table>
    </from>
</body>
</html>