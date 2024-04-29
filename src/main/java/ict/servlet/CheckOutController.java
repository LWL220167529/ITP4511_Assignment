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
import ict.db.CheckOutDB;
import ict.db.Database;
import ict.bean.CheckOut;



/**
 *
 * @author user
 */
@WebServlet(name = "CheckOutController", urlPatterns = {"/CheckOutController"})
public class CheckOutController extends HttpServlet {

     private CheckOutDB db;
     
    public void init() {
        String dbUrl = getServletContext().getInitParameter("dbUrl");
        String dbUser = getServletContext().getInitParameter("dbUser");
        String dbPassword = getServletContext().getInitParameter("dbPassword");

        Database database = new Database(dbUrl, dbUser, dbPassword);

        db = new CheckOutDB(database);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CheckOutController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CheckOutController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    
    private void confirmCheckout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String checkoutIdParam = request.getParameter("checkoutId");
        String message; 
        try {
            int checkoutId = Integer.parseInt(checkoutIdParam);
            boolean success = db.confirmCheckOut(checkoutId);

            if (success) {
                message = "The record has been successfully updated.";
            } else {
                message = "Failed to confirm checkout."; 
            }
        } catch (NumberFormatException e) {
             message = "Invalid checkout ID."; 
        }
        
         response.setContentType("text/html");
           try (PrintWriter out = response.getWriter()) {
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<script type='text/javascript'>");
                    out.println("alert('" + message + "');"); // Use the message in the alert
                    out.println("window.location='" + request.getContextPath() + "/checkOut.jsp';"); // Redirect after the alert
                    out.println("</script>");
                    out.println("</head>");
                    out.println("<body>");
                    out.println("</body>");
                    out.println("</html>");
        }

        // Redirect to an appropriate page
        response.sendRedirect(request.getContextPath() + "/checkOut.jsp");
    }

    private void deleteCheckout(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
            String checkoutIdParam = request.getParameter("checkoutId");
            String message; 
            try {
                int checkoutId = Integer.parseInt(checkoutIdParam);
                boolean success = db.deleteCheckOut(checkoutId);

                if (success) {
                    message = "The record has been successfully deleted.";
                } else {
                     message = "Failed to delete the record."; 
                }
            } catch (NumberFormatException e) {
                message = "Invalid checkout ID."; 
            }
            
        response.setContentType("text/html");
           try (PrintWriter out = response.getWriter()) {
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<script type='text/javascript'>");
                    out.println("alert('" + message + "');"); // Use the message in the alert
                    out.println("window.location='" + request.getContextPath() + "/checkOut.jsp';"); // Redirect after the alert
                    out.println("</script>");
                    out.println("</head>");
                    out.println("<body>");
                    out.println("</body>");
                    out.println("</html>");
        }

            response.sendRedirect(request.getContextPath() + "/checkOut.jsp");
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
            confirmCheckout(request, response);
        } else if ("delete".equals(action)){
             deleteCheckout(request, response);
        }
        else {
            // Handle other actions or default action
            processRequest(request, response);
        }
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
