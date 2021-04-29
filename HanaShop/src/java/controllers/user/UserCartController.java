/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.user;

import daos.UserDAO;
import dtos.FoodInCart;
import dtos.FoodInCartDTO;
import dtos.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
@WebServlet(urlPatterns = {"/user/cart"})
public class UserCartController extends HttpServlet {

    private final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(UserCartController.class);
    private PathConstants path;

    @Override
    public void init() throws ServletException {
        logger.addAppender(new ConsoleAppender(new PatternLayout("%d{yyyy-MM-dd HH:mm:ss} %p %c{1}: %C %M - %m%n")));
        path = new PathConstants(getServletContext());
        try {
            logger.addAppender(new RollingFileAppender(new PatternLayout("%d{yyyy-MM-dd HH:mm:ss} %p %c{1}: %C %M - %m%n"), path.getProjectLogPath()));
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(UserCartController.class.getName()).log(Level.SEVERE, null, ex);
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
        response.sendRedirect("/HanaShop/user/view-cart.jsp");
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
        String id = request.getParameter("id");
        String action = request.getParameter("action");
        String message = "";
        boolean amountFlag = false;
        String url = "/user";
        try {
            switch (action) {
                case "add":
                    HttpSession session = request.getSession();
                    List<FoodInCart> cart = (List<FoodInCart>) session.getAttribute("CART");
                    if (cart == null) {
                        cart = new ArrayList<>();
                        UserDAO dao = new UserDAO();
                        FoodInCartDTO temp = dao.getFoodNameByID(Integer.parseInt(id.trim()));
                        if (temp != null) {
                            cart.add(new FoodInCart(Integer.parseInt(id.trim()), temp.getName(), temp.getPrice(), 1));
                            message = "Them vao gio hang thanh cong";
                            session.setAttribute("CART", cart);
                        } else {
                            throw new NullPointerException();
                        }
                    } else {
                        UserDAO dao = new UserDAO();
                        boolean flag = true;
                        for (FoodInCart foodInCart : cart) {
                            if (Integer.parseInt(id.trim()) == foodInCart.getId()) {
                                foodInCart.setAmount(foodInCart.getAmount() + 1);
                                flag = false;
                                break;
                            }
                        }
                        if (flag) {
                            FoodInCartDTO temp = dao.getFoodNameByID(Integer.parseInt(id.trim()));
                            cart.add(new FoodInCart(Integer.parseInt(id.trim()), temp.getName(), temp.getPrice(), 1));
                        }
                        message = "Them vao gio hang thanh cong";
                        session.setAttribute("CART", cart);
                    }
                    break;
                case "delete":
                    session = request.getSession();
                    cart = (List<FoodInCart>) session.getAttribute("CART");
                    if (cart == null) {
                        throw new NullPointerException();
                    } else {
                        int idDele = Integer.parseInt(request.getParameter("idDele"));
                        for (FoodInCart foodInCart : cart) {
                            if (idDele == foodInCart.getId()) {
                                message = "Xoa thanh cong";
                                url = "/user/view-cart.jsp";
                                cart.remove(foodInCart);
                                break;
                            }
                        }
                    }
                    break;
                case "pay":
                    session = request.getSession();
                    cart = (List<FoodInCart>) session.getAttribute("CART");
                    if (cart == null) {
                        throw new NullPointerException();
                    } else {
                        for(FoodInCart temp : cart){
                            if(temp.getAmount() <= 0){
                                amountFlag = true;
                                throw new NumberFormatException();
                            }
                        }
                        List<Integer> listIDs = new ArrayList<>();
                        for (FoodInCart food : cart) {
                            listIDs.add(food.getId());
                        }
                        UserDAO dao = new UserDAO();
                        Map<Integer, Integer> listQuantities = dao.getFoodQuantitiesByIds(listIDs);
                        List<Integer> outOfStockList = new ArrayList<>();
                        for (FoodInCart food : cart) {
                            if (food.getAmount() > listQuantities.get(food.getId())) {
                                outOfStockList.add(food.getId());
                            }
                        }
                        if (outOfStockList.isEmpty()) {
                            boolean flag = dao.shoppingProcess(cart);
                            if (flag) {
                                message = "Thanh toan thanh cong";
                                int totalPrice = Integer.parseInt(request.getParameter("totalPrice"));
                                User user = (User) session.getAttribute("USER");
                                dao.storePaymentHistoryOfAUser(user, totalPrice, cart);
                                session.setAttribute("CART", null);
                            } else {
                                throw new Exception();
                            }

                        } else {
                            message = "Co " + outOfStockList.size() + " san pham da het hang,xin hay chon lai";
                            url = "/user/view-cart.jsp";
                        }

                    }
                    break;
                default:
                    throw new Exception();
            }

        } catch (NumberFormatException e) {
            message = "Loi khi parse number";
            if(amountFlag){
                message = "So luong phai lon hon 0";
            }
            logger.error("Error at UserCartController:" + e.toString());
        } catch (NullPointerException e) {
            message = "Loi null pointer";
            logger.error("Error at UserCartController:" + e.toString());
        } catch (Exception e) {
            message = "Loi khong xac dinh";
            logger.error("Error at UserCartController:" + e.toString());
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
