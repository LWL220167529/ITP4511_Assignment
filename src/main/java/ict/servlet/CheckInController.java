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

    
     private CheckInDB checkInDB;
     private CheckOutDB checkOutDB;
     
    public void init() {
        String dbUrl = getServletContext().getInitParameter("dbUrl");
        String dbUser = getServletContext().getInitParameter("dbUser");
        String dbPassword = getServletContext().getInitParameter("dbPassword");

        Database db = new Database(dbUrl, dbUser, dbPassword);

        checkInDB = new CheckInDB(db);
        checkOutDB = new CheckOutDB(db);
    }

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

    
    private void addDamage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int checkInId = Integer.parseInt(request.getParameter("checkInId"));
        String damageReport = request.getParameter("damageReport");
        String message;

        if(checkInDB.reportDamage(checkInId, damageReport)) {
 
            if(checkInDB.confirmCheckIn(checkInId)) {
                message = "Damage reported and check-in confirmed successfully.";
            } else {
                message = "Damage reported but failed to confirm check-in.";
            }
        } else {
            message = "Failed to report damage. Please try again!";
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
        request.getRequestDispatcher("/checkIn.jsp").forward(request, response);
    }
    
     private void confirmCheckIn(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String checkInIdParam = request.getParameter("checkInId");
        String message; 
        try {
            int checkInId = Integer.parseInt(checkInIdParam);
            boolean success = checkInDB.confirmCheckIn(checkInId);

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

    private void insertRecord(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
            int userId = Integer.parseInt(request.getParameter("userId"));
            String userName = request.getParameter("userName");
            String equipmentName = request.getParameter("equipmentName");
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            String campusName = request.getParameter("campusName");
            String image = request.getParameter("image");
             // Get the current date
            java.util.Date today = new java.util.Date();
            java.sql.Date checkInDate = new java.sql.Date(today.getTime());
            String message; 
            
            boolean checkInSuccess = checkInDB.insertCheckIn(userId, userName, equipmentName, quantity, campusName, image, checkInDate);

            if (checkInSuccess) {
                // After successful check-in, update the checkout record to mark the item as returned
                int checkOutId = Integer.parseInt(request.getParameter("checkOutId")); // Ensure this parameter is provided
                boolean checkOutSuccess = checkOutDB.returnItem(checkOutId);
        
                if (checkOutSuccess) {
                    message = "Item returned successfully and checkout record updated.";
                } else {
                    message = "Item returned successfully, but failed to update checkout record.";
                }
            } else {
                message = "Failed to return item. Please try again.";
            }
    
        response.setContentType("text/html");
           try (PrintWriter out = response.getWriter()) {
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<script type='text/javascript'>");
                    out.println("alert('" + message + "');"); // Use the message in the alert
                    out.println("window.location='" + request.getContextPath() + "/borrowedRecord.jsp';"); // Redirect after the alert
                    out.println("</script>");
                    out.println("</head>");
                    out.println("<body>");
                    out.println("</body>");
                    out.println("</html>");
        }

        // Redirect to an appropriate page
        response.sendRedirect(request.getContextPath() + "/borrowedRecord.jsp");
  } 
     
    private void deleteCheckIn(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
            String checkoutIdParam = request.getParameter("checkInId");
            String message; 
            try {
                int checkInId = Integer.parseInt(checkoutIdParam);
                boolean success = checkInDB.deleteCheckIn(checkInId);

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

    private void confirmDamage(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
            int checkInId = Integer.parseInt(request.getParameter("checkInId"));
            String message;

            if(checkInDB.confirmDamage(checkInId)) {
                message = "Damage confirmed successfully.";
            } else {
                message = "Failed to confirm damage.";
            }

            response.setContentType("text/html");
                   try (PrintWriter out = response.getWriter()) {
                            out.println("<html>");
                            out.println("<head>");
                            out.println("<script type='text/javascript'>");
                            out.println("alert('" + message + "');"); // Use the message in the alert
                            out.println("window.location='" + request.getContextPath() + "/reviewDamage.jsp';"); // Redirect after the alert
                            out.println("</script>");
                            out.println("</head>");
                            out.println("<body>");
                            out.println("</body>");
                            out.println("</html>");
                }

            request.getRequestDispatcher("/reviewDamage.jsp").forward(request, response);
        }
    
          
     private void editDamage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            int checkInId = Integer.parseInt(request.getParameter("checkInId"));
            String damageReport = request.getParameter("damageReport");
            String message;
            boolean success = checkInDB.reportDamage(checkInId, damageReport);
        
            if (success && checkInDB.confirmDamage(checkInId)) {
                message = "Damage report updated and confirmed successfully.";
            } else {
                message = "Failed to update damage report.";
            }

            response.setContentType("text/html");
            try (PrintWriter out = response.getWriter()) {
                     out.println("<html>");
                     out.println("<head>");
                     out.println("<script type='text/javascript'>");
                     out.println("alert('" + message + "');"); // Use the message in the alert
                     out.println("window.location='" + request.getContextPath() + "/reviewDamage.jsp';"); // Redirect after the alert
                     out.println("</script>");
                     out.println("</head>");
                     out.println("<body>");
                     out.println("</body>");
                     out.println("</html>");
         }

            request.getRequestDispatcher("/reviewDamage.jsp").forward(request, response);
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
        } else if ("return".equals(action)){
             insertRecord(request, response);
        } else if ("reportDamage".equals(action)){
             addDamage(request, response);
        }else if ("confirmDamage".equals(action)){
           confirmDamage(request, response);
        }else if ("editDamage".equals(action)){
           editDamage(request, response);
        }else {
            processRequest(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
