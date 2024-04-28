<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, ict.db.CheckInDB, ict.bean.CheckIn"%>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Damage Report</title>
    <!-- Include your CSS and Bootstrap links here -->
</head>
<body>
<%@ include file="header.jsp" %>
<%
    String checkInId = request.getParameter("checkInId");
    CheckInDB checkInDB = new CheckInDB("jdbc:mysql://localhost:3306/ITP4511_Assignment_DB", "root", ""); 
    CheckIn checkIn = checkInDB.getCheckInById(Integer.parseInt(checkInId));
    String existingDamageReport = checkIn != null ? checkIn.getDamageReport() : "";
%>
<div class="container">
    <h2>Edit Damage Report for Check-In ID: <%= checkInId %></h2>
    <form action="CheckInController" method="post">
        <input type="hidden" name="checkInId" value="<%= checkInId %>" />
        <div class="mb-3">
            <label for="damageDescription" class="form-label">Damage Description:</label>
            <textarea class="form-control" id="damageDescription" name="damageReport" required><%= existingDamageReport %></textarea>
        </div>
        <button type="submit" name="action" value="editDamage" class="btn btn-primary">Edit Damage Report</button>
    </form>
</div>
</body>
</html>
