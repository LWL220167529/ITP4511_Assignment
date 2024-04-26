/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ict.servlet;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ict.bean.UserReserve;
import ict.bean.UserReserves;

import javax.servlet.ServletContext;
import ict.bean.WishList;
import ict.bean.WishEquipment;
import ict.bean.CampusEquipment;
import ict.bean.User;
import ict.bean.Users;
import ict.db.CampusEquipmentDB;
import ict.db.Database;
import ict.db.ReserveDB;
import ict.db.UserDB;

/**
 *
 * @author User
 */
@WebServlet(name = "ReserveController", urlPatterns = { "/Reserve" })
public class ReserveController extends HttpServlet {
    private ReserveDB db;
    private UserDB udb;
    private CampusEquipmentDB cedb;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String message = "";

        HttpSession session = request.getSession();

        User user;

        if (session.getAttribute("user") == null) {
            response.sendRedirect("Login");
            return;
        } else {
            user = (User) session.getAttribute("user");
        }

        if ("wish".equalsIgnoreCase(action)) {

            request.getRequestDispatcher("wishList.jsp").forward(request, response);

        } else if ("list".equalsIgnoreCase(action)) {

            WishList wishList = new WishList();
            try {

                wishList.setWishEquipments(db.getReservesByUserId(user.getId()));

            } catch (Exception e) {
                e.printStackTrace();
            }
            request.setAttribute("reserves", wishList);
            request.getRequestDispatcher("reserves.jsp").forward(request, response);

        } else if ("allList".equalsIgnoreCase(action)) {

            if (user.getRole().equalsIgnoreCase("admin") ||
                    user.getRole().equalsIgnoreCase("technician") || user.getRole().equalsIgnoreCase("staff")) {
                getAllReserves(request, response);
            } else {
                response.sendRedirect(
                        request.getServletContext().getContextPath() + "/Equipment?action=getCampus&campus="
                                + user.getCampus());
            }

        } else if ("setDelivery".equalsIgnoreCase(action)) {

            setDelivery(request, response);

        } else if ("cancel".equalsIgnoreCase(action)) {

            if (user.getRole().equalsIgnoreCase("admin") || user.getRole().equalsIgnoreCase("technician")) {

                try {
                    int id = Integer.parseInt(request.getParameter("id"));
                    db.cancelReserve(id);
                    response.sendRedirect("Reserve?action=list");
                } catch (NumberFormatException e) {
                    // Handle the exception if the parameter "id" cannot be parsed as an integer
                    e.printStackTrace();
                } catch (Exception e) {
                    // Handle other exceptions that may occur during the cancellation process
                    e.printStackTrace();
                }
            } else {
                response.sendRedirect(
                        request.getServletContext().getContextPath());
            }

        } else if ("updateStatus".equalsIgnoreCase(action)) {

            if (user.getRole().equalsIgnoreCase("admin") || user.getRole().equalsIgnoreCase("technician")) {
                updateReserve(request, response);
            } else {
                response.sendRedirect(
                        request.getServletContext().getContextPath());
            }

        } else if ("reserve".equalsIgnoreCase(action)) {

            addReserve(request, response, message, user);

        } else if ("form".equalsIgnoreCase(action)) {
            if (user.getRole().equalsIgnoreCase("admin") || user.getRole().equalsIgnoreCase("technician")) {
                getReserveStatus(request, response);
            } else {
                response.sendRedirect(
                        request.getServletContext().getContextPath() + "/Equipment?action=getCampus&campus="
                                + user.getCampus());
            }
        } else if ("approval".equalsIgnoreCase(action)) {

            getApprovalReserve(request, response, user);

        } else if ("pending".equalsIgnoreCase(action)) {

            getPendingReserve(request, response, user);

        } else if ("updateAllStatus".equalsIgnoreCase(action)) {

            updateReserves(request, response, "Approved");

        } else if ("delivery".equalsIgnoreCase(action)) {

            getCourier(request, response);

        } else if ("deliveryStatus".equalsIgnoreCase(action)) {

            updateDeliveryStatusReserve(request, response);
        } else {
            response.sendRedirect(
                    request.getServletContext().getContextPath());
        }
    }

    private void updateDeliveryStatusReserve(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession(); // Change the cast to HttpSession
            boolean valid = true;
            User user;
            if (session.getAttribute("user") == null) {
                response.sendRedirect("Login");
                return;
            } else {
                user = (User) session.getAttribute("user");
            }

            String status = "";
            String message = "";
            String[] selecteds = request.getParameterValues("selected");
            int[] selectedIds = new int[selecteds.length];

            for (int i = 0; i < selecteds.length; i++) {
                selectedIds[i] = Integer.parseInt(selecteds[i]);
            }

            if (request.getParameter("status") != null) {
                status = request.getParameter("status");
            } else {
                message += "Status is not valid";
                valid = false;
            }
            System.out
                    .println("Status: " + status + " Campus: " + user.getCampus() + " Selected: " + selectedIds.length);
            if (selectedIds != null && selectedIds.length > 0) {

                    if ("Approved".equalsIgnoreCase(status)) {
                        status = "Delivery";
                    } else if ("delivery".equalsIgnoreCase(status)) {
                        status = "Delivered";
                    } else {
                        valid = false;
                        message += "\nStatus is not valid";
                    }
                if (valid) {
                    for (int selected : selectedIds) {
                        if (db.updateDReserveStatus(selected, status)) {
                        } else {
                            CampusEquipment equipment = cedb.getCampusEquipmentById(selected);
                            message += "\n" + equipment.getEquipmentName() + " failed to update";
                        }
                        message += "\nReserve status updated successfully";
                    }
                    sendRedirectAndMessage(request, response, message, "");
                }

            } else {
                message += "No selected items";
                sendRedirectAndMessage(request, response, message, "");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getCourier(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession(); // Change the cast to HttpSession
            User user;
            if (session.getAttribute("user") == null) {
                response.sendRedirect("Login");
                return;
            } else {
                user = (User) session.getAttribute("user");
            }

            WishList wishList = new WishList();

            List<Date> dates = db.getPendingReserveDates();

            String status = request.getParameter("status") == null ? "Approved" : request.getParameter("status");

            if("completed".equalsIgnoreCase(status)){
                status = "Delivered";
            }

            String campus = request.getParameter("campus") == null ? user.getCampus()
                    : request.getParameter("campus");

            wishList.setWishEquipments(db.getReservesByDeliveryUserId(user.getId(), campus, status));

            request.setAttribute("dates", dates);
            request.setAttribute("campus", campus);
            request.setAttribute("reserves", wishList);

            request.getRequestDispatcher("delivery.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setDelivery(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession(); // Change the cast to HttpSession
            User user;
            if (session.getAttribute("user") == null) {
                response.sendRedirect("Login");
                return;
            } else {
                user = (User) session.getAttribute("user");
            }

            WishList wishList = new WishList();

            Users users = udb.getCourier();

            Date date = request.getParameter("date") == null ? new Date(System.currentTimeMillis())
                    : Date.valueOf(request.getParameter("date"));

            List<Date> dates = db.getPendingReserveDates();

            String campus = request.getParameter("campus") == null ? user.getCampus()
                    : request.getParameter("campus");

            wishList.setWishEquipments(
                    db.getReservesByDateAndStatus(date == null ? new Date(System.currentTimeMillis()) : date, campus));

            request.setAttribute("users", users);
            request.setAttribute("dates", dates);
            request.setAttribute("campus", campus);
            session.setAttribute("setDelivery", wishList);

            request.getRequestDispatcher("reserveForm.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateReserves(HttpServletRequest request, HttpServletResponse response, String status) {
        try {
            HttpSession session = request.getSession(); // Change the cast to HttpSession
            WishList wishList = (WishList) session.getAttribute("setDelivery");
            int courierId = Integer.parseInt(request.getParameter("Courier"));
            String message = "";

            for (WishEquipment reserve : wishList.getList()) {
                if (db.updateReserveStatus(reserve.getId(), courierId, status)) {
                    message += reserve.getEquipmentName() + " updated\n";
                } else {
                    message += reserve.getEquipmentName() + " failed\n";
                }
            }
            sendRedirectAndMessage(request, response, message, "/Reserve?action=allList");

        } catch (NumberFormatException e) {
            // Handle the exception when parsing integers
            e.printStackTrace(); // Or log the error message
            return;
        } catch (Exception e) {
            // Handle other exceptions
            e.printStackTrace(); // Or log the error message
            return;
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the
    // + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Adds a reserve for the specified user.
     *
     * @param request  the HttpServletRequest object containing the request
     *                 information
     * @param response the HttpServletResponse object used to send the response
     * @param message  the current message string
     * @param user     the User object representing the user
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    public void addReserve(HttpServletRequest request, HttpServletResponse response, String message, User user)
            throws ServletException, IOException {
        ServletContext context = getServletContext();
        UserReserves reserves = (UserReserves) context.getAttribute("reserves");
        Date date = request.getParameter("date") == null ? new Date(System.currentTimeMillis())
                : Date.valueOf(request.getParameter("date"));

        if (reserves == null) {
            sendRedirectAndMessage(request, response, "Wish list is null",
                    "/Equipment?action=getCampus&campus=" + user.getCampus());
            return;
        }

        for (UserReserve reserve : reserves.getList()) {

            reserve.setDate(date);
            reserve.setUserId(user.getId());
            reserve.setStatus("Pending");

            if (!"available".equalsIgnoreCase(reserve.getCampusEquipmentStatus())) {
                message += reserve.getName() + " failed\n";
                continue;
            }

            if (!db.addReserve(reserve)) {
                message += reserve.getName() + " failed\n";
            }
        }

        context.removeAttribute("reserves");

        message += "Reserve successfully";

        sendRedirectAndMessage(request, response, message,
                "/Equipment?action=getCampus&campus=" + user.getCampus());

    }

    /**
     * Retrieves all reserves from the database and forwards the request to the
     * "reserves.jsp" page.
     * 
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     */
    public void getAllReserves(HttpServletRequest request, HttpServletResponse response) {
        try {
            WishList wishList = new WishList();
            wishList.setWishEquipments(db.getReserves());
            request.setAttribute("reserves", wishList);
            request.getRequestDispatcher("reserves.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the reserve status based on the provided request parameters.
     * 
     * @param request  the HttpServletRequest object containing the request
     *                 parameters
     * @param response the HttpServletResponse object for sending the response
     */
    public void updateReserve(HttpServletRequest request, HttpServletResponse response) {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            int courierId = Integer.parseInt(request.getParameter("Courier"));
            String message = "";
            String status = "Approved";

            if (db.updateReserveStatus(id, courierId, status)) {
                message = "Reserve status updated successfully";
                sendRedirectAndMessage(request, response, message, "/Reserve?action=allList");
            } else {
                message = "Failed to update reserve status";
                sendRedirectAndMessage(request, response, message, "/Reserve?action=allList");
            }

        } catch (NumberFormatException e) {
            // Handle the exception when parsing integers
            e.printStackTrace(); // Or log the error message
            return;
        } catch (Exception e) {
            // Handle other exceptions
            e.printStackTrace(); // Or log the error message
            return;
        }
    }

    public void getReserveStatus(HttpServletRequest request, HttpServletResponse response) {
        try {
            String pid = request.getParameter("id");
            int id = 0;
            try {
                id = Integer.parseInt(pid);
            } catch (NumberFormatException e) {
                // Handle the exception when parsing integers
                e.printStackTrace(); // Or log the error message
                return;
            }

            WishEquipment reserve = db.getReserveById(id);

            Users users = udb.getCourier();

            CampusEquipment campusEquipment = cedb.getCampusEquipmentById(reserve.getEquipmentId());

            request.setAttribute("reserve", reserve);
            request.setAttribute("users", users);
            request.setAttribute("campusEquipment", campusEquipment);

            request.getRequestDispatcher("reserveStatus.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            // Handle the exception when parsing integers
            e.printStackTrace(); // Or log the error message
            return;
        } catch (Exception e) {
            // Handle other exceptions
            e.printStackTrace(); // Or log the error message
            return;
        }
    }

    public void getPendingReserve(HttpServletRequest request, HttpServletResponse response, User user) {
        try {
            WishList wishList = new WishList();
            wishList.setWishEquipments(db.getReservesByStatusAndUserId("Pending", user.getId()));
            request.setAttribute("reserves", wishList);
            request.getRequestDispatcher("reserves.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getApprovalReserve(HttpServletRequest request, HttpServletResponse response, User user) {
        try {
            WishList wishList = new WishList();
            wishList.setWishEquipments(db.getReservesByStatusAndUserId("Approved", user.getId()));
            request.setAttribute("reserves", wishList);
            request.getRequestDispatcher("reserves.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends a redirect response to the client along with a message and target URL.
     *
     * @param request   The HttpServletRequest object representing the client's
     *                  request.
     * @param response  The HttpServletResponse object representing the response to
     *                  be sent to the client.
     * @param message   The message to be stored in the session attribute "message".
     * @param targetURL The target URL to which the client should be redirected.
     * @throws IOException If an I/O error occurs while sending the redirect
     *                     response.
     */
    public void sendRedirectAndMessage(HttpServletRequest request, HttpServletResponse response, String message,
            String targetURL) throws IOException {
        HttpSession session = request.getSession();
        session.setAttribute("message", message);
        response.sendRedirect(getServletContext().getContextPath() + targetURL);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    @Override
    public void init() {
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");

        Database database = new Database(dbUrl, dbUser, dbPassword);

        db = new ReserveDB(database);

        udb = new UserDB(database);

        cedb = new CampusEquipmentDB(database);

    }

}
