/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets.student;

import daos.UserDAO;
import dtos.Answer;
import dtos.AnswerOfStudentDTO;
import dtos.QuestionHistoryQuizDTO;
import dtos.User;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;
import utils.PathConstants;

/**
 *
 * @author dell
 */
public class HistoryServlet extends HttpServlet {

    private PathConstants path;

    private final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(HistoryServlet.class);

    @Override
    public void init() throws ServletException {
        logger.addAppender(new ConsoleAppender(new PatternLayout("%d{yyyy-MM-dd HH:mm:ss} %p %c{1}: %C %M - %m%n")));
        path = new PathConstants(getServletContext());
        try {
            logger.addAppender(new RollingFileAppender(new PatternLayout("%d{yyyy-MM-dd HH:mm:ss} %p %c{1}: %C %M - %m%n"), path.getProjectLogPath()));
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(HistoryServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        String url = "/user/view-history.jsp";
        try {
            String pageString = request.getParameter("page");
            Integer page = Integer.parseInt(pageString);
            if (pageString == null || pageString.isEmpty()) {
                page = 0;
            }
            String subjectString = request.getParameter("subjectID");
            Integer subjectID;
            if (subjectString == null || subjectString.isEmpty() || subjectString.equals("0")) {
                subjectID = null;
            } else {
                subjectID = Integer.parseInt(subjectString);
            }
            HttpSession session = request.getSession();
            UserDAO dao = new UserDAO();
            String email = (String) ((User) session.getAttribute("USER")).getEmail();
            List<QuestionHistoryQuizDTO> listHistory = dao.processForHistoryViewing(email, subjectID, page);
            List<String> listQuestionID = new ArrayList<>();
            for (QuestionHistoryQuizDTO historyDTO : listHistory) {
                for (AnswerOfStudentDTO answerOfStudentDTO : historyDTO.getAnswersOfStudent()) {
                    listQuestionID.add(answerOfStudentDTO.getQuestionID());
                }
            }
            List<Answer> listAnswerFromDB = dao.getAllAnswersByQuestionID(listQuestionID);
            int totalRecords = dao.getAllRecordsOfAUser(email, subjectID);
            int totalPages = (int) Math.ceil(totalRecords / 1);
            request.setAttribute("LIST_ANSWER_FROM_DB", listAnswerFromDB);
            request.setAttribute("LIST_HISTORY", listHistory);
            request.setAttribute("LIST_HISTORY", listHistory);
            request.setAttribute("TOTAL_PAGES", totalPages);
            request.setAttribute("PAGE", page);
            request.setAttribute("SUBJECT_ID", subjectID);
        } catch (SQLException ex) {
            logger.error("Error at HistoryServlet:" + ex.toString());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
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
