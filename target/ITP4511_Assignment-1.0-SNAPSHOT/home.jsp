<!DOCTYPE html>
<%@ page import="java.util.List, ict.bean.CampusDevice, ict.bean.CampusDevices" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="stylesheet" href="css/table.css">
    <script src="js/home.js"></script>
</head>
<body>
        
    <%@ include file="header.jsp" %>
    <h1>Campus Devices</h1>
    <% 
        CampusDevices campusDevices = (CampusDevices) request.getAttribute("devices");
        if (campusDevices.getCampusDevices() != null) {
        List<CampusDevice> devices = campusDevices.getCampusDevices(); 
    %>
    <div class="campus">Campus: 
        <select name="campus" onchange="changeCampus(this.value)">
            <option value="CW" <%= campusDevices.getCampus().equalsIgnoreCase("CW") ? "selected" : "" %>>Chai Wan</option>
            <option value="LWL" <%= campusDevices.getCampus().equalsIgnoreCase("LWL") ? "selected" : "" %>>Lee Wai Lee</option>
            <option value="ST" <%= campusDevices.getCampus().equalsIgnoreCase("ST") ? "selected" : "" %>>Sha Tin</option>
            <option value="TM" <%= campusDevices.getCampus().equalsIgnoreCase("TM") ? "selected" : "" %>>Tuen Mun</option>
            <option value="TY" <%= campusDevices.getCampus().equalsIgnoreCase("TY") ? "selected" : "" %>>Tsing Yi</option>
        </select>
    </div>
    <table class="showDevice" width="75%" height="75%" border="1">
        <tr>
            <th>Device image</th>
            <th>Device name</th>
            <th>Campus</th>
            <th>Quantity</th>
            <th>Status</th>
            <th>Actions</th>
        </tr>
        <% 
            for (CampusDevice device : devices) { 
        %>
            <tr>
                <td width="10%" height="10%"><img width="100%" height="100%" src="<%= (device.getDeviceImage() != null) ? "img/" + device.getDeviceImage() : "http://travelmen.org/static/images/404.png" %>" /></td>
                <td><center><%= device.getDeviceName() %></center></td>
                <td><center><%= device.getCampus() %></center></td>
                <td><center><%= device.getQuantity() %></center></td>
                <td><center><%= device.getStatus() %></center></td>
                <% if (!device.getCampus().equalsIgnoreCase(campus)) { %>
                <th><a href="<%= request.getServletContext().getContextPath() %>/DeviceController?action=reserveForm&id=<%= device.getId() %>">Reserve</a></th>
                <% } else { %>
                <th>Damage</th>
                <% } %> 
            </tr>
        <% }
    } %>
    </table>
</body>
</html>