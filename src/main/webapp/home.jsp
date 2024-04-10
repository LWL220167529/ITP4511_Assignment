<!DOCTYPE html>
<%@ page import="java.util.List, ict.bean.CampusEquipment" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <script src="js/home.js"></script>
</head>
<body>
        
    <%@ include file="header.jsp" %>
    <h1>Campus Equipments</h1>
    <jsp:useBean id="equipments" class="ict.bean.CampusEquipments" scope="request" />
    <% 
        if (equipments.getCampusEquipments() != null) {
        List<CampusEquipment> campusEquipments = equipments.getCampusEquipments(); 
    %>
    <div class="campus">Campus: 
        <select name="campus" onchange="changeCampus(this.value)">
            <option value="CW" <%= equipments.getCampus().equalsIgnoreCase("CW") ? "selected" : "" %>>Chai Wan</option>
            <option value="LWL" <%= equipments.getCampus().equalsIgnoreCase("LWL") ? "selected" : "" %>>Lee Wai Lee</option>
            <option value="ST" <%= equipments.getCampus().equalsIgnoreCase("ST") ? "selected" : "" %>>Sha Tin</option>
            <option value="TM" <%= equipments.getCampus().equalsIgnoreCase("TM") ? "selected" : "" %>>Tuen Mun</option>
            <option value="TY" <%= equipments.getCampus().equalsIgnoreCase("TY") ? "selected" : "" %>>Tsing Yi</option>
        </select>
    </div>
    <table class="showEquipment" width="75%" height="75%" border="1">
        <tr>
            <th>Equipment image</th>
            <th>Equipment name</th>
            <th>Campus</th>
            <th>Quantity</th>
            <th>Status</th>
            <th>Actions</th>
        </tr>
        <% 
            for (CampusEquipment campusEquipment : campusEquipments) { 
        %>
            <tr>
                <td width="10%" height="10%"><img width="100%" height="100%" src="<%= (campusEquipment.getEquipmentImage() != null) ? "img/" + campusEquipment.getEquipmentImage() : "http://travelmen.org/static/images/404.png" %>" /></td>
                <td><%= campusEquipment.getEquipmentName() %></td>
                <td><%= campusEquipment.getCampus() %></td>
                <td><%= campusEquipment.getQuantity() %></td>
                <td><%= campusEquipment.getStatus() %></td>
                <% if (!campusEquipment.getCampus().equalsIgnoreCase(campus)) { %>
                <th><a href="<%= request.getServletContext().getContextPath() %>/Equipment?action=reserveForm&id=<%= campusEquipment.getId() %>">Reserve</a></th>
                <% } else { %>
                <th>Damage</th>
                <% } %>
            </tr>
        <% }
    } %>
    </table>
</body>
</html>