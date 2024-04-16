<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reserve Form</title>
    <link rel="stylesheet" href="css/form.css">
    <script>
        function ChangeDateCampus() {
            var form = document.createElement("form");
            var date = document.getElementById("date").value;
            var campus = document.getElementById("campus").value;
            form.action = "/ITP4511_Assignment/Reserve?action=setDelivery";
            form.method = "POST";
            form.hidden = true;
            form.innerHTML += "<input type='hidden' name='date' id='date' value='" + date + "' />";
            form.innerHTML += "<input type='hidden' name='campus' id='campus' value='" + campus + "' />";
            document.body.appendChild(form);
            form.submit();
        }
    </script>
</head>
<body>
    <%@ include file="header.jsp" %>
    <%@ page import="ict.bean.User, java.util.List, ict.bean.WishEquipment, java.util.Date" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <jsp:useBean id="setDelivery" class="ict.bean.WishList" scope="session" />
    <jsp:useBean id="users" class="ict.bean.Users" scope="request" />
    <jsp:useBean id="dates" class="java.util.List" scope="request" />
    <% String selectedDate = request.getParameter("date"); %>
    <div style="
    display: flex;
    width: 75%;
    justify-content: space-between;
    margin: auto;
    margin-top: 20px;">
        <div>
            Date: <select name="date" id="date" onchange="ChangeDateCampus()">
                <% for (Object date : dates) { %>
                    <option value="<%=date%>" <%=date.toString().equals(selectedDate) ? "selected" : ""%> ><%=date%></option>
                <% } %>
            </select>
        </div>
        <div>
            Campus: 
            <select name="campus" id="campus" onchange="ChangeDateCampus()">
                <option value="CW" <%= "CW".equalsIgnoreCase(request.getAttribute("campus").toString()) ? "selected" : "" %>>Chai Wan</option>
                <option value="LWL" <%= "LWL".equalsIgnoreCase(request.getAttribute("campus").toString()) ? "selected" : "" %>>Lee Wai Lee</option>
                <option value="ST" <%= "ST".equalsIgnoreCase(request.getAttribute("campus").toString()) ? "selected" : "" %>>Sha Tin</option>
                <option value="TM" <%= "TM".equalsIgnoreCase(request.getAttribute("campus").toString()) ? "selected" : "" %>>Tuen Mun</option>
                <option value="TY" <%= "TY".equalsIgnoreCase(request.getAttribute("campus").toString()) ? "selected" : "" %>>Tsing Yi</option>
            </select>
        </div>
    </div>
    <table border="1" style="
    margin: auto;
    margin-top: 1%;
    margin-bottom: 1%;
    width: 75%;">
        <tr>
          <th>Equipment Image</th>
          <th>Username</th>
          <th>Equipment Name</th>
          <th>Belong Campus Name</th>
          <th>Destination Campus Name</th>
          <th>Quantity</th>
          <th>Status</th>
          <th>Estimated Time</th>
        </tr>
        <% 
          if (setDelivery == null) {
              response.sendRedirect(
                      request.getServletContext().getContextPath() + "/Equipment?action=getCampus&campus=" + campus);
            return;
          } else {
            List<WishEquipment> wishEquipmentList = setDelivery.getList();
            for (WishEquipment wishEquipment : wishEquipmentList) {
        %>
          <tr>
            <td width="10%" height="10%"><img width="100%" height="100%" src="<%= (wishEquipment.getEquipmentImage() != null) ? "img/" + wishEquipment.getEquipmentImage() : "http://travelmen.org/static/images/404.png" %>" /></td>
            <td><%= wishEquipment.getUsername() %></td>
            <td><%= wishEquipment.getEquipmentName() %></td>
            <td><%= wishEquipment.getBelongCampusName() %></td>
            <td><%= wishEquipment.getDestinationCampusName() %></td>
            <td><%= wishEquipment.getQuantity() %></td>
            <td><%= wishEquipment.getStatus() %></td>
            <td><%= wishEquipment.getDate() %></td>
            </tr>
        <% } } %>

    </table>
    <form action="Reserve" method="post" style="width: fit-content; margin: auto; text-align: center;">
        <input type="hidden" name="action" value="updateAllStatus" />
        <div class="container">
            
            Courier:
            <% if (!users.listIsEmpty()) { %>
            <select name="Courier" required>
                <c:forEach var="user" items="${users.getList()}">
                    <option value="${user.getId()}" >${user.getUsername()}</option>
                </c:forEach>
            </select>
            <% } else {%>
                No courier available
            <% } %>
            <hr>
            <center><button type="submit" class="registerbtn">Update</button></center>
        </div>
    </form>
</body>
</html>