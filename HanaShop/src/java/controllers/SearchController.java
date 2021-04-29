/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import daos.GeneralDAO;
import dtos.Category;
import dtos.SearchFoodPaginationDTO;
import dtos.User;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
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
@WebServlet(urlPatterns = {"/search"})
public class SearchController extends HttpServlet {

    private final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(SearchController.class);
    private PathConstants path;

    @Override
    public void init() throws ServletException {
        logger.addAppender(new ConsoleAppender(new PatternLayout("%d{yyyy-MM-dd HH:mm:ss} %p %c{1}: %C %M - %m%n")));
        path = new PathConstants(getServletContext());
        try {
            logger.addAppender(new RollingFileAppender(new PatternLayout("%d{yyyy-MM-dd HH:mm:ss} %p %c{1}: %C %M - %m%n"), path.getProjectLogPath()));
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(SearchController.class.getName()).log(Level.SEVERE, null, ex);
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
        String page = request.getParameter("page");
        String message = "";
        String url = "/";
        try {
            if (page == null || page.isEmpty()) {
                page = "0";
            }
            HttpSession session = request.getSession();
            if (session != null) {
                User user = (User) session.getAttribute("USER");
                if (user != null) {
                    switch (user.getRole()) {
                        case "ROLE_USER":
                            url = "/user/home.jsp";
                            GeneralDAO dao = new GeneralDAO();
                            List<Category> listCate = dao.getAllCategories();
                            request.setAttribute("LIST_CATE", listCate);
                            break;
                        case "ROLE_ADMIN":
                            url = "/admin/home.jsp";
                            dao = new GeneralDAO();
                            listCate = dao.getAllCategories();
                            request.setAttribute("LIST_CATE", listCate);
                            break;
                        default:
                            url = "/";
                            break;
                    }
                    GeneralDAO dao = new GeneralDAO();
                    String price = request.getParameter("price");
                    String searchName = request.getParameter("searchName");
                    String category = request.getParameter("category");
                    SearchFoodPaginationDTO searchResult = null;
                    if (price == null) {
                        price = "";
                    }
                    if (searchName == null) {
                        searchName = "";
                    }
                    if (category == null) {
                        category = "";
                    }
                    int pageQuantities = 0;
                    if (!price.isEmpty() || !searchName.isEmpty() || !category.isEmpty()) {
                        String choice = request.getParameter("radioChoice");
                        switch (choice) {
                            case "nameSearch":
                                searchResult = dao.searchFoodWithPagingByName(Integer.parseInt(page.trim()), 20, searchName);
                                searchResult.setRadioChoice(choice);
                                searchResult.setSearchValue(searchName);
                                searchResult.setTypeOfSearch("searchName");
                                searchResult.setPageQuantities(dao.getSizeWhenSearchByName(searchName) / 20);
                                break;
                            case "priceSearch":
                                searchResult = dao.searchFoodWithPagingByPriceRange(Integer.parseInt(page.trim()), 20, Integer.parseInt(price));
                                searchResult.setRadioChoice(choice);
                                searchResult.setSearchValue(price);
                                searchResult.setTypeOfSearch("price");
                                searchResult.setPageQuantities(dao.getSizeWhenSearchByPrice(Integer.parseInt(price)) / 20);
                                break;
                            case "categorySearch":
                                searchResult = dao.searchFoodWithPagingByCategory(Integer.parseInt(page.trim()), 20, category);
                                searchResult.setRadioChoice(choice);
                                searchResult.setSearchValue(category);
                                searchResult.setTypeOfSearch("category");
                                searchResult.setPageQuantities(dao.getSizeWhenSearchByCategory(category) / 20);
                                break;
                            default:
                                break;
                        }
                    } else {
                        searchResult = dao.getFoodWithPaging(Integer.parseInt(page.trim()), 20);
                        searchResult.setRadioChoice(null);
                        searchResult.setSearchValue(null);
                        pageQuantities = dao.getFoodQuantities() / 20;
                        searchResult.setPageQuantities(pageQuantities);
                    }
                    request.setAttribute("SEARCH_OBJECT", searchResult);
                    request.setAttribute("PAGE_QUANTITIES", searchResult.getPageQuantities());
                } else {
                    GeneralDAO dao = new GeneralDAO();
                    String price = request.getParameter("price");
                    String searchName = request.getParameter("searchName");
                    String category = request.getParameter("category");
                    SearchFoodPaginationDTO searchResult = null;
                    if (price == null) {
                        price = "";
                    }
                    if (searchName == null) {
                        searchName = "";
                    }
                    if (category == null) {
                        category = "";
                    }
                    int pageQuantities = 0;
                    if (!price.isEmpty() || !searchName.isEmpty() || !category.isEmpty()) {
                        String choice = request.getParameter("radioChoice");
                        switch (choice) {
                            case "nameSearch":
                                searchResult = dao.searchFoodWithPagingByName(Integer.parseInt(page.trim()), 20, searchName);
                                searchResult.setRadioChoice(choice);
                                searchResult.setSearchValue(searchName);
                                searchResult.setTypeOfSearch("searchName");
                                searchResult.setPageQuantities(dao.getSizeWhenSearchByName(searchName) / 20);
                                break;
                            case "priceSearch":
                                searchResult = dao.searchFoodWithPagingByPriceRange(Integer.parseInt(page.trim()), 20, Integer.parseInt(price));
                                searchResult.setRadioChoice(choice);
                                searchResult.setSearchValue(price);
                                searchResult.setTypeOfSearch("price");
                                searchResult.setPageQuantities(dao.getSizeWhenSearchByPrice(Integer.parseInt(price)) / 20);
                                break;
                            case "categorySearch":
                                searchResult = dao.searchFoodWithPagingByCategory(Integer.parseInt(page.trim()), 20, category);
                                searchResult.setRadioChoice(choice);
                                searchResult.setSearchValue(category);
                                searchResult.setTypeOfSearch("category");
                                searchResult.setPageQuantities(dao.getSizeWhenSearchByCategory(category) / 20);
                                break;
                            default:
                                break;
                        }
                    } else {
                        searchResult = dao.getFoodWithPaging(Integer.parseInt(page.trim()), 20);
                        searchResult.setRadioChoice(null);
                        searchResult.setSearchValue(null);
                        pageQuantities = dao.getFoodQuantities() / 20;
                        searchResult.setPageQuantities(pageQuantities);
                    }
                    request.setAttribute("SEARCH_OBJECT", searchResult);
                    request.setAttribute("PAGE_QUANTITIES", searchResult.getPageQuantities());
                    List<Category> listCate = dao.getAllCategories();
                    request.setAttribute("LIST_CATE", listCate);
                }
            }
        } catch (NumberFormatException ex) {
            message = "Trang phai la so";
            url = "/index.jsp";
            logger.error("Error at SearchController:" + ex.toString());

        } catch (NullPointerException e) {
            url = "/index.jsp";
            message = "Loi null pointer";
            logger.error("Error at SearchController:" + e.toString());
        } catch (SQLException e) {
            url = "/index.jsp";
            message = "Loi server";
            logger.error("Error at SearchController:" + e.toString());
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
