/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import daos.HistoryDAO;
import daos.LoginDAO;
import daos.SearchDAO;
import dtos.Car;
import dtos.User;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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
import utils.CaptchaVerifyUtil;
import utils.PathConstants;

/**
 *
 * @author dell
 */
@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    private PathConstants path;

    private final Logger logger = Logger.getLogger(LoginServlet.class);

    @Override
    public void init() throws ServletException {
        logger.addAppender(new ConsoleAppender(new PatternLayout("%d{yyyy-MM-dd HH:mm:ss} %p %c{1}: %C %M - %m%n")));
        path = new PathConstants(getServletContext());
        try {
            logger.addAppender(new RollingFileAppender(new PatternLayout("%d{yyyy-MM-dd HH:mm:ss} %p %c{1}: %C %M - %m%n"), path.getProjectLogPath()));
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String message = "";
        String url = "";
        try {
            if (email.isEmpty() || password.isEmpty()) {
                throw new NullPointerException();
            }
            LoginDAO dao = new LoginDAO();
            User user = dao.checkLogin(email, password);
            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("USER", user);
                switch (user.getRole()) {
                    case "ROLE_ADMIN":
                        break;
                    case "ROLE_USER":
                        url = "/user/home.jsp";
                        String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
                        // Verify CAPTCHA.
                        boolean valid = CaptchaVerifyUtil.verify(gRecaptchaResponse);
                        if (!valid) {
                            message = "Captcha invalid!";
                            session.invalidate();
                            url = "/login.jsp";
                        } else {
                            SearchDAO searchDAO = new SearchDAO();
                            HistoryDAO historyDAO = new HistoryDAO();
                            List<Car> listCar = searchDAO.searchCar("", "", null, null, 0);
                            Set<Date> setOrderDateOfThisUser = historyDAO.getAllOrderDateByEmail(email);
                            List<Date> listOrderDateOfThisUser = new ArrayList<>();
                            listOrderDateOfThisUser.addAll(setOrderDateOfThisUser);
                            session.setAttribute("LIST_ORDER_DATE", listOrderDateOfThisUser);
                            request.setAttribute("LIST_CAR", listCar);
                        }
                        break;
                }

            } else {
                message = "Khong ton tai user";
                url = "/login.jsp";
            }
        } catch (NullPointerException e) {
            message = "Loi null pointer";

        } catch (Exception e) {
            logger.error("Error at LoginServlet:" + e.toString());
        } finally {
            request.setAttribute("MESSAGE", message);
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
