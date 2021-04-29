/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import daos.UserDAO;
import dtos.GoogleDTO;
import dtos.User;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;
import utils.GoogleUtils;
import web_constants.PathConstants;

/**
 *
 * @author dell
 */
@WebServlet(name = "GoogleLoginController", urlPatterns = {"/login-google"})
public class GoogleLoginController extends HttpServlet {

    private final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(GoogleLoginController.class);
    private final String SUCCESS = "/user";
    private final String FAIL = "/login.jsp";
    private PathConstants path;

    @Override
    public void init() throws ServletException {
        logger.addAppender(new ConsoleAppender(new PatternLayout("%d{yyyy-MM-dd HH:mm:ss} %p %c{1}: %C %M - %m%n")));
        path = new PathConstants(getServletContext());
        try {
            logger.addAppender(new RollingFileAppender(new PatternLayout("%d{yyyy-MM-dd HH:mm:ss} %p %c{1}: %C %M - %m%n"), path.getProjectLogPath()));
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(GoogleLoginController.class.getName()).log(Level.SEVERE, null, ex);
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
        String code = request.getParameter("code").trim();
        String url = FAIL;
        if (code == null || code.isEmpty()) {
            response.sendRedirect(PathConstants.CONTEXT_PATH + url);
        } else {
            String accessToken = GoogleUtils.getToken(code).trim();
            GoogleDTO googleDTO = GoogleUtils.getUserInfo(accessToken);
            String userName = googleDTO.getEmail().split("\\@")[0].trim();
            UserDAO dao = new UserDAO();
            User user = new User(userName, "***", "ROLE_USER");
            //Nếu user đã có trong db thì ko add mới
            try {
                boolean checkFlag = dao.isExist(userName);
                if (checkFlag) {
                    HttpSession session = request.getSession();
                    session.setAttribute("USER", user);
                    url = SUCCESS;
                } else {
                    //trường hợp này chưa có user trong db,add mới
                    dao.addAGoogleUser(user);
                    HttpSession session = request.getSession();
                    session.setAttribute("USER", user);
                    url = SUCCESS;
                }
            } catch (SQLException ex) {
                logger.error("Error at GoogleLoginController:" + ex.getMessage());
            } finally {
                response.sendRedirect(PathConstants.CONTEXT_PATH + url);
            }
        }
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
