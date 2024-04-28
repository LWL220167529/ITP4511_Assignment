<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,ict.db.CheckInDB,ict.bean.CheckIn"%>
<!DOCTYPE html>
<html>
<head>
    <title>Add Damage Report</title>
    <!-- Include your CSS and Bootstrap links here -->
</head>
<body>
<%@ include file="header.jsp" %>
<%
    String checkInId = request.getParameter("checkInId");
    
%>
<div class="container">
    <h2>Add Damage Report for Check-In ID: <%= checkInId %></h2>
    <form action="CheckInController" method="post">
        <input type="hidden" name="checkInId" value="<%= checkInId %>" />
        <div class="mb-3">
            <label for="damageDescription" class="form-label">Damage Description:</label>
            <textarea class="form-control" id="damageDescription" name="damageReport" required></textarea>
        </div>
        <button type="submit" name="action" value="reportDamage" class="btn btn-primary">Submit Report</button>
    </form>
</div>
</body>
</html>
