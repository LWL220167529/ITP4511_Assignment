<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reserve Status Form</title>
    <link rel="stylesheet" type="text/css" href="css/table.css" />

</head>
<body>
    <%@ include file="header.jsp" %>
    <%@ page import="ict.bean.User, java.util.List" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <jsp:useBean id="reserve" class="ict.bean.WishEquipment" scope="request" />
    <jsp:useBean id="campusEquipment" class="ict.bean.CampusEquipment" scope="request" />
    <jsp:useBean id="users" class="ict.bean.Users" scope="request" />
    <table border="1" width="75%" height="75%" >
        <tr>
            <th>Equipment image</th>
            <th>Equipment name</th>
            <th>Campus</th>
            <th>Quantity</th>
            <th>Status</th>
        </tr>
        <tr>
            <td width="10%" height="10%"><img width="100%" height="100%" src="<%= (campusEquipment.getEquipmentImage() != null) ? "img/" + campusEquipment.getEquipmentImage() : "http://travelmen.org/static/images/404.png" %>" /></td>
            <td><%= campusEquipment.getEquipmentName() %></td>
            <td><%= campusEquipment.getCampus() %></td>
            <td><%= campusEquipment.getQuantity() %></td>
            <td><%= campusEquipment.getStatus() %></td>
        </tr>
    </table>
    <form action="Reserve" method="post" style="width: fit-content; margin: auto; text-align: center;">
        <input type="hidden" name="action" value="updateStatus" />
        <input type="hidden" name="id" value="<%= reserve.getId() %>" />
        <div class="container">
            <h1>Reserve Status Form</h1>
            <p>Please fill in this form to update the status of the reserve.</p>
            <hr>
            <label for="campusEquipmentName" ><%= reserve.getEquipmentName() %> </label>
            <br>
            <div style="height: auto; text-align: centers;">
                <img src="img/<%= reserve.getEquipmentImage() %>" alt="<%= reserve.getEquipmentName() %>" width="100" height="100">
            </div>
            <br>
            <label for="getBelongCampusName"><b>Campus: </b><%= reserve.getBelongCampusName() %></label>
            <br>
            <label for="quantity"><b>Quantity: </b><%= reserve.getQuantity() %></label>
            <br>
            <label for="status"><b>Status: </b><%= reserve.getStatus() %></label><br>
            Courier:
            <% if (!users.listIsEmpty()) { %>
            <select name="Courier" required>
                <c:forEach var="user" items="${users.getList()}">
                    <option value="${user.getId()}" ${user.getId() == reserve.getDeliveryUserId() ? "selected" : ""}>${user.getUsername()}</option>
                </c:forEach>
            </select>
            <% } else {%>
                No courier available
            <% } %>
            <hr>
            <% if (campusEquipment.getQuantity() > reserve.getQuantity()) { %>
            <center><button type="submit" class="registerbtn">Update</button></center>
            <% } else { %>
                <a>The equipment not enough</a>
            <% } %>
        </div>
    </form>
</body>
</html>