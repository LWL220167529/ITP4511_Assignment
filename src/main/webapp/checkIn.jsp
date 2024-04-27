<!DOCTYPE html>
<html>
<head>
    <title>View Check Out</title>
    <link rel="stylesheet" href="css/checkout.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <%@ page import="ict.db.CheckInDB" %>
    <%@ page import="ict.bean.CheckIn" %>
    <%@ page import="java.util.List" %>
</head>
<script>
function setAction(form) {
    var action = form.querySelector('button[type="submit"]:focus').value;
    form.action = "CheckInController?action=" + action;
}
</script>

<body>
<%@ include file="header.jsp" %>
<%
     CheckInDB checkInDB = new CheckInDB("jdbc:mysql://localhost:3306/ITP4511_Assignment_DB", "root", ""); 
     List<CheckIn> checkIns = checkInDB.getAllUnconfirmedCheckIns();
%>
<div class="container my-4">
    <h4>Unconfirmed Check-In Records</h4>
    <% if (checkIns != null && !checkIns.isEmpty()) {
        for (CheckIn checkIn : checkIns) { %>
    <div class="card mb-3">
        <div class="row g-0 align-items-center">
            <div class="col-md-8">
                <div class="card-body">
                    <h5 class="card-title">#<%= checkIn.getCheckInId() %></h5>
                    <p class="card-text"><b>Username:</b> <%= checkIn.getUserName() %></p>
                    <p class="card-text"><b>Check-out Date:</b> <%= checkIn.getCheckInDate() %></p>
                    <p class="card-text"><b>Item:</b> <%= checkIn.getEquipmentName() %></p>
                    <p class="card-text"><b>Campus:</b> <%= checkIn.getCampusName() %></p>
                    <p class="card-text"><b>Quantity:</b> <%= checkIn.getQuantity() %></p>
                     <form action="" method="post" onsubmit="setAction(this)">
                        <input type="hidden" name="checkInId" value="<%= checkIn.getCheckInId() %>" />
                       <button type="submit" name="action" value="confirm" class="btn btn-primary">Confirm</button>
                       <button type="submit" name="action" value="reportDamage" class="btn btn-danger">Add Damage Report</button>
                       <button type="submit" name="action" value="delete" class="btn btn-warning">Delete</button>
                     </form>
                </div>
            </div>
            <div class="col-md-4 d-flex justify-content-center">
                <img src="<%= checkIn.getImage() %>" class="img-fluid rounded-start" alt="Item Image">
            </div>
        </div>
    </div>
    <%  }
    } else { %>
        <p>No unconfirmed check-in records found.</p>
    <% } %>
</div>
</body>
</html>
