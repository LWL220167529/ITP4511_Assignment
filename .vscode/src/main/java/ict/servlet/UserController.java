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
import javax.servlet.http.HttpSession;

import ict.bean.User;
import ict.db.UserDB;

/**
 *
 * @author User
 */
@WebServlet(name = "UserController", urlPatterns = { "/User" })
public class UserController extends HttpServlet {
    private UserDB db;

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
            out.println("<title>Servlet UserController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UserController at " + request.getContextPath() + "</h1>");
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
        response.sendRedirect(getServletContext().getContextPath() + "/userEditForm.jsp");
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
        if ("edit".equals(action)) {
            showUserEditForm(request, response);
        } else if ("add".equals(action)) {
            addUser(request, response);
        } else if ("update".equals(action)) {
            updateUser(request, response);
        } else {
            response.sendRedirect(getServletContext().getContextPath() + "/user.jsp");
        }
        // else if ("delete".equals(action)) {
        // deleteUser(request, response);

    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String message = "";
        boolean hasError = false;
        String idStr = request.getParameter("id");
        int id = 0;
        User user;
        try {
            id = Integer.parseInt(idStr);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        User sessionUser = (User) request.getSession().getAttribute("user");
        if (id == 0 && !sessionUser.getRole().equals("admin")) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        String username = request.getParameter("username");
        username = username.replace("  ", " ");
        if (username == null || username.isEmpty()) {
            message = "Username is required\n";
            hasError = true;
        }
        String phone = request.getParameter("phone");
        if (phone == null || phone.isEmpty() || phone.length() > 8) {
            message += "Phone is required\n";
            hasError = true;
        }
        String email = request.getParameter("email");
        if (email == null || email.isEmpty()) {
            message += "Email is required";
            hasError = true;
        }
        String campus = request.getParameter("campus");
        if (campus == null || campus.isEmpty()) {
            message += "Campus is required";
            hasError = true;
        }
        campus = campus.toUpperCase();
        String role = request.getParameter("role");
        System.out.println(role);
        if (hasError) {
            sendRedirectAndMessage(request, response, message, "/userEditForm.jsp");
            return;
        }
        role = role.toLowerCase();
        try {
            user = new User(username, email, phone, campus, role);
            System.out.println(user.toString());
            user.setId(id);
        } catch (IllegalArgumentException e) {
            sendRedirectAndMessage(request, response, e.getMessage(), "/home.jsp");
            return;
        }
        if (!db.updateUser(user)) {
            sendRedirectAndMessage(request, response, "Failed to update user", "/userEditForm.jsp");
            return;
        }
        sendRedirectAndMessage(request, response, "User updated successfully", "/user.jsp");
    }

    public void showUserEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // get user id from request
        // get user by id
        // set user to request
        // forward to user edit form
        String targetURL = "/userEditForm.jsp";
        int id = 0;
        HttpSession session = request.getSession();
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        User sessionUser = (User) request.getSession().getAttribute("user");
        if (id == 0 && !sessionUser.getRole().equals("admin")) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        User user = db.getUserById(id);
        if (user == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        session.setAttribute("userForm", user);
        response.sendRedirect(getServletContext().getContextPath() + targetURL);
    }

    public void addUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String message = "";
        boolean hasError = false;
        User user;
        String username = request.getParameter("username");
        username = username.replace("  ", " ");
        if (username == null || username.isEmpty()) {
            message = "Username is required\n";
            hasError = true;
        }
        String password = request.getParameter("password");
        if (password == null || password.isEmpty()) {
            message += "Password is required\n";
            hasError = true;
        }
        String phone = request.getParameter("phone");
        if (phone == null || phone.isEmpty() || phone.length() > 8) {
            message += "Phone is required\n";
            hasError = true;
        }
        String email = request.getParameter("email");
        if (email == null || email.isEmpty()) {
            message += "Email is required";
            hasError = true;
        }
        String campus = request.getParameter("campus");
        if (campus == null || campus.isEmpty()) {
            message += "Campus is required";
            hasError = true;
        }
        campus = campus.toUpperCase();
        String role = request.getParameter("role");
        if (hasError) {
            sendRedirectAndMessage(request, response, message, "/user.jsp");
            return;
        }
        role = role.toLowerCase();
        try {
            user = new User(username, email, phone, campus, role);
            System.out.println(user.toString());
        } catch (IllegalArgumentException e) {
            sendRedirectAndMessage(request, response, e.getMessage(), "/home.jsp");
            return;
        }
        if (!db.addUser(user, password)) {
            sendRedirectAndMessage(request, response, "Failed to add user", "/user.jsp");
            return;
        }
        sendRedirectAndMessage(request, response, "User added successfully", "/user.jsp");
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

        db = new UserDB(dbUrl, dbUser, dbPassword);

    }
}
