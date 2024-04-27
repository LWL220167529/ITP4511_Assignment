<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Statisic</title>
    <script>
      function searchEquipment() {
        var campusElement = document.getElementById("campus");
        var date1Element = document.getElementById("startDate");
        var date2Element = document.getElementById("endDate");

        var campus = campusElement ? campusElement.value : "";
        var startDate = date1Element ? date1Element.value : "";
        var endDate = date2Element ? date2Element.value : "";

        if (startDate && endDate && startDate > endDate) {
          alert("End date must be greater than start date");
          return;
        }

        if (!startDate || !endDate) {
          alert("Please select start date and end date");
          return;
        }

        var form = document.createElement("form");

        form.action = "<%= request.getServletContext().getContextPath() %>/Reserve?action=statistic";
        form.method = "get";
        form.hidden = true;

        form.innerHTML +=
          "<input type='hidden' name='action' id='campus' value='statistic' />";

        form.innerHTML +=
          "<input type='hidden' name='campus' id='campus' value='" +
          campus +
          "' required/>";
        form.innerHTML +=
          "<input type='hidden' name='startDate' id='startDate' value='" +
          startDate +
          "' required/>";

        form.innerHTML +=
          "<input type='hidden' name='endDate' id='endDate' value='" +
          endDate +
          "' required/>";
        document.body.appendChild(form);
        form.submit();

      }
    </script>
  </head>
  <body>
    <%@ include file="header.jsp" %>
    <%@ page import="java.util.List, ict.bean.CheckoutStatistic;" %>
    <% String cocampus = request.getParameter("campus") == null ? campus : request.getParameter("campus"); %>
    <div class="container mt-3">
      <div class="contentbar">
        <!-- Start row -->
        <div class="row">
          <!-- Start col -->
          <div class="col-md-12 col-lg-12 col-xl-12">
            <div class="card m-b-30">
              <div class="card-header">
                <h5 class="card-title">Check-out statistic</h5>
              </div>
              <div class="card-body">
                <div class="row justify-content-center">
                  <div class="col-lg-10 col-xl-8">
                    <div class="cart-container">
                      <div class="cart-head">
                          <div class="cart-body">
                            <div class="row d-flex justify-content-between">
                              <div
                                class="col-md-12 order-2 order-lg-1 col-lg-5 col-xl-6"
                              >
                                Campus:
                                <select
                                  name="campus"
                                  id="campus" required
                                >
                                  <option value="CW" <%= cocampus.equalsIgnoreCase("CW") ? "selected" : "" %>>Chai Wan</option>
                                  <option value="LWL" <%= cocampus.equalsIgnoreCase("LWL") ? "selected" : "" %>>Lee Wai Lee</option>
                                  <option value="ST" <%= cocampus.equalsIgnoreCase("ST") ? "selected" : "" %>>Sha Tin</option>
                                  <option value="TM" <%= cocampus.equalsIgnoreCase("TM") ? "selected" : "" %>>Tuen Mun</option>
                                  <option value="TY" <%= cocampus.equalsIgnoreCase("TY") ? "selected" : "" %>>Tsing Yi</option>
                                </select>
                              </div>
                              <div
                                class="col-md-12 order-1 order-lg-2 col-lg-7 col-xl-6"
                                style="text-align: right"
                              >
                                Date:
                                <input type="date" name="startDate" id="startDate" value="<%= request.getParameter("startDate") == null ? "" : request.getParameter("startDate")%>" required/> -
                                <input type="date" name="endDate" id="endDate" value="<%= request.getParameter("endDate") == null ? "" : request.getParameter("endDate")%>" required/>
                              </div>
                            </div>
                            <div class="row mt-3">
                              <div class="input" style="text-align: right;">
                                <input
                                  type="button"
                                  id="search"
                                  value="Search"
                                  onclick="searchEquipment()"
                                />
                              </div>
                            </div>
                          </div>
                        <div class="table-responsive">
                          <table class="table table-borderless">
                            <thead>
                              <tr>
                                <th scope="col">Name</th>
                                <th scope="col">Image</th>
                                <th scope="col">Time</th>
                              </tr>
                            </thead>
                            <tbody>
                              <%
                              if (request.getAttribute("CheckoutStatistics") != null) {
                                List<CheckoutStatistic> checkoutStatistics = (List<CheckoutStatistic>) request.getAttribute("CheckoutStatistics");
                                if (checkoutStatistics != null) {
                                for (CheckoutStatistic checkoutStatistic : checkoutStatistics) {
                              %>
                              <tr>
                                <td><%= checkoutStatistic.getName() %></td>
                                <td>
                                  <img
                                    src="<%= (checkoutStatistic.getImage() != null) ? "img/" + checkoutStatistic.getImage() : "http://travelmen.org/static/images/404.png" %>"
                                    class="img-fluid"
                                    width="35"
                                    alt="product"
                                  />
                                </td>
                                <td>
                                  <%= checkoutStatistic.getCount() %>
                                </td>
                              </tr>
                              <%
                              } } } else {
                              %>
                              <tr>
                                <td colspan="3">No data</td>
                              </tr>
                              <%
                              }
                              %>
                            </tbody>
                          </table>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <!-- End col -->
        </div>
        <!-- End row -->
      </div>
    </div>
  </body>
</html>
