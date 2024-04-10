/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ict.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ict.db.EquipmentDB;
import ict.db.CampusEquipmentDB;

import ict.bean.CampusEquipment;
import ict.bean.CampusEquipments;

/**
 *
 * @author User
 */
@WebServlet(name = "EquipmentController", urlPatterns = {"/Equipment"})
public class EquipmentController extends HttpServlet {
    private CampusEquipmentDB cdb;
    private EquipmentDB ddb;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null) {
            response.sendRedirect("Login");
            return;
        }

        if ("getCampus".equalsIgnoreCase(action)) {
            String campus = request.getParameter("campus");
            CampusEquipments campusEquipments = new CampusEquipments();
            campusEquipments.setCampusEquipments(cdb.getCampusEquipmentsByCampus(campus));
            campusEquipments.setCampus(campus);
            request.setAttribute("equipments", campusEquipments);
            request.getRequestDispatcher("home.jsp").forward(request, response);
        } else if ("getEquipment".equalsIgnoreCase(action)) {
            request.setAttribute("equipments", ddb.getAllEquipments());
            request.getRequestDispatcher("equipments.jsp").forward(request, response);
        } else if ("reserve".equalsIgnoreCase(action)) {
            String campus = request.getParameter("campus");
            int equipmentId = Integer.parseInt(request.getParameter("equipmentId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            String status = request.getParameter("status");
            CampusEquipment cd = new CampusEquipment(campus, equipmentId, quantity, status);
            if (cdb.addCampusEquipment(cd)) {
                request.setAttribute("msg", "Campus Equipment added.");
            } else {
                request.setAttribute("msg", "Campus Equipment exists.");
            }
            request.getRequestDispatcher("addCampusEquipment.jsp").forward(request, response);
        } else if ("reserveForm".equalsIgnoreCase(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            CampusEquipment cd = cdb.getCampusEquipmentById(id);
            request.setAttribute("equipment", cd);
            request.getRequestDispatcher("reserveForm.jsp").forward(request, response);
        } else if ("return".equalsIgnoreCase(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            if (false) {
                request.setAttribute("msg", "Campus Equipment returned.");
            } else {
                request.setAttribute("msg", "Campus Equipment not returned.");
            }
            request.getRequestDispatcher("return.jsp").forward(request, response);
        } else if ("updateStatus".equalsIgnoreCase(action)) {
            cdb.updateStatusForQuantityLessThanOne();
            request.setAttribute("msg", "Status updated.");
            request.getRequestDispatcher("updateStatus.jsp").forward(request, response);
        } else {
            response.sendRedirect("Login");
        }
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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

        cdb = new CampusEquipmentDB(dbUrl, dbUser, dbPassword);

        ddb = new EquipmentDB(dbUrl, dbUser, dbPassword);

    }

}
