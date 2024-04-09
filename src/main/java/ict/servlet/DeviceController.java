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

import ict.db.DeviceDB;
import ict.db.CampusDeviceDB;

import ict.bean.CampusDevice;
import ict.bean.CampusDevices;

/**
 *
 * @author User
 */
@WebServlet(name = "DeviceController", urlPatterns = {"/DeviceController"})
public class DeviceController extends HttpServlet {
    private CampusDeviceDB cdb;
    private DeviceDB ddb;

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
            response.sendRedirect("LoginController");
            return;
        }

        if ("getCampus".equalsIgnoreCase(action)) {
            String campus = request.getParameter("campus");
            CampusDevices campusDevices = new CampusDevices();
            campusDevices.setCampusDevices(cdb.getCampusDevicesByCampus(campus));
            campusDevices.setCampus(campus);
            request.setAttribute("devices", campusDevices);
            request.getRequestDispatcher("home.jsp").forward(request, response);
        } else if ("getDevice".equalsIgnoreCase(action)) {
            request.setAttribute("devices", ddb.getAllDevices());
            request.getRequestDispatcher("devices.jsp").forward(request, response);
        } else if ("reserve".equalsIgnoreCase(action)) {
            String campus = request.getParameter("campus");
            int deviceId = Integer.parseInt(request.getParameter("deviceId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            String status = request.getParameter("status");
            CampusDevice cd = new CampusDevice(campus, deviceId, quantity, status);
            if (cdb.addCampusDevice(cd)) {
                request.setAttribute("msg", "Campus Device added.");
            } else {
                request.setAttribute("msg", "Campus Device exists.");
            }
            request.getRequestDispatcher("addCampusDevice.jsp").forward(request, response);
        } else if ("reserveForm".equalsIgnoreCase(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            CampusDevice cd = cdb.getCampusDeviceById(id);
            request.setAttribute("device", cd);
            request.getRequestDispatcher("reserveForm.jsp").forward(request, response);
        } else if ("return".equalsIgnoreCase(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            if (false) {
                request.setAttribute("msg", "Campus Device returned.");
            } else {
                request.setAttribute("msg", "Campus Device not returned.");
            }
            request.getRequestDispatcher("return.jsp").forward(request, response);
        } else if ("updateStatus".equalsIgnoreCase(action)) {
            cdb.updateStatusForQuantityLessThanOne();
            request.setAttribute("msg", "Status updated.");
            request.getRequestDispatcher("updateStatus.jsp").forward(request, response);
        } else {
            response.sendRedirect("LoginController");
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

        cdb = new CampusDeviceDB(dbUrl, dbUser, dbPassword);

        ddb = new DeviceDB(dbUrl, dbUser, dbPassword);

    }

}
