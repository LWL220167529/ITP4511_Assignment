<!DOCTYPE html>
<html>
<head>
    <title>View Check Out</title>
    <link rel="stylesheet" href="css/checkout.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body>
<div class="container my-4">
    <h4>View check-out records</h4>
    <%-- Loop through your checkouts --%>
    <% for (int i = 1; i <= 3; i++) { %>
    <div class="card mb-3">
            <div class="row g-0 align-items-center">
              <div class="col-md-8">
                <div class="card-body">
                    <h5 class="card-title">#<%= i %></h5>
                    <p class="card-text"><b>Username:</b> Peter</p>
                    <p class="card-text"><b>Check-out Date:</b> 2024-4-22</p>
                    <p class="card-text"><b>Item:</b> Laptop<%= i %></p>
                    <p class="card-text"><b>Campus:</b> LWL<%= i %></p>
                    <button type="button" class="btn btn-primary">Confirm</button>
                    <button type="button" class="btn btn-warning">Delete</button>
                  </div>
              </div>
            <div class="col-md-4 d-flex justify-content-center">
                <img src="img/laptops.jpg" class="img-fluid rounded-start" alt="Laptop">
            </div>
        </div>
    </div>
    <% } %>
</div>
</body>
</html>
