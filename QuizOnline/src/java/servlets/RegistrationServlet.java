/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import daos.GeneralDAO;
import dtos.User;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;
import utils.PathConstants;

/**
 *
 * @author dell
 */
public class RegistrationServlet extends HttpServlet {

    private PathConstants path;

    private final Logger logger = Logger.getLogger(RegistrationServlet.class);

    @Override
    public void init() throws ServletException {
        logger.addAppender(new ConsoleAppender(new PatternLayout("%d{yyyy-MM-dd HH:mm:ss} %p %c{1}: %C %M - %m%n")));
        path = new PathConstants(getServletContext());
        try {
            logger.addAppender(new RollingFileAppender(new PatternLayout("%d{yyyy-MM-dd HH:mm:ss} %p %c{1}: %C %M - %m%n"), path.getProjectLogPath()));
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(RegistrationServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        super.init(); //To change body of generated methods, choose Tools | Templates.
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
        String method = request.getMethod();
        String message = "";
        String url = "/registration.jsp";
        switch (method) {
            case "GET":
                response.sendRedirect("/QuizOnline/registration.jsp");
                break;
            case "POST":
                String name = request.getParameter("name");
                String email = request.getParameter("email");
                String password = request.getParameter("password");
                String rePassword = request.getParameter("rePassword");
                try {
                    if (name.isEmpty() || email.isEmpty() || password.isEmpty() || rePassword.isEmpty()) {
                        throw new NullPointerException();
                    } else {
                        if (!password.equals(rePassword)) {
                            message = "Password va password nhap lai khong trung nhau";
                            url = "/registration.jsp";
                        } else {
                            MessageDigest digest = MessageDigest.getInstance("SHA-256");
                            byte[] hashPassword = digest.digest(password.getBytes(StandardCharsets.UTF_8));
                            String encodedPassword = Base64.getEncoder().encodeToString(hashPassword);
                            GeneralDAO dao = new GeneralDAO();
                            User user = new User(email, encodedPassword, name);
                            boolean flag = dao.addAUser(user);
                            if (flag) {
                                message = "Dang ky thanh cong";
                                url = "/user/home.jsp";
                                HttpSession session = request.getSession();
                                session.setAttribute("USER", user);
                            } else {
                                message = "Dang ky khong thanh cong,email da trung";
                                url = "/registration.jsp";
                            }
                        }
                    }

                } catch (NullPointerException e) {
                    message = "Khong duoc nhap thieu";
                    logger.error("Error at RegistrationServlet:" + e.toString());
                } catch (Exception e) {
                    message = "Loi khong xac dinh";
                    logger.error("Error at RegistrationServlet:" + e.toString());
                } finally {
                    request.setAttribute("MESSAGE", message);
                    request.getRequestDispatcher(url).forward(request, response);
                }
                break;
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

}
