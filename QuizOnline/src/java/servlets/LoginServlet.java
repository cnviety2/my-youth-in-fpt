/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import daos.AdminDAO;
import daos.GeneralDAO;
import daos.UserDAO;
import dtos.Answer;
import dtos.SearchQuestionDTO;
import dtos.Subject;
import dtos.User;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
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
        String url = "";
        HttpSession session = request.getSession();
        if (session != null) {
            User user = (User) session.getAttribute("USER");
            if (user != null) {
                try {
                    String role = user.getRole();
                    switch (role) {
                        case "ROLE_ADMIN":
                            AdminDAO adminDAO = new AdminDAO();
                            List<Subject> listSubjects = adminDAO.getAllSubjects();
                            session.setAttribute("LIST_SUBJECTS", listSubjects);
                            List<SearchQuestionDTO> listQuestion = adminDAO.searchPart1("", true, null, 0);
                            List<String> listQuestionID = new ArrayList<>();
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
                            url = "/admin/home.jsp";
                            break;
                        case "ROLE_STUDENT":
                            url = "/user/home.jsp";
                            break;
                    }
                } catch (SQLException ex) {
                    logger.error("Error at LoginServlet:" + ex.toString());
                }
            } else {
                url = "/index.jsp";
            }
        } else {
            url = "/index.jsp";
        }
        request.getRequestDispatcher(url).forward(request, response);
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
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashPassword = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            String encodedPassword = Base64.getEncoder().encodeToString(hashPassword);
            GeneralDAO dao = new GeneralDAO();
            User user = dao.checkLogin(email, encodedPassword);
            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("USER", user);
                switch (user.getRole()) {
                    case "ROLE_STUDENT":
                        UserDAO userDAO = new UserDAO();
                        List<Subject> listUserSubject = userDAO.getAllSubjects();
                        session.setAttribute("LIST_SUBJECT", listUserSubject);
                        url = "/user/home.jsp";
                        break;
                    case "ROLE_ADMIN":
                        AdminDAO adminDAO = new AdminDAO();
                        List<Subject> listSubjects = adminDAO.getAllSubjects();
                        session.setAttribute("LIST_SUBJECTS", listSubjects);
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
                        url = "/admin/home.jsp";
                        break;
                }
            } else {
                message = "Khong ton tai user";
                url = "/index.jsp";
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
