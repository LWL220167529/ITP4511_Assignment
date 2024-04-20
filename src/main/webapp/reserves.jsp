<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Reserves List</title>
    <link rel="stylesheet" href="css/table.css" />
  </head>
  <body>
    <%@ include file="header.jsp" %>
    <%@ page import="java.util.List, ict.bean.WishEquipment" %>
    <jsp:useBean id="reserves" class="ict.bean.WishList" scope="request" />

    <table border="1">
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
        if (reserves == null) {
            response.sendRedirect(
                    request.getServletContext().getContextPath() + "/Equipment?action=getCampus&campus=" + campus);
          return;
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
                <a href="<%= request.getServletContext().getContextPath() %>/Reserve?action=updateStatus&id=<%= wishEquipment.getId() %>">Remove</a>
            <% } %>
          </td>
        </tr>
      <% } } %>
    </table>
  </body>
</html>
