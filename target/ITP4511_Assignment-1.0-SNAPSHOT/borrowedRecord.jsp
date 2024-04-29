<!DOCTYPE html>
<html>
<head>
    <title>View Borrowed Record</title>
    <link rel="stylesheet" href="css/checkout.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <%@ page import="ict.db.CheckOutDB" %>
    <%@ page import="ict.bean.CheckOut" %>
    <%@ page import="java.util.List" %>
</head>


<body>
<%@ include file="header.jsp" %>
<%
String dbUrl = request.getServletContext().getInitParameter("dbUrl");
String dbUser = request.getServletContext().getInitParameter("dbUser");
String dbPassword = request.getServletContext().getInitParameter("dbPassword");
    CheckOutDB checkOutDB = new CheckOutDB(dbUrl, dbUser, dbPassword); 
    List<CheckOut> checkOuts = checkOutDB.getConfirmedCheckOutsForUser(headerUser.getId());
    
%>
<div class="container my-4">
    
    <% if (checkOuts != null && !checkOuts.isEmpty()) {
        %><h4>Borrow Records</h4>
        <%
       for (CheckOut checkOut : checkOuts) { %>
    <div class="card mb-3">
        <div class="row g-0 align-items-center">
            <div class="col-md-8">
                <div class="card-body">
                    <h5 class="card-title">#<%= checkOut.getCheckOutId() %></h5>
                    <p class="card-text"><b>Username:</b> <%= checkOut.getUserName() %></p>
                    <p class="card-text"><b>Check-out Date:</b> <%= checkOut.getCheckOutDate() %></p>
                    <p class="card-text"><b>Item:</b> <%= checkOut.getEquipmentName() %></p>
                    <p class="card-text"><b>Campus:</b> <%= checkOut.getCampusName() %></p>
                    <p class="card-text"><b>Quantity:</b> <%= checkOut.getQuantity() %></p>
                    <form action="CheckInController" method="post" onsubmit="setAction(this)">
                        <input type="hidden" name="action" value="return" />
                        <input type="hidden" name="checkOutId" value="<%= checkOut.getCheckOutId() %>" />
                        <input type="hidden" name="userId" value="<%= checkOut.getUserId() %>" />
                        <input type="hidden" name="userName" value="<%= checkOut.getUserName() %>" />
                        <input type="hidden" name="equipmentName" value="<%= checkOut.getEquipmentName() %>" />
                        <input type="hidden" name="quantity" value="<%= checkOut.getQuantity() %>" />
                        <input type="hidden" name="campusName" value="<%= checkOut.getCampusName() %>" />
                        <input type="hidden" name="image" value="<%= checkOut.getImage() %>" />
                        <button type="submit" class="btn btn-primary">Return Item</button>
                    </form>



                </div>
            </div>
            <div class="col-md-4 d-flex justify-content-center">
                <img src="<%= checkOut.getImage() %>" class="img-fluid rounded-start" alt="Item Image">
            </div>
        </div>
    </div>
    <%  }
    } else { %>
        <p>No borrow records found.</p>
    <% } %>
</div>
</body>
</html>
