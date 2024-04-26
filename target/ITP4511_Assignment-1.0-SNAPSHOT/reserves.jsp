<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Reserves List</title>
    <link rel="stylesheet" href="css/table.css" />
    <script>
      function Reserve(action) {
        window.location.href = "<%= request.getServletContext().getContextPath() %>/Reserve?action=" + action;
      }
    </script>
  </head>
  <body>
    <%@ include file="header.jsp" %>
    <%@ page import="java.util.List, ict.bean.WishEquipment" %>
    <jsp:useBean id="reserves" class="ict.bean.WishList" scope="request" />
    <div class="reserve" style="margin-top: 20px;">
      <div>
        Status: 
        <select name="action" onchange="Reserve(this.value)">
          <option value="pending" <%= ("pending".equalsIgnoreCase(request.getParameter("action"))) ? "selected" : "" %>>Reserve</option>
          <option value="approval" <%= ("approval".equalsIgnoreCase(request.getParameter("action"))) ? "selected" : "" %>>Approved</option>
          <option value="list" <%= ("list".equalsIgnoreCase(request.getParameter("action"))) ? "selected" : "" %>>Borrow</option>
        </select>
      </div>
      <% if (role.equalsIgnoreCase("admin") || role.equalsIgnoreCase("Technician")) { %>
        <div>
          Set up courier delivery <a href="<%= request.getServletContext().getContextPath() %>/Reserve?action=setDelivery"><button>Set up</button></a>
        </div>
      <% } %>
    </div>

    <table border="1" style="width: 85%;">
      <tr>
        <th>Equipment Image</th>
        <th>Username</th>
        <th>Equipment Name</th>
        <th>Belong Campus Name</th>
        <th>Destination Campus Name</th>
        <th>Quantity</th>
        <% if (!"pending".equalsIgnoreCase(request.getParameter("action"))) { %><th>Delivery Username</th> <% } %>
        <th>Status</th>
        <th>Estimated Time</th>
        <th colspan="<%= (role.equalsIgnoreCase("Technician") || role.equalsIgnoreCase("admin"))? "2" : "1" %>">Action</th>
      </tr>
      <% 
        if (reserves.listIsEmpty()) {
      %>
        <tr>
          <td colspan="10">No records found</td>
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
            <% if (role != null) { %> 
              <% if (role.equalsIgnoreCase("Technician") || role.equalsIgnoreCase("admin")) { %>
                <a href="<%= request.getServletContext().getContextPath() %>/Reserve?action=form&id=<%= wishEquipment.getId() %>">Update</a>
              </td >
              <td>
              <% } %>
                <a href="<%= request.getServletContext().getContextPath() %>/Reserve?action=cancel&id=<%= wishEquipment.getId() %>">cancel</a>
            <% } %>
          </td>
        </tr>
      <% } } %>
    </table>
  </body>
</html>
