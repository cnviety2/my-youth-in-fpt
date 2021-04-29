/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets.student;

import daos.UserDAO;
import dtos.SearchQuestionDTO;
import java.io.IOException;
import java.util.HashMap;
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
public class GenerateQuizServlet extends HttpServlet {

    private PathConstants path;

    private final Logger logger = Logger.getLogger(GenerateQuizServlet.class);

    @Override
    public void init() throws ServletException {
        logger.addAppender(new ConsoleAppender(new PatternLayout("%d{yyyy-MM-dd HH:mm:ss} %p %c{1}: %C %M - %m%n")));
        path = new PathConstants(getServletContext());
        try {
            logger.addAppender(new RollingFileAppender(new PatternLayout("%d{yyyy-MM-dd HH:mm:ss} %p %c{1}: %C %M - %m%n"), path.getProjectLogPath()));
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(GenerateQuizServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        String url = "/user/quiz.jsp";
        String message = "";
        List<SearchQuestionDTO> listQuestion = null;
        try {
            HttpSession session = request.getSession();
            Boolean takingQuizFlag = (Boolean) session.getAttribute("TAKING_QUIZ_FLAG");
            if (takingQuizFlag != null) {
                if (takingQuizFlag) {
                    message = "Ban dang lam bai quiz";
                    throw new Exception();
                }
            }
            int subjectID = Integer.parseInt(request.getParameter("quizSubject"));
            UserDAO dao = new UserDAO();

            listQuestion = dao.getQuestionsForQuiz(5, subjectID);
            if (listQuestion == null || listQuestion.isEmpty()) {
                message = "Khong co cau hoi";
                throw new NullPointerException();
            }
            if (subjectID == 1) {
                session.setAttribute("REMAIN_SECONDS", 20);
            } else {
                session.setAttribute("REMAIN_SECONDS", 30);
            }
            session.setAttribute("QUIZ_QUESTIONS_OBJECT", listQuestion);
            session.setAttribute("QUESTION", listQuestion.get(0));
            session.setAttribute("QUESTION_NUMBER", 1);
            session.setAttribute("QUIZ_TOTAL_QUESTIONS", 5);
            Map<String, Integer> studentAnswer = new HashMap<>();
            session.setAttribute("STUDENT_ANSWER", studentAnswer);
            session.setAttribute("TAKING_QUIZ_FLAG", true);
            session.setAttribute("QUIZ_SUBJECT", subjectID);
        } catch (NullPointerException e) {
            logger.error("Error at GenerateQuizServlet:" + e.toString());
            url = "/user/home.jsp";

        } catch (Exception ex) {
            logger.error("Error at GenerateQuizServlet:" + ex.toString());
            if (message.isEmpty()) {
                message = "Loi khong xac dinh";
            }
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
