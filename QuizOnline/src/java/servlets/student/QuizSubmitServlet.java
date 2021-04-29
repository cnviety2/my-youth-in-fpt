/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets.student;

import daos.UserDAO;
import dtos.HistoryQuizDTO;
import dtos.SearchQuestionDTO;
import dtos.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
public class QuizSubmitServlet extends HttpServlet {

    private PathConstants path;

    private final Logger logger = Logger.getLogger(QuizSubmitServlet.class);

    @Override
    public void init() throws ServletException {
        logger.addAppender(new ConsoleAppender(new PatternLayout("%d{yyyy-MM-dd HH:mm:ss} %p %c{1}: %C %M - %m%n")));
        path = new PathConstants(getServletContext());
        try {
            logger.addAppender(new RollingFileAppender(new PatternLayout("%d{yyyy-MM-dd HH:mm:ss} %p %c{1}: %C %M - %m%n"), path.getProjectLogPath()));
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(QuizSubmitServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        String url = "/user/home.jsp";
        String message = "";
        try {
            HttpSession session = request.getSession();
            String questionID = request.getParameter("questionID");
            String answerID = request.getParameter("answerID");
            Map<String, Integer> answersOfStudent = (Map<String, Integer>) session.getAttribute("STUDENT_ANSWER");
            if (answerID == null || answerID.isEmpty()) {
                answersOfStudent.put(questionID, null);
            } else {
                answersOfStudent.put(questionID, Integer.parseInt(answerID));
            }
            List<SearchQuestionDTO> listOfQuestionsOfTheQuiz = (List<SearchQuestionDTO>) session.getAttribute("QUIZ_QUESTIONS_OBJECT");
            List<String> listOfQuestionIDsOfTheQuiz = new ArrayList<>();
            for (SearchQuestionDTO dto : listOfQuestionsOfTheQuiz) {
                listOfQuestionIDsOfTheQuiz.add(dto.getQuestion().getId());
            }
            UserDAO dao = new UserDAO();
            Map<String, Integer> mapCorrectAnswerFromDB = dao.getCorrectAnswersByQuestionIDs(listOfQuestionIDsOfTheQuiz);
            int correctAnswerOfStudent = 0;
            for (Map.Entry<String, Integer> entry : answersOfStudent.entrySet()) {
                int answerOfStudent = entry.getValue() == null ? -1 : entry.getValue();
                int correctAnswerFromDB = mapCorrectAnswerFromDB.get(entry.getKey());
                if (answerOfStudent == correctAnswerFromDB) {
                    correctAnswerOfStudent++;
                }
            }
            for (String tmp : listOfQuestionIDsOfTheQuiz) {
                if (!answersOfStudent.containsKey(tmp)) {
                    answersOfStudent.put(tmp, -1);
                }
            }
            HistoryQuizDTO history = new HistoryQuizDTO();
            history.setMapStudentAnswers(answersOfStudent);
            history.setScore(correctAnswerOfStudent);
            history.setSubjectID((int) session.getAttribute("QUIZ_SUBJECT"));
            User user = (User) session.getAttribute("USER");
            history.setUserEmail(user.getEmail());
            dao.saveHistory(history);
            message = "Nop bai thanh cong,so cau dung:" + correctAnswerOfStudent + "/" + (int) session.getAttribute("QUIZ_TOTAL_QUESTIONS");
            session.setAttribute("QUIZ_QUESTIONS_OBJECT", null);
            session.setAttribute("TAKING_QUIZ_FLAG", null);
        } catch (NumberFormatException e) {
            logger.error("Error at QuizSubmitServlet:" + e.toString());
            message = "Loi khi parse sang so";
        } catch (Exception e) {
            logger.error("Error at QuizSubmitServlet:" + e.toString());
        } finally {
            request.setAttribute("MESSAGE", message);
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
