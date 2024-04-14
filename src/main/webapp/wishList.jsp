<!DOCTYPE html>
<html lang="en">
    <%@ page import="java.util.List, ict.bean.WishEquipment, ict.bean.UserReserve" %>
<head>
    <jsp:useBean id="reserves" class="ict.bean.UserReserves" scope="application" />
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Wish List</title>
    <script src="js/wishList.js"></script>
    <link rel="stylesheet" type="text/css" href="css/table.css" />
</head>
<body>
    <%@ include file="header.jsp" %>
    <%
        if (request.getAttribute("equipment") != null) {
            UserReserve campusEquipment = (UserReserve) request.getAttribute("equipment");
            
            if (!"available".equalsIgnoreCase(campusEquipment.getCampusEquipmentStatus())) {
                session.setAttribute("message", campusEquipment.setCampusEquipmentName() + " (" + campusEquipment.getBelongCampusId() + ") is not available");

                response.sendRedirect(
                        request.getServletContext().getContextPath() + "/Equipment?action=getCampus&campus=" + campus);
                return;
            }
            if (reserves.addUserReserve(campusEquipment)) {
            %>
    
            <script>
                alert("Added <%= campusEquipment.setCampusEquipmentName() %> (<%= campusEquipment.getBelongCampusId() %>) to wish list successfully");
            </script>
            
            <% } else { %>
    
                <script>
                    alert("<%= campusEquipment.setCampusEquipmentName() %> (<%= campusEquipment.getBelongCampusId() %>) is exist in wish list");
                </script>
            <%
            }

        } else if (request.getParameter("remove") != null) {
            try {
                String Name = reserves.removeUserReserve(Integer.parseInt(request.getParameter("remove")));
    %>
            <script>
                alert("Removed <%= Name %> from wish list successfully");
            </script>
    <%
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect(
                        request.getServletContext().getContextPath() + "/Equipment?action=getCampus&campus=" + campus);
            }
        } else if (request.getParameter("quantity") != null) {
            try {
                reserves.updateUserReserve(Integer.parseInt(request.getParameter("id")), Integer.parseInt(request.getParameter("quantity")));
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect(
                        request.getServletContext().getContextPath() + "/Equipment?action=getCampus&campus=" + campus);
            }
        }
    %>
    <h1>Wish List</h1>
    <form method="post" action="Reserve">
        <div class="reserve">
            <input type="hidden" name="action" value="reserve" />
            <div>Data:<input type="date" name="date" required /></div>
            <div><input type="submit" value="Reserve" /></div>
        </div>
    </form>
    <table width="75%" height="75%" border="1">
        <tr>
            <th>Equipment image</th>
            <th>Equipment name</th>
            <th>Belong Campus</th>
            <th>Destination</th>
            <th>Quantity</th>
            <th>Actions</th>
        </tr>
        <% 
            if (!reserves.isUserReserveEmpty()) {
            List<UserReserve> reservesList = reserves.getList();
            int i = 0;
            for (UserReserve reserve : reservesList) { %>
        <tr>
            <td width="10%" height="10%"><img width="100%" height="100%" src="<%= (reserve.getEquipmentImage() != null)? "img/" + reserve.getEquipmentImage() : "http://travelmen.org/static/images/404.png" %>" /></td>
            <td><%= reserve.setCampusEquipmentName() %></td>
            <td><%= reserve.getBelongCampusId() %></td>
            <td><%= reserve.getDestinationCampusId() %></td>
            <td>
                <input type="number" name="quantity" value="<%= (reserve.getQuantity() != 0)? reserve.getQuantity() : 1 %>" min="1" max="<%= reserve.getCampusEquipmentQuantity() %>" onchange="addQuantity(<%= reserve.getCampusEquipmentId() %>, this.value)" required/>
            </td>
            <th>
                <form action="wishList.jsp" method="post">
                    <input type="hidden" name="remove" value="<%= i %>" />
                    <input type="submit" value="remove" />
                </form>
            </th>
        </tr>
        <% i++; } }  else { %>
        <tr>
            <td colspan="6">No wish equipment</td>
        </tr>
        <% } %>
    </table>
</body>
</html>