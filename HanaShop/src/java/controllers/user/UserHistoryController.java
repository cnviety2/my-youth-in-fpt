/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.user;

import daos.UserDAO;
import dtos.HistoryPaymentDTO;
import dtos.User;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
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
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;
import web_constants.PathConstants;

/**
 *
 * @author dell
 */
@WebServlet(urlPatterns = {"/user/history"})
public class UserHistoryController extends HttpServlet {

    private final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(UserHistoryController.class);
    private PathConstants path;

    @Override
    public void init() throws ServletException {
        logger.addAppender(new ConsoleAppender(new PatternLayout("%d{yyyy-MM-dd HH:mm:ss} %p %c{1}: %C %M - %m%n")));
        path = new PathConstants(getServletContext());
        try {
            logger.addAppender(new RollingFileAppender(new PatternLayout("%d{yyyy-MM-dd HH:mm:ss} %p %c{1}: %C %M - %m%n"), path.getProjectLogPath()));
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(UserHistoryController.class.getName()).log(Level.SEVERE, null, ex);
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
        try {
            UserDAO dao = new UserDAO();
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("USER");
            List<HistoryPaymentDTO> list = dao.getHistoryPaymentByUserID(user.getUserID());
            List<Date> listPaymentDate = new ArrayList<>();
            Set<Date> tempPaymentDate = new HashSet<>();
            for (HistoryPaymentDTO temp : list) {
                tempPaymentDate.add(temp.getPaymentDate());
            }
            listPaymentDate.addAll(tempPaymentDate);
            request.setAttribute("LIST_HISTORY_PAYMENT_DATE", listPaymentDate);
            request.setAttribute("HISTORY", list);
        } catch (SQLException ex) {
            logger.error("Error at UserHistoryController:" + ex.toString());
        }
        request.getRequestDispatcher("/user/view-history.jsp").forward(request, response);
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
