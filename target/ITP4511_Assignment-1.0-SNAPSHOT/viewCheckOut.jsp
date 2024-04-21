<!DOCTYPE html>
<html>
<head>
    <title>View Check Out</title>
    <link rel="stylesheet" href="css/checkout.css">
</head>
<body>
<div class="container">
    <h4>View Checkouts</h4>
    <%-- Loop through your checkouts --%>
    <% for (int i = 1; i <= 3; i++) { %>
    <div class="card">
        <div class="card-body">
            <h5>#<%= i %></h5>
            <a href="#">View</a> | <a href="#">Edit</a>
            <div class="row">
                <div class="col-md-4">Username</div>
                <div class="col-md-8">Peter</div>
            </div>
            <div class="row">
                <div class="col-md-4">Check Out Date</div>
                <div class="col-md-8">2024-4-22</div>
            </div>
            <div class="row">
                <div class="col-md-4">Item</div>
                <div class="col-md-8">Item<%= i %></div>
            </div>
            <div class="row">
                <div class="col-md-4">Campus</div>
                <div class="col-md-8">LWL<%= i %></div>
            </div>
        </div>
    </div>
    <% } %>
</div>
</body>
</html>
