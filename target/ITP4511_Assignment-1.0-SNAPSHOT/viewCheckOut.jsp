<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>View Check Out </title>
    <%@page import="ict.bean.CheckOut"%>
    <!-- Include your CSS files here -->
</head>
<body>
<div class="py-2">
    <div class="container">
        <div class="row">
            <div class="col-md-12">

                <h4><%=title%></h4>

                <% for (CheckOut checkout : db.listAllReservationRequest()) {%>


                <div class="card">
                    <div class="card-body">
                        <h5>#R<%=checkout.getId()%></h5>
                        <a href="${pageContext.request.contextPath}/reservation/view?id=<%=checkout.getId()%>">View</a> | <a href="#">Edit</a>

                        <div class="row">
                            <div class="col-12 col-sm-6 col-md-4">
                                Username
                            </div>
                            <div class="col-12 col-sm-6 col-md-8">
                                <%=checkout.getTypeVerbose()%>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-12 col-sm-6 col-md-4">
                                Status
                            </div>
                            <div class="col-12 col-sm-6 col-md-8">
                                <%=checkout.getTypeVerbose()%>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-12 col-sm-6 col-md-4">
                                Item
                            </div>
                            <div class="col-12 col-sm-6 col-md-8">
                                <%
                                    String msg = "";
                                    for (checkout e : db.getEquipmentsByReservation(checkout.getId())) {
                                        msg += String.format("%s (Status: %s)<br>", e.getname(), e.getStatusVerbose());
                                    }
                                    out.println(msg);
                                %>
                            </div>
                        </div>
                    </div>
                </div>

                <% }%>

            </div>
        </div>
    </div>

</body>
</html>
