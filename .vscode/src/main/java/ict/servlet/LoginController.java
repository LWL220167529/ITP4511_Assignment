/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ict.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ict.bean.User;
import ict.db.UserDB;

/**
 *
 * @author User
 */
@WebServlet(name = "LoginController", urlPatterns = { "/Login" })
public class LoginController extends HttpServlet {
    private UserDB udb;

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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet LoginController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
        doLogout(request, response);
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
        String action = request.getParameter("action");
        if (!isAuthenticated(request) && !("authenticate".equals(action))) {
            // doWelcome(request, response);
            doLogin(request, response);
            return;
        } else if ("authenticate".equals(action)) {
            doAuthenticate(request, response);
            return;
        } else {
            
            doLogout(request, response);
            return;
        }
        //   else {
        //     response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
        // }
    }

    private void doLogout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletContext context = getServletContext();
        context.removeAttribute("reserves");
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute("user");
            session.invalidate();
        }
        doLogin(request, response);
    }

    private void doLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String targetURL = getServletContext().getContextPath();
        response.sendRedirect(targetURL);
        return;
    }

    private boolean isAuthenticated(HttpServletRequest request) {
        boolean result = false;
        HttpSession session = request.getSession();

        if (session.getAttribute("user") != null) {
            result = true;
        }

        return result;
    }

    private void doAuthenticate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        String targetURL;
        String[] isValid = udb.isValidUser(username, password);
        HttpSession session = request.getSession(true);

        if (Boolean.valueOf(isValid[0])) {
            User bean = udb.getUserById(Integer.parseInt(isValid[1]));
            session.setAttribute("user", bean);
            targetURL = getServletContext().getContextPath() + "/Equipment?action=getCampus&campus=" + bean.getCampus();
        } else {
            String error = "Invalid login. Please try again.";
            session.setAttribute("error", error);
            targetURL = getServletContext().getContextPath(); // Fix: Call getContextPath() method to get the context path as a string
        }
        response.sendRedirect(targetURL);
        return;
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

        udb = new UserDB(dbUrl, dbUser, dbPassword);

    }
}
