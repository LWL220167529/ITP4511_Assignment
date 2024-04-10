/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ict.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ict.bean.UserReserve;
import ict.bean.WishList;
import ict.bean.User;
import ict.db.ReserveDB;

/**
 *
 * @author User
 */
@WebServlet(name = "ReserveController", urlPatterns = { "/Reserve" })
public class ReserveController extends HttpServlet {
    private ReserveDB db;

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
        boolean isRedirect = false;
        String message = "";

        HttpSession session = request.getSession();

        User user;

        if (session.getAttribute("user") == null) {
            response.sendRedirect("Login");
            return;
        } else {
            user = (User) session.getAttribute("user");
        }

        if ("reserve".equalsIgnoreCase(action)) {
            int userId;
            int equipmentId;
            String belongCampusId;
            String destinationCampusId;
            int quantity;
            String status;
            Date date;
            try {
                userId = user.getId();
                String equipmentIdParam = request.getParameter("equipmentId");
                belongCampusId = request.getParameter("belongCampusId");
                String quantityParam = request.getParameter("quantity");
                String dateParam = request.getParameter("date");

                if (equipmentIdParam == null) {
                    message += "Please fill id field\n";
                } 
                if (belongCampusId == null) {
                    message += "Please fill campus fields\n";
                } 
                if (quantityParam == null) {
                    message += "Please fill quantity fields\n";
                } 
                if (dateParam == null) {
                    message += "Please fill quantity fields\n";
                }

                if (isRedirect) {

                    sendRedirectAndMessage(request, response, message,
                            "/Equipment?action=getCampus&campus=" + user.getCampus());
                    return;
                }

                equipmentId = Integer.parseInt(equipmentIdParam);
                destinationCampusId = user.getCampus();
                quantity = Integer.parseInt(quantityParam);
                status = "requesting";
                date = Date.valueOf(dateParam);

                if (quantity <= 0) {
                    message += "Quantity must be greater than 0";
                    isRedirect = true;
                }

                if (isRedirect) {

                    sendRedirectAndMessage(request, response, message,
                            "Equipment?action=reserveForm&id=" + equipmentId);
                    return;
                }

                
                UserReserve reserve = new UserReserve();

                reserve.setUserId(userId);
                reserve.setEquipmentId(equipmentId);
                reserve.setBelongCampusId(belongCampusId);
                reserve.setDestinationCampusId(destinationCampusId);
                reserve.setQuantity(quantity);
                reserve.setStatus(status);
                reserve.setDate(date);

                if (db.addReserve(reserve)) {
                    message += "Reserve successfully";
                } else {
                    message += "Reserve failed";
                }
                
                sendRedirectAndMessage(request, response, "Reserve successfully",
                "/Equipment?action=getCampus&campus=" + user.getCampus());
            } catch (NumberFormatException e) {
                // Handle the exception when parsing integers
                e.printStackTrace(); // Or log the error message
                return;
            } catch (IllegalArgumentException e) {
                // Handle the exception when parsing date
                e.printStackTrace(); // Or log the error message
                return;
            } catch (Exception e) {
                // Handle other exceptions
                e.printStackTrace(); // Or log the error message
                return;
            }
        } else if ("list".equalsIgnoreCase(action)) {
            WishList wishList = new WishList();
            try {
                wishList.setWishEquipments(db.getReservesByUserId(user.getId()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            request.setAttribute("reserves", wishList);
            request.getRequestDispatcher("reserves.jsp").forward(request, response);
        } else if ("delete".equalsIgnoreCase(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            db.deleteReserve(id);
            response.sendRedirect("Reserve?action=list");
        } else if ("update".equalsIgnoreCase(action)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                String status = request.getParameter("status");

                db.updateReserveStatus(id, status);
                response.sendRedirect("Reserve?action=list");
            } catch (NumberFormatException e) {
                // Handle the exception when parsing integers
                e.printStackTrace(); // Or log the error message
                return;
            } catch (Exception e) {
                // Handle other exceptions
                e.printStackTrace(); // Or log the error message
                return;
            }
        } else {
            response.sendRedirect("home.jsp");
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

        db = new ReserveDB(dbUrl, dbUser, dbPassword);

    }

}
