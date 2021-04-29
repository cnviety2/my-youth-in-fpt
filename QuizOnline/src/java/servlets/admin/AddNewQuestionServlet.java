/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets.admin;

import daos.AdminDAO;
import dtos.Answer;
import dtos.Question;
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
public class AddNewQuestionServlet extends HttpServlet {

    private PathConstants path;

    private final Logger logger = Logger.getLogger(AddNewQuestionServlet.class);

    @Override
    public void init() throws ServletException {
        logger.addAppender(new ConsoleAppender(new PatternLayout("%d{yyyy-MM-dd HH:mm:ss} %p %c{1}: %C %M - %m%n")));
        path = new PathConstants(getServletContext());
        try {
            logger.addAppender(new RollingFileAppender(new PatternLayout("%d{yyyy-MM-dd HH:mm:ss} %p %c{1}: %C %M - %m%n"), path.getProjectLogPath()));
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(AddNewQuestionServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        String url = "/admin/home.jsp";
        switch (method) {
            case "GET":
                url = "/QuizOnline/admin/home.jsp";
                response.sendRedirect(url);
                break;
            case "POST":
                String questionContent = request.getParameter("questionContent");
                String answer1Content = request.getParameter("answer1");
                String answer2Content = request.getParameter("answer2");
                String answer3Content = request.getParameter("answer3");
                String answer4Content = request.getParameter("answer4");
                String correctAnswer = request.getParameter("correctAnswer");
                String subjectID = request.getParameter("subject");
                try {
                    if (questionContent.isEmpty() || answer1Content.isEmpty() || answer2Content.isEmpty()
                            || answer3Content.isEmpty() || answer4Content.isEmpty() || correctAnswer.isEmpty() || subjectID.isEmpty()) {
                        throw new NullPointerException();
                    } else {
                        Question question = new Question(questionContent, Integer.parseInt(subjectID));
                        Answer answer1 = new Answer(answer1Content, false);
                        Answer answer2 = new Answer(answer2Content, false);
                        Answer answer3 = new Answer(answer3Content, false);
                        Answer answer4 = new Answer(answer4Content, false);
                        switch (correctAnswer) {
                            case "answer1":
                                answer1.setIsCorrect(true);
                                break;
                            case "answer2":
                                answer2.setIsCorrect(true);
                                break;
                            case "answer3":
                                answer3.setIsCorrect(true);
                                break;
                            case "answer4":
                                answer4.setIsCorrect(true);
                                break;
                            default:
                                throw new NullPointerException();
                        }
                        List<Answer> listAnswer = new ArrayList<>(4);
                        listAnswer.add(answer1);
                        listAnswer.add(answer2);
                        listAnswer.add(answer3);
                        listAnswer.add(answer4);
                        AdminDAO dao = new AdminDAO();
                        boolean flag = dao.createNewQuestion(question, listAnswer);
                        if (flag) {
                            message = "Them moi thanh cong";
                        } else {
                            message = "Them moi chua thanh cong,hay thu lai";
                        }
                        List<SearchQuestionDTO> listQuestion = dao.searchPart1("", true, null, 0);
                        List<String> listQuestionID = new ArrayList<>();
                        for (SearchQuestionDTO dto : listQuestion) {
                            listQuestionID.add(dto.getQuestion().getId());
                        }
                        List<Answer> listAnswerFromDB = dao.searchPart2(listQuestionID);
                        for (SearchQuestionDTO dto : listQuestion) {
                            for (Answer answer : listAnswerFromDB) {
                                if (dto.getQuestion().getId().equals(answer.getQuestionID())) {
                                    dto.getListAnswers().add(answer);
                                }
                            }
                        }
                        request.setAttribute("SEARCH_RESULT", listQuestion);
                    }

                } catch (NumberFormatException e) {
                    message = "Loi khi parse sang so";
                    logger.error("Error at AddNewQuestionServlet:" + e.toString());
                } catch (NullPointerException e) {
                    message = "Khong duoc nhap thieu";
                    logger.error("Error at AddNewQuestionServlet:" + e.toString());
                } catch (Exception e) {
                    message = "Loi khong xac dinh";
                    logger.error("Error at AddNewQuestionServlet:" + e.toString());
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
