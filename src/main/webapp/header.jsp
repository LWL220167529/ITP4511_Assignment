<%@ page import="ict.bean.User" %>
<html>
  <% User headerUser = (User) session.getAttribute("user"); 
  if (headerUser == null) { 
    response.sendRedirect("LoginController");
    return;
  }  
  String message = (String) session.getAttribute("message"); 
    if (message != null) { %>
    <script>alert("<%=message%>");</script>
  <% 
      session.removeAttribute("message");
    } 
  %>
<link rel="stylesheet" type="text/css" href="css/header.css" />
<nav>
  <div class="page">
    <a href="<%= request.getServletContext().getContextPath() %>/home.jsp">Home</a>
  </div>
  <% if (headerUser.getRole().equals("admin")) { %>
    <div class="page">
      <a href="<%= request.getServletContext().getContextPath() %>/user.jsp">User</a>
    </div>
  <% } %>
  <div class="logout">
    <a href="<%= request.getServletContext().getContextPath() %>/LoginController">Logout</a>
  </div>
  <div class="user">
    <a>Welcome <%= (headerUser != null) ? headerUser.getUsername() : "" %></a>
  </div>
</nav>
</html>