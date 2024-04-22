<%@ page import="ict.bean.User" %>
  <link rel="stylesheet" type="text/css" href="css/header.css" />
  <% User headerUser = (User) session.getAttribute("user"); if (headerUser ==
  null) { response.sendRedirect("Login"); return; }  String message =
  (String) session.getAttribute("message"); if (message != null) { %>
  <script>
    alert("<%=message%>");
  </script>
  <% session.removeAttribute("message"); } %>
  <nav>
    <% 
    String role = "";
    String campus = "";
    if (headerUser != null) { role = headerUser.getRole(); campus = headerUser.getCampus(); } %>
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
      <a href="<%= request.getServletContext().getContextPath() %>/damage.jsp"
        >Return-equipment</a
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
    <% if (role.equalsIgnoreCase("courier")) { %>
    <div class="page">
      <a href="<%= request.getServletContext().getContextPath() %>/delivery.jsp"
        >Delivery</a
      >
    </div>
    <% } %>
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
      <a
        >Welcome <%= (headerUser != null) ? headerUser.getUsername() : "" %> (
        <%= (headerUser != null) ? role : "" %> )</a
      >
    </div>
  </nav>
