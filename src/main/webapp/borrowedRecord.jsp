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
<script>
function setAction(form) {
    var action = form.querySelector('button[type="submit"]:focus').value;
    form.action = "CheckOutController?action=" + action;
}
</script>

<body>
<%@ include file="header.jsp" %>
<%
    CheckOutDB checkOutDB = new CheckOutDB("jdbc:mysql://localhost:3306/ITP4511_Assignment_DB?useSSL=false", "root", ""); 
    List<CheckOut> checkOuts = checkOutDB.getAllUnconfirmedCheckOuts();
%>
<div class="container my-4">
    
    <% if (checkOuts != null && !checkOuts.isEmpty()) {
        %><h4>Unconfirmed Check-Out Records</h4>
        <%
        System.out.println("Number of checkouts fetched: " + checkOuts.size());
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
                    <form action="" method="post" onsubmit="setAction(this)">
                        <input type="hidden" name="checkoutId" value="<%= checkOut.getCheckOutId() %>" />
                        <button type="submit" name="action" value="confirm" class="btn btn-primary">Confirm</button>
                        <button type="submit" name="action" value="delete" class="btn btn-warning">Delete</button>
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
        <p>No unconfirmed check-out records found.</p>
    <% } %>
</div>
</body>
</html>
