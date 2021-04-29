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
public class UpdateQuestionServlet extends HttpServlet {

    private PathConstants path;

    private final Logger logger = Logger.getLogger(UpdateQuestionServlet.class);

    @Override
    public void init() throws ServletException {
        logger.addAppender(new ConsoleAppender(new PatternLayout("%d{yyyy-MM-dd HH:mm:ss} %p %c{1}: %C %M - %m%n")));
        path = new PathConstants(getServletContext());
        try {
            logger.addAppender(new RollingFileAppender(new PatternLayout("%d{yyyy-MM-dd HH:mm:ss} %p %c{1}: %C %M - %m%n"), path.getProjectLogPath()));
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(UpdateQuestionServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        String url = "/admin/home.jsp";
        try {
            String questionContent = request.getParameter("questionContentUpdate");
            String answer1Content = request.getParameter("answer1Update");
            String answer2Content = request.getParameter("answer2Update");
            String answer3Content = request.getParameter("answer3Update");
            String answer4Content = request.getParameter("answer4Update");
            String correctAnswer = request.getParameter("correctAnswerUpdate");
            String subjectID = request.getParameter("subjectUpdate");
            String questionIDUpdate = request.getParameter("questionIDUpdate");
            String answerID1Update = request.getParameter("answerID1Update");
            String answerID2Update = request.getParameter("answerID2Update");
            String answerID3Update = request.getParameter("answerID3Update");
            String answerID4Update = request.getParameter("answerID4Update");
            if (questionContent.isEmpty() || answer1Content.isEmpty() || answer2Content.isEmpty()
                    || answer3Content.isEmpty() || answer4Content.isEmpty()
                    || correctAnswer.isEmpty() || subjectID.isEmpty()
                    || questionIDUpdate.isEmpty() || answerID1Update.isEmpty()
                    || answerID2Update.isEmpty() || answerID3Update.isEmpty() || answerID4Update.isEmpty()) {
                throw new NullPointerException();
            } else {
                Question question = new Question(questionContent, Integer.parseInt(subjectID));
                question.setId(questionIDUpdate);
                Answer answer1 = new Answer(answer1Content, false);
                answer1.setId(Integer.parseInt(answerID1Update));
                Answer answer2 = new Answer(answer2Content, false);
                answer2.setId(Integer.parseInt(answerID2Update));
                Answer answer3 = new Answer(answer3Content, false);
                answer3.setId(Integer.parseInt(answerID3Update));
                Answer answer4 = new Answer(answer4Content, false);
                answer4.setId(Integer.parseInt(answerID4Update));
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
                boolean flag = dao.updateQuestion(question, listAnswer);
                if (flag) {
                    message = "Cap nhat thanh cong";
                } else {
                    message = "Cap nhat chua thanh cong,hay thu lai";
                }
                List<SearchQuestionDTO> listQuestion = dao.searchPart1("", true, null, 0);
                int totalRecords = dao.getAllRecordsOfSearchResult("", true, null);
                int totalPages = (int) Math.ceil(totalRecords / 5);
                if (totalRecords % 5 == 0) {
                    totalPages = (totalRecords / 5) - 1;
                }
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
                request.setAttribute("SEARCH_STATUS", true);
                request.setAttribute("SEARCH_CONTENT", "");
                request.setAttribute("SEARCH_SUBJECT", "none");
                request.setAttribute("TOTAL_PAGES", totalPages);
                request.setAttribute("STANDING_PAGE", 0);
            }

        } catch (NumberFormatException e) {
            message = "Loi khi parse sang so";
            logger.error("Error at UpdateQuestionServlet:" + e.toString());
        } catch (NullPointerException e) {
            message = "Khong duoc nhap thieu";
            logger.error("Error at UpdateQuestionServlet:" + e.toString());
        } catch (Exception e) {
            logger.error("Error at UpdateQuestionServlet:" + e.toString());
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
