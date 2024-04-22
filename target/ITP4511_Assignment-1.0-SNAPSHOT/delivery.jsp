<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Delivery</title>
    <link rel="stylesheet" href="css/table.css" />
    <script>
        function ChangeCampus() {
          window.location.href = "Reserve?action=delivery&campus=" + document.getElementById("campus").value + "&status=" + document.getElementById("status").value;
        }
    </script>
</head>
<body>
    <%@ include file="header.jsp" %>
    <%@ page import="java.util.List, ict.bean.WishEquipment" %>
    <jsp:useBean id="reserves" class="ict.bean.WishList" scope="request" />
    <jsp:useBean id="users" class="ict.bean.Users" scope="request" />
    <jsp:useBean id="dates" class="java.util.List" scope="request" />
    <% 
      String selectedDate = request.getParameter("date"); 
      String status = request.getParameter("status");
    %>

    <div style="
    display: flex;
    width: 75%;
    justify-content: space-between;
    margin: auto;
    margin-top: 20px;">
        <div>
            Campus: 
            <select name="campus" id="campus" onchange="ChangeCampus()">
                <option value="CW" <%= "CW".equalsIgnoreCase(request.getAttribute("campus").toString()) ? "selected" : "" %>>Chai Wan</option>
                <option value="LWL" <%= "LWL".equalsIgnoreCase(request.getAttribute("campus").toString()) ? "selected" : "" %>>Lee Wai Lee</option>
                <option value="ST" <%= "ST".equalsIgnoreCase(request.getAttribute("campus").toString()) ? "selected" : "" %>>Sha Tin</option>
                <option value="TM" <%= "TM".equalsIgnoreCase(request.getAttribute("campus").toString()) ? "selected" : "" %>>Tuen Mun</option>
                <option value="TY" <%= "TY".equalsIgnoreCase(request.getAttribute("campus").toString()) ? "selected" : "" %>>Tsing Yi</option>
            </select>
        </div>
        <div>
          Status:
          <select name="status" id="status" onchange="ChangeCampus()">
            <option value="approved" <%= "Approved".equalsIgnoreCase(status) ? "selected" : "" %>>Approved</option>
            <option value="delivery" <%= "Delivery".equalsIgnoreCase(status) ? "selected" : "" %>>Delivery</option>
            <option value="completed" <%= "completed".equalsIgnoreCase(status) ? "selected" : "" %>>Completed</option>
          </select>
        </div>
    </div>
    <table border="1" style="width: 75%;">
        <tr>
          <th>Equipment Image</th>
          <th>Username</th>
          <th>Equipment Name</th>
          <th>Belong Campus Name</th>
          <th>Destination Campus Name</th>
          <th>Quantity</th>
          <th>Delivery Username</th>
          <th>Status</th>
          <th>Estimated Time</th>
          <th colspan="<%= (role.equalsIgnoreCase("Technician") || role.equalsIgnoreCase("admin"))? "2" : "1" %>">Action</th>
        </tr>
        <% 
          if (reserves.listIsEmpty()) {
        %>
          <tr>
            <td colspan="10">No delivery available</td>
          </tr>
        <%
          } else {
            List<WishEquipment> wishEquipmentList = reserves.getList();
            for (WishEquipment wishEquipment : wishEquipmentList) {
        %>
          <tr>
            <td width="10%" height="10%"><img width="100%" height="100%" src="<%= (wishEquipment.getEquipmentImage() != null) ? "img/" + wishEquipment.getEquipmentImage() : "http://travelmen.org/static/images/404.png" %>" /></td>
            <td><%= wishEquipment.getUsername() %></td>
            <td><%= wishEquipment.getEquipmentName() %></td>
            <td><%= wishEquipment.getBelongCampusName() %></td>
            <td><%= wishEquipment.getDestinationCampusName() %></td>
            <td><%= wishEquipment.getQuantity() %></td>
            <% if (wishEquipment.getDeliveryUsername() != null) { %><td><%= wishEquipment.getDeliveryUsername() %></td><% } else if (!"pending".equalsIgnoreCase(request.getParameter("action"))) { %>
              <td>
  
              </td>
            <% } %>
            <td><%= wishEquipment.getStatus() %></td>
            <td><%= wishEquipment.getDate() %></td>
            <td>
              <% if ("approved".equalsIgnoreCase(status)) { %> 
                  <a href="<%= request.getServletContext().getContextPath() %>/Reserve?action=Delivery&id=<%= wishEquipment.getId() %>">Delivery</a>
                </td >
              <% } %>
          </tr>
        <% } } %>
      </table>
</body>
</html>