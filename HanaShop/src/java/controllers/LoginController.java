/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import daos.LoginDAO;
import dtos.User;
import java.io.IOException;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;
import web_constants.PathConstants;

/**
 *
 * @author dell
 */
@WebServlet(urlPatterns = {"/login"})
public class LoginController extends HttpServlet {

    private final String LOGIN_SUCCESS_AS_ADMIN = "/admin";
    private final String LOGIN_SUCCESS_AS_USER = "/user";
    private final String LOGIN_FAILED = "/login.jsp";
    private PathConstants path;

    private final Logger logger = Logger.getLogger(LoginController.class);

    @Override
    public void init() throws ServletException {
        logger.addAppender(new ConsoleAppender(new PatternLayout("%d{yyyy-MM-dd HH:mm:ss} %p %c{1}: %C %M - %m%n")));
        path = new PathConstants(getServletContext());
        try {
            logger.addAppender(new RollingFileAppender(new PatternLayout("%d{yyyy-MM-dd HH:mm:ss} %p %c{1}: %C %M - %m%n"), path.getProjectLogPath()));
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        super.init(); //To change body of generated methods, choose Tools | Templates.
    }

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

        HttpSession session = request.getSession(false);
        String url = "";
        if (session != null) {
            try {
                User user = (User) session.getAttribute("USER");
                switch (user.getRole()) {
                    case "ROLE_ADMIN":
                        url = LOGIN_SUCCESS_AS_ADMIN;
                        break;
                    case "ROLE_USER":
                        url = LOGIN_SUCCESS_AS_USER;
                        break;
                }
                request.getRequestDispatcher(url).forward(request, response);
                return;
            } catch (Exception e) {
                logger.error("Error at LoginController-GET:" + e.toString());
            }
        }
        response.sendRedirect(PathConstants.CONTEXT_PATH + "/login.jsp");
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
        String userID = request.getParameter("userID");
        String password = request.getParameter("password");
        String url = LOGIN_FAILED;
        LoginDAO dao = new LoginDAO();
        try {
            if (userID.isEmpty() || password.isEmpty()) {
                request.setAttribute("MESSAGE", "UserID va Password khong duoc trong");
            } else {
                userID = userID.trim();
                password = password.trim();
                User user = dao.checkLogin(userID, password);
                if (user != null) {
                    switch (user.getRole()) {
                        case "ROLE_ADMIN":
                            url = LOGIN_SUCCESS_AS_ADMIN;
                            HttpSession session = request.getSession();
                            session.setAttribute("USER", user);
                            break;
                        case "ROLE_USER":
                            url = LOGIN_SUCCESS_AS_USER;
                            session = request.getSession();
                            session.setAttribute("USER", user);
                            break;
                    }
                } else {
                    request.setAttribute("MESSAGE", "Khong ton tai user nay");
                }
            }

        } catch (Exception e) {
            logger.error("Error at LoginController-POST:" + e.toString());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }

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

}
