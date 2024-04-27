/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ict.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ict.db.UserDB;
import ict.db.CheckInDB;
import ict.db.CheckOutDB;
import ict.db.Database;
import ict.bean.CheckIn;

/**
 *
 * @author user
 */
@WebServlet(name = "CheckInController", urlPatterns = {"/CheckInController"})
public class CheckInController extends HttpServlet {

    
     private CheckInDB db;
     
    public void init() {
        String dbUrl = getServletContext().getInitParameter("dbUrl");
        String dbUser = getServletContext().getInitParameter("dbUser");
        String dbPassword = getServletContext().getInitParameter("dbPassword");
        db = new CheckInDB(dbUrl, dbUser, dbPassword);
    }
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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CheckInController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CheckInController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    
     private void confirmCheckIn(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String checkInIdParam = request.getParameter("checkInId");
        String message; 
        try {
            int checkInId = Integer.parseInt(checkInIdParam);
            boolean success = db.confirmCheckIn(checkInId);

            if (success) {
                message = "The record has been successfully updated.";
            } else {
                message = "Failed to confirm checkin."; 
            }
        } catch (NumberFormatException e) {
             message = "Invalid checkin ID."; 
        }
        
         response.setContentType("text/html");
           try (PrintWriter out = response.getWriter()) {
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<script type='text/javascript'>");
                    out.println("alert('" + message + "');"); // Use the message in the alert
                    out.println("window.location='" + request.getContextPath() + "/checkIn.jsp';"); // Redirect after the alert
                    out.println("</script>");
                    out.println("</head>");
                    out.println("<body>");
                    out.println("</body>");
                    out.println("</html>");
        }

        // Redirect to an appropriate page
        response.sendRedirect(request.getContextPath() + "/checkIn.jsp");
    }

    private void deleteCheckIn(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
            String checkoutIdParam = request.getParameter("checkInId");
            String message; 
            try {
                int checkInId = Integer.parseInt(checkoutIdParam);
                boolean success = db.deleteCheckIn(checkInId);

                if (success) {
                    message = "The record has been successfully deleted.";
                } else {
                     message = "Failed to delete the record."; 
                }
            } catch (NumberFormatException e) {
                message = "Invalid checkin ID."; 
            }
            
        response.setContentType("text/html");
           try (PrintWriter out = response.getWriter()) {
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<script type='text/javascript'>");
                    out.println("alert('" + message + "');"); // Use the message in the alert
                    out.println("window.location='" + request.getContextPath() + "/checkIn.jsp';"); // Redirect after the alert
                    out.println("</script>");
                    out.println("</head>");
                    out.println("<body>");
                    out.println("</body>");
                    out.println("</html>");
        }

            response.sendRedirect(request.getContextPath() + "/checkIn.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("confirm".equals(action)) {
            confirmCheckIn(request, response);
        } else if ("delete".equals(action)){
             deleteCheckIn(request, response);
        }
        else {
            // Handle other actions or default action
            processRequest(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
