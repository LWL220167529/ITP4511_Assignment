<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reserve Form</title>
</head>
<body>
    <%@ include file="header.jsp" %>
    <%@ page import="ict.bean.CampusEquipment" %>
    <% 
        CampusEquipment equipment = (CampusEquipment) request.getAttribute("equipment");
    %>
    <form method="post" action="Reserve">
        <input type="hidden" name="action" value="reserve">
        <input type="hidden" name="equipmentId" value="<%= equipment.getId() %>" %>
        <table width="75%" height="75%">
            <tr>
                <th colspan="2" ><img src="<%= (equipment.getEquipmentImage() != null) ? "img/" + equipment.getEquipmentImage() : "http://travelmen.org/static/images/404.png" %>" /></td>
            </tr>
            <tr>
                <td><label for="equipmentName">Equipment Name:</label></td>
                <td><input type="text" id="equipmentName" name="equipmentName" value="<%= equipment.getEquipmentName() %>" readonly></td>
            </tr>
            <tr>
                <td><label for="belongCampusId">Campus:</label></td>
                <td><input type="text" id="belongCampusId" name="belongCampusId" value="<%= equipment.getCampus() %>" readonly></td>
            </tr>
            <tr>
                <td><label for="quantity">Quantity:</label></td>
                <td><input type="number" id="quantity" name="quantity" min="1" max="<%= equipment.getQuantity() %>" value="1"></td>
            </tr>
            <tr>
                <td><label for="date">Date:</label></td>
                <td><input type="date" id="date" name="date" required></td>
            </tr>
            <tr>
                <td colspan="2"><center><input type="submit" value="Reserve"></center></td>
            </tr>
        </table>
    </form>
</body>
</html>