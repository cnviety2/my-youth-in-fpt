/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets.user;

import daos.PayDAO;
import daos.SearchDAO;
import dtos.CarInCartDTO;
import dtos.User;
import java.io.IOException;
import java.util.ArrayList;
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
import org.apache.log4j.Logger;
import utils.PathConstants;

/**
 *
 * @author dell
 */
@WebServlet(urlPatterns = {"/user/cart"})
public class UserCartServlet extends HttpServlet {

    private PathConstants path;

    private final Logger logger = Logger.getLogger(UserCartServlet.class);

    @Override
    public void init() throws ServletException {
        logger.addAppender(new ConsoleAppender(new PatternLayout("%d{yyyy-MM-dd HH:mm:ss} %p %c{1}: %C %M - %m%n")));
        path = new PathConstants(getServletContext());
        try {
            logger.addAppender(new RollingFileAppender(new PatternLayout("%d{yyyy-MM-dd HH:mm:ss} %p %c{1}: %C %M - %m%n"), path.getProjectLogPath()));
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(UserCartServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        response.sendRedirect("/CarRental/user/view-cart.jsp");
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
        String url = "/user";
        try {
            SearchDAO searchDAO = new SearchDAO();
            switch (action) {
                case "add":
                    HttpSession session = request.getSession();
                    List<CarInCartDTO> cart = (List<CarInCartDTO>) session.getAttribute("CART");
                    if (cart != null) {
                        boolean flag = false;
                        for (CarInCartDTO temp : cart) {
                            if (temp.getId() == Integer.parseInt(id)) {
                                temp.setQuantity(temp.getQuantity() + 1);
                                flag = true;
                                break;
                            }
                        }
                        if (!flag) {
                            CarInCartDTO dto = searchDAO.getCarById(Integer.parseInt(id));
                            if (dto != null) {
                                dto.setQuantity(1);
                                cart.add(dto);
                            } else {
                                throw new NullPointerException();
                            }
                        }
                        Integer total = 0;
                        for (CarInCartDTO dto : cart) {
                            total = total + dto.getPrice() * dto.getQuantity();
                        }
                        message = "Them vao gio hang thanh cong";
                        session.setAttribute("CART", cart);
                        session.setAttribute("TOTAL", total);
                        session.setAttribute("CART_QUANTITY", cart.size());
                    } else {
                        cart = new ArrayList<>();
                        CarInCartDTO dto = searchDAO.getCarById(Integer.parseInt(id));
                        if (dto != null) {
                            dto.setQuantity(1);
                            cart.add(dto);
                            Integer total = 0;
                            for (CarInCartDTO dto2 : cart) {
                                total = total + dto2.getPrice() * dto2.getQuantity();
                            }
                            message = "Them vao gio hang thanh cong";
                            session.setAttribute("CART", cart);
                            session.setAttribute("TOTAL", total);
                            session.setAttribute("CART_QUANTITY", cart.size());
                        } else {
                            throw new NullPointerException();
                        }
                    }
                    break;
                case "delete":
                    session = request.getSession();
                    cart = (List<CarInCartDTO>) session.getAttribute("CART");
                    if (cart == null) {
                        throw new NullPointerException();
                    } else {
                        int idDele = Integer.parseInt(request.getParameter("id"));
                        for (CarInCartDTO dto : cart) {
                            if (idDele == dto.getId()) {
                                message = "Xoa thanh cong";
                                url = "/user/view-cart.jsp";
                                cart.remove(dto);
                                break;
                            }
                        }
                        Integer total = 0;
                        for (CarInCartDTO dto2 : cart) {
                            total = total + dto2.getPrice() * dto2.getQuantity();
                        }
                        session.setAttribute("TOTAL", total);
                        session.setAttribute("CART_QUANTITY", cart.size());
                    }
                    break;
                case "pay":
                    session = request.getSession();
                    cart = (List<CarInCartDTO>) session.getAttribute("CART");
                    if (cart == null) {
                        throw new NullPointerException();
                    }
                    PayDAO payDAO = new PayDAO();
                    List<String> listOutOfStock = payDAO.checkQuantityInDB(cart);
                    if (listOutOfStock != null && !listOutOfStock.isEmpty()) {
                        message = "Thanh toan khong thanh cong:";
                        for (String carName : listOutOfStock) {
                            message = message.concat(carName + ",");
                        }
                        message = message.concat("da het,xin hay chon lai");
                    } else {
                        //kiem tra khong con xe nao bi out of stock,thuc hien pay action
                        String email = ((User) session.getAttribute("USER")).getEmail();
                        int total = (Integer) session.getAttribute("TOTAL");
                        boolean flag = payDAO.payProcessing(email, cart);
                        if (flag) {
                            message = "Thanh toan thanh cong,don hang cua ban da duoc luu lai";
                            payDAO.saveToHistory(email, cart, total);
                            session.removeAttribute("DISCOUNT_FLAG");
                            session.removeAttribute("CART");
                            session.removeAttribute("TOTAL");
                            session.removeAttribute("CART_QUANTITY");
                        } else {
                            message = "Thanh toan chua thanh cong";
                        }
                    }
                    break;
                default:
                    throw new Exception();
            }

        } catch (NumberFormatException e) {
            message = "Loi khi parse number";
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
