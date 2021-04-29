/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets.admin;

import daos.AdminDAO;
import dtos.Answer;
import dtos.SearchQuestionDTO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;
import utils.PathConstants;

/**
 *
 * @author dell
 */
public class DeleteQuestionServlet extends HttpServlet {

    private PathConstants path;

    private final Logger logger = Logger.getLogger(DeleteQuestionServlet.class);

    @Override
    public void init() throws ServletException {
        logger.addAppender(new ConsoleAppender(new PatternLayout("%d{yyyy-MM-dd HH:mm:ss} %p %c{1}: %C %M - %m%n")));
        path = new PathConstants(getServletContext());
        try {
            logger.addAppender(new RollingFileAppender(new PatternLayout("%d{yyyy-MM-dd HH:mm:ss} %p %c{1}: %C %M - %m%n"), path.getProjectLogPath()));
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(DeleteQuestionServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        String questionID = request.getParameter("idDelete");
        String message = "";
        String url = "/admin/home.jsp";
        try {
            AdminDAO adminDAO = new AdminDAO();
            boolean flag = adminDAO.deleteQuestion(questionID);
            if (flag) {
                message = "Xoa thanh cong";
            } else {
                message = "Xoa chua thanh cong";
            }
            List<SearchQuestionDTO> listQuestion = adminDAO.searchPart1("", true, null, 0);
            List<String> listQuestionID = new ArrayList<>();
            int totalRecords = adminDAO.getAllRecordsOfSearchResult("", true, null);
            int totalPages = (int) Math.ceil(totalRecords / 5);
            if (totalRecords % 5 == 0) {
                totalPages = (totalRecords / 5) - 1;
            }
            for (SearchQuestionDTO dto : listQuestion) {
                listQuestionID.add(dto.getQuestion().getId());
            }
            List<Answer> listAnswer = adminDAO.searchPart2(listQuestionID);
            for (SearchQuestionDTO dto : listQuestion) {
                for (Answer answer : listAnswer) {
                    if (dto.getQuestion().getId().equals(answer.getQuestionID())) {
                        dto.getListAnswers().add(answer);
                    }
                }
            }
            request.setAttribute("SEARCH_RESULT", listQuestion);
            request.setAttribute("SEARCH_STATUS", true);
            request.setAttribute("SEARCH_CONTENT", "");
            request.setAttribute("SEARCH_SUBJECT", "none");
            request.setAttribute("TOTAL_PAGES", totalPages);
            request.setAttribute("STANDING_PAGE", 0);
        } catch (Exception e) {
            logger.error("Error at DeleteQuestionServlet:" + e.toString());
            message = "Loi";
        } finally {
            request.setAttribute("MESSAGE", message);
            request.getRequestDispatcher(url).forward(request, response);
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
