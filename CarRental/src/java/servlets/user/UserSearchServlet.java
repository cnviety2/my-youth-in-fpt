/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets.user;

import daos.SearchDAO;
import dtos.Car;
import java.io.IOException;
import java.io.PrintWriter;
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
public class UserSearchServlet extends HttpServlet {

    private PathConstants path;

    private final Logger logger = Logger.getLogger(UserSearchServlet.class);

    @Override
    public void init() throws ServletException {
        logger.addAppender(new ConsoleAppender(new PatternLayout("%d{yyyy-MM-dd HH:mm:ss} %p %c{1}: %C %M - %m%n")));
        path = new PathConstants(getServletContext());
        try {
            logger.addAppender(new RollingFileAppender(new PatternLayout("%d{yyyy-MM-dd HH:mm:ss} %p %c{1}: %C %M - %m%n"), path.getProjectLogPath()));
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(UserSearchServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        String searchName = request.getParameter("name");
        String searchCategory = request.getParameter("category");
        String searchRentalDate = request.getParameter("rentalDate");
        String searchQuantity = request.getParameter("quantity");
        String pageString = request.getParameter("page");
        String message = "";
        String url = "/user/home.jsp";
        try {
            if (searchName == null) {
                searchName = "";
            }
            Integer rentalDate = null;
            Integer quantity = null;
            if (searchRentalDate != null && !searchRentalDate.isEmpty()) {
                rentalDate = Integer.parseInt(searchRentalDate);
            }
            if (searchQuantity != null && !searchQuantity.isEmpty()) {
                quantity = Integer.parseInt(searchQuantity);
            }
            if (pageString == null || pageString.isEmpty()) {
                pageString = "0";
            }
            SearchDAO searchDAO = new SearchDAO();
            int totalRecords = searchDAO.getAllRecordsOfThatSearch(searchName, searchCategory, rentalDate, quantity);
            if (totalRecords == 0) {
                message = "Khong co ket qua";
            } else {
                int totalPages = (int) Math.ceil(totalRecords / 2);
                if (totalRecords % 2 == 0) {
                    totalPages = (totalRecords / 2) - 1;
                }
                List<Car> listCar = searchDAO.searchCar(searchName, searchCategory, rentalDate, quantity, Integer.parseInt(pageString));
                request.setAttribute("LIST_CAR", listCar);
                request.setAttribute("SEARCH_NAME", searchName);
                request.setAttribute("SEARCH_CATEGORY", searchCategory);
                request.setAttribute("SEARCH_RENTAL_DATE", rentalDate);
                request.setAttribute("SEARCH_QUANTITY", quantity);
                request.setAttribute("TOTAL_PAGES", totalPages);
                request.setAttribute("STANDING_PAGE", Integer.parseInt(pageString));
            }
        } catch (NumberFormatException e) {
            message = "Loi khi chuyen sang so";
            logger.error("Error at SearchCarServlet:" + e.toString());
        } catch (Exception e) {
            message = "Loi khong xac dinh";
            logger.error("Error at SearchCarServlet:" + e.toString());
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
