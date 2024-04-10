<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Wish List</title>
</head>
<body>
    <%@ include file="header.jsp" %>
    <%@ page import="java.util.List, ict.bean.WishEquipment" %>
    <h1>Wish List</h1>
    <table width="75%" height="75%" border="1">
        <tr>
            <th>Equipment image</th>
            <th>Equipment name</th>
            <th>Belong Campus</th>
            <th>Destination</th>
            <th>Quantity</th>
            <th>Status</th>
            <th>Actions</th>
        </tr>
        <jsp:useBean id="reserves" class="ict.bean.WishList" scope="request" />
        <% 
            if (reserves.getList() != null) {
            List<WishEquipment> wishEquipments = reserves.getList();
            for (WishEquipment wishEquipment : wishEquipments) {
                if (wishEquipment.getStatus().equalsIgnoreCase("requesting")) { %>
        <tr>
            <td width="10%" height="10%"><img width="100%" height="100%" src="<%= (wishEquipment.getEquipmentImage() != null)? "img/" + wishEquipment.getEquipmentImage() : "http://travelmen.org/static/images/404.png" %>" /></td>
            <td><%= wishEquipment.getEquipmentName() %></td>
            <td><%= wishEquipment.getBelongCampusName() %></td>
            <td><%= wishEquipment.getDestinationCampusName() %></td>
            <td><%= wishEquipment.getQuantity() %></td>
            <td><%= wishEquipment.getStatus() %></td>
            <th><a href="#">Remove</a></th>
        </tr>
        <% } } } %>
    </table>
</body>
</html>