<!DOCTYPE html>
<html lang="en">
  <head>
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
      rel="stylesheet"
      integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
      crossorigin="anonymous"
    />
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Login</title>
    <style></style>
  </head>
  <body style="background-color: #66b0ff">
    <%@ page import="ict.bean.User" %> <% if (request.getAttribute("error") !=
    null) { %>
    <p style="color: red"><%= request.getAttribute("error") %></p>
    <% } %> <% if (session.getAttribute("user") != null) { User user = (User)
    session.getAttribute("user"); if (user.getRole().equals("courier")) {
    response.sendRedirect("Reserve?action=delivery"); } else {
    response.sendRedirect("Equipment?action=getCampus&campus=" +
    user.getCampus()); } } String message = (String)
    session.getAttribute("error"); if (message != null) { %>
    <script>
      alert("<%=message%>");
    </script>
    <% session.removeAttribute("error"); } %>

    <!----------------------- Main Container -------------------------->
    <div
      class="container d-flex justify-content-center align-items-center min-vh-100"
    >
      <!----------------------- Login Container -------------------------->
      <div class="row border rounded-5 p-3 bg-white shadow box-area">
        <!--------------------------- Left Box ----------------------------->
        <div
          class="col-md-6 rounded-4 d-flex justify-content-center align-items-center flex-column left-box"
          style="background: #103cbe"
        >
          <div class="featured-image mb-3">
            <img
              src="img/peak-logo.png"
              class="img-fluid"
              style="width: 250px"
            />
          </div>
        </div>
        <!-------------------- ------ Right Box ---------------------------->

        <div class="col-md-6 right-box">
          <div class="row align-items-center">
            <div class="header-text mb-4">
              <h2>Welcome to Hong Kong Institute of Professional Education</h2>
              <p>We are happy to have you back.</p>
            </div>
            <form action="Login" class="" method="post">
              <input type="hidden" name="action" value="authenticate" />
              <div class="input-group mb-3">
                <input
                  type="text"
                  id="username"
                  name="username"
                  class="form-control form-control-lg bg-light fs-6"
                  placeholder="name / email / phone"
                  required
                />
              </div>
              <div class="input-group mb-1">
                <input
                  type="password"
                  name="password"
                  class="form-control form-control-lg bg-light fs-6"
                  placeholder="Password"
                  required
                />
              </div>
              <div class="input-group mb-5 d-flex justify-content-between">
                <div class="form-check">
                  <input
                    type="checkbox"
                    class="form-check-input"
                    id="formCheck"
                    name="rememberMe"
                  />
                  <label for="formCheck" class="form-check-label text-secondary"
                    ><small>Remember Me</small></label
                  >
                </div>
                <div class="forgot">
                  <small><a href="#">Forgot Password?</a></small>
                </div>
              </div>
              <div class="input-group mb-3">
                <input
                  type="submit"
                  class="btn btn-lg btn-primary w-100 fs-6"
                  value="Login"
                />
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </body>
</html>
