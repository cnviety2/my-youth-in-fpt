/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.user;

import dtos.FoodInCart;
import java.io.IOException;
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
@WebServlet(urlPatterns = {"/user/cart/update"})
public class UpdateQuantityInCartController extends HttpServlet {

    private final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(UpdateQuantityInCartController.class);
    private PathConstants path;

    @Override
    public void init() throws ServletException {
        logger.addAppender(new ConsoleAppender(new PatternLayout("%d{yyyy-MM-dd HH:mm:ss} %p %c{1}: %C %M - %m%n")));
        path = new PathConstants(getServletContext());
        try {
            logger.addAppender(new RollingFileAppender(new PatternLayout("%d{yyyy-MM-dd HH:mm:ss} %p %c{1}: %C %M - %m%n"), path.getProjectLogPath()));
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(UpdateQuantityInCartController.class.getName()).log(Level.SEVERE, null, ex);
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
        HttpSession session = request.getSession();
        try {
            List<FoodInCart> cart = (List< FoodInCart>) session.getAttribute("CART");
            if (cart == null) {
                throw new NullPointerException();
            } else {
                String action = request.getParameter("action");
                String idString = request.getParameter("id");
                String amountString = request.getParameter("amount");
                int id = Integer.parseInt(idString.trim());
                int amount = Integer.parseInt(amountString.trim());
                for (FoodInCart foodInCart : cart) {
                    if (id == foodInCart.getId()) {
                        switch (action) {
                            case "inc":
                                foodInCart.setAmount(amount + 1);
                                break;
                            case "dec":
                                if (foodInCart.getAmount() > 0) {
                                    foodInCart.setAmount(amount - 1);
                                }
                                break;
                            default:
                                throw new NullPointerException();
                        }
                        break;
                    }
                }
                session.setAttribute("CART", cart);
            }
        } catch (NullPointerException e) {
            logger.error("Error at UserCartController:" + e.toString());
        } catch (Exception e) {
            logger.error("Error at UserCartController:" + e.toString());
        } finally {
            response.sendRedirect("/HanaShop/user/view-cart.jsp");
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
