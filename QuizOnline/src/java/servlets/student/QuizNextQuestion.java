/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets.student;

import dtos.SearchQuestionDTO;
import java.io.IOException;
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
public class QuizNextQuestion extends HttpServlet {

    private PathConstants path;

    private final Logger logger = Logger.getLogger(QuizNextQuestion.class);

    @Override
    public void init() throws ServletException {
        logger.addAppender(new ConsoleAppender(new PatternLayout("%d{yyyy-MM-dd HH:mm:ss} %p %c{1}: %C %M - %m%n")));
        path = new PathConstants(getServletContext());
        try {
            logger.addAppender(new RollingFileAppender(new PatternLayout("%d{yyyy-MM-dd HH:mm:ss} %p %c{1}: %C %M - %m%n"), path.getProjectLogPath()));
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(QuizNextQuestion.class.getName()).log(Level.SEVERE, null, ex);
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
        String message = "";
        String url = "/user/quiz.jsp";
        HttpSession session = request.getSession();
        try {
            String questionNumber = request.getParameter("questionNumber");
            String questionID = request.getParameter("questionID");
            String answerID = request.getParameter("answerID");
            String remainSeconds = request.getParameter("remainSeconds");
            if (remainSeconds.equals("0")) {
                Map<String, Integer> studentAnswer = (Map<String, Integer>) session.getAttribute("STUDENT_ANSWER");
                if (answerID == null || answerID.isEmpty()) {
                    studentAnswer.put(questionID, null);
                } else {
                    studentAnswer.put(questionID, Integer.parseInt(answerID));
                }
                session.setAttribute("STUDENT_ANSWER", studentAnswer);
                session.setAttribute("QUESTION", null);
                session.setAttribute("QUESTION_NUMBER", null);
                session.setAttribute("REMAIN_SECONDS", 0);
                url = "/user/quiz-submit";
            } else {
                Map<String, Integer> studentAnswer = (Map<String, Integer>) session.getAttribute("STUDENT_ANSWER");
                if (answerID == null || answerID.isEmpty()) {
                    studentAnswer.put(questionID, null);
                } else {
                    studentAnswer.put(questionID, Integer.parseInt(answerID));
                }
                List<SearchQuestionDTO> listQuestion = (List<SearchQuestionDTO>) session.getAttribute("QUIZ_QUESTIONS_OBJECT");
                int nextQuestion = Integer.parseInt(questionNumber);
                SearchQuestionDTO nextQuestionObject = listQuestion.get(nextQuestion);
                session.setAttribute("QUESTION", nextQuestionObject);
                session.setAttribute("QUESTION_NUMBER", nextQuestion + 1);
                session.setAttribute("STUDENT_ANSWER", studentAnswer);
                session.setAttribute("REMAIN_SECONDS", remainSeconds);
            }
        } catch (NullPointerException e) {
            logger.error("Error at QuizNextQuestionServlet:" + e.toString());
            message = "Loi null pointer";
            url = "/";
            session.invalidate();
        } catch (NumberFormatException e) {
            logger.error("Error at QuizNextQuestionServlet:" + e.toString());
            message = "Loi khi parse sang so";
            url = "/";
            session.invalidate();
        } catch (Exception e) {
            logger.error("Error at QuizNextQuestionServlet:" + e.toString());
            session.invalidate();
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
