<%@ page import="ict.bean.User" %>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
<link rel="stylesheet" type="text/css" href="header.css" />
  <% User headerUser = (User) session.getAttribute("user"); if (headerUser ==
  null) { response.sendRedirect("Login"); return; }  String message =
  (String) session.getAttribute("message"); if (message != null) { %>
  <div class="modal fade" id="myObject" aria-hidden="true" aria-labelledby="exampleModalToggleLabel" tabindex="-1">
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
        <div class="modal-header">
          <h1 class="modal-title fs-5" id="exampleModalToggleLabel">Message</h1>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <%=message%>
        </div>
      </div>
    </div>
  </div>
  <script>
      var myModal = new bootstrap.Modal(document.getElementById('myObject'));
      myModal.show();
  </script>
  <% session.removeAttribute("message"); } %>
  <style>
    th {
      text-align: center;
    }
  </style>
  <nav>
    <% 
    String role = "";
    String campus = "";
    int id = 0;
    if (headerUser != null) { role = headerUser.getRole(); campus = headerUser.getCampus(); id = headerUser.getId();} %>
    <div class="page" style="margin-left: 5%">
      <a
        href="<%= request.getServletContext().getContextPath() %>"
        >Home</a
      >
    </div>
    <% if (role.equalsIgnoreCase("user") || role.equalsIgnoreCase("staff") || role.equalsIgnoreCase("technician") || role.equalsIgnoreCase("admin")) { %>
    <div class="page">
      <a href="<%= request.getServletContext().getContextPath() %>/wishList.jsp"
        >Wish List</a
      >
    </div>
    <div class="page">
      <a href="<%= request.getServletContext().getContextPath() %>/damage.jsp"
        >Pick-up</a
      >
    </div>
    <div class="page">
      <a href="<%= request.getServletContext().getContextPath() %>/Reserve?action=list"
        >Records</a
      >
    </div>
    <div class="page">
      <a href="<%= request.getServletContext().getContextPath() %>/damage.jsp"
        >Check-out</a
      >
    </div>
    <div class="page">
      <a href="<%= request.getServletContext().getContextPath() %>/checkIn.jsp"
        >Check-In</a
      >
    </div>
    <% if (role.equalsIgnoreCase("staff")) { %>
    <div class="page">
      <a href="<%= request.getServletContext().getContextPath() %>/damage.jsp"
        >Borrow-items-exclusive</a
      >
    </div>
    <div class="page">
      <a href="<%= request.getServletContext().getContextPath() %>/Reserve?action=allList"
        >Reserves</a
      >
    </div>
    <% } %> <% } %> 
    <% if (role.equalsIgnoreCase("admin") ||
    role.equalsIgnoreCase("technician")) { %>
    <div class="page">
      <a href="<%= request.getServletContext().getContextPath() %>/damage.jsp"
        >Damage</a
      >
    </div>
    </div>
    <div class="page">
      <a href="<%= request.getServletContext().getContextPath() %>/Reserve?action=allList"
        >Reserves</a
      >
    </div>
    <% if (role.equalsIgnoreCase("admin")) { %>
    <div class="page">
      <a href="<%= request.getServletContext().getContextPath() %>/user.jsp"
        >User</a
      >
    </div>
    <div class="page">
      <a href="<%= request.getServletContext().getContextPath() %>/Reserve?action=statistic"
        >Statistic</a
      >
    </div> 
    <% } %> <% } %>
    <div class="logout" style="margin-right: 5%">
      <a
        href="<%= request.getServletContext().getContextPath() %>/Login"
        >Logout</a
      >
    </div>
    <div class="user">
      <a>Campus : <%= campus %> </a>
    </div>
    <div class="user">
      <a href="<%= request.getServletContext().getContextPath() %>/User?action=edit&id=<%= id %>"
        >Welcome <%= (headerUser != null) ? headerUser.getUsername() : "" %> (
        <%= (headerUser != null) ? role : "" %> )</a
      >
    </div>
  </nav>
