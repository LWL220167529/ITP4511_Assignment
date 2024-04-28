<!DOCTYPE html>
<%@ page import="java.util.List, ict.bean.CampusEquipment" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <script src="js/home.js"></script>
    <link rel="stylesheet" type="text/css" href="css/table.css" />
    <link rel="stylesheet" type="text/css" href="css/equipment.css" />
    <script>
        function searchEquipment() {
            var input, filter, cards, cardContainer, title, i;
            input = document.getElementById("search");
            filter = input.value.toUpperCase();
            cardContainer = document.getElementsByClassName("equipment")[0];
            cards = cardContainer.getElementsByClassName("card");
            for (i = 0; i < cards.length; i++) {
                title = cards[i].querySelector(".card-title");
                if (title.innerText.toUpperCase().indexOf(filter) > -1) {
                    cards[i].style.display = "";
                } else {
                    cards[i].style.display = "none";
                }
            }
        }
    </script>
</head>
<body>
        
    <%@ include file="header.jsp" %>
    <h1>Campus Equipments</h1>
    <jsp:useBean id="equipments" class="ict.bean.CampusEquipments" scope="request" />
    <% 
        if (equipments != null && !equipments.listIsEmpty()) {
        List<CampusEquipment> campusEquipments = equipments.getCampusEquipments(); 
    %>
    <div class="campus" style="display: flex; justify-content: space-between;">
        <div>
            Campus: 
            <select name="campus" onchange="changeCampus(this.value)">
                <option value="CW" <%= equipments.getCampus().equalsIgnoreCase("CW") ? "selected" : "" %>>Chai Wan</option>
                <option value="LWL" <%= equipments.getCampus().equalsIgnoreCase("LWL") ? "selected" : "" %>>Lee Wai Lee</option>
                <option value="ST" <%= equipments.getCampus().equalsIgnoreCase("ST") ? "selected" : "" %>>Sha Tin</option>
                <option value="TM" <%= equipments.getCampus().equalsIgnoreCase("TM") ? "selected" : "" %>>Tuen Mun</option>
                <option value="TY" <%= equipments.getCampus().equalsIgnoreCase("TY") ? "selected" : "" %>>Tsing Yi</option>
            </select>
        </div>
        <div>
            Search:
            <input type="text" id="search" placeholder="Search for equipment" onkeyup="searchEquipment()">
        </div>
    </div>
    <div class="container equipment">
        <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
            <% 
                for (CampusEquipment campusEquipment : campusEquipments) { 
            %>
                <div class="col">
                    <div class="card">
                        <div class="card-body">
                            <picture>
                                <img class="img-fluid rounded" src="<%= (campusEquipment.getEquipmentImage() != null) ? "img/" + campusEquipment.getEquipmentImage() : "http://travelmen.org/static/images/404.png" %>" 
                                style="width: 600px; height: 337px; object-fit: cover;" alt="<%= campusEquipment.getEquipmentName() %>" />
                            </picture>
                            <h5 class="card-title center text-center"><%= campusEquipment.getEquipmentName() %></h5>
                            <p>campus: <%= campusEquipment.getCampus() %></p>
                            <div class="d-flex justify-content-between align-items-center">
                                <div>Status: <%= campusEquipment.getStatus() %></div>
                                <div>Quantity: <%= campusEquipment.getQuantity() %></div>
                            </div>
                            <div class="text-end mt-3">
                                <% if (!campusEquipment.getCampus().equalsIgnoreCase(campus)) { %>
                                <a href="<%= request.getServletContext().getContextPath() %>/Equipment?action=wish&id=<%= campusEquipment.getId() %>"><button class="btn btn-primary">Add to wish list</button></a>
                                <% } else { %>
                                <button class="btn btn-primary">Check-out</button>
                                <% } %>
                            </div>
                        </div>
                    </div>
                </div>
            <% }
        } else {
            response.sendRedirect(request.getServletContext().getContextPath());
        } %>
        </div>
    </div>
</body>
</html>