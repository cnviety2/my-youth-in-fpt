/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets.user;

import dtos.CarInCartDTO;
import java.io.IOException;
import java.util.List;
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
public class UpdateCartServlet extends HttpServlet {

    private PathConstants path;

    private final Logger logger = Logger.getLogger(UpdateCartServlet.class);

    @Override
    public void init() throws ServletException {
        logger.addAppender(new ConsoleAppender(new PatternLayout("%d{yyyy-MM-dd HH:mm:ss} %p %c{1}: %C %M - %m%n")));
        path = new PathConstants(getServletContext());
        try {
            logger.addAppender(new RollingFileAppender(new PatternLayout("%d{yyyy-MM-dd HH:mm:ss} %p %c{1}: %C %M - %m%n"), path.getProjectLogPath()));
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(UpdateCartServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        String id = request.getParameter("id");
        String action = request.getParameter("action");
        String message = "";
        try {
            List<CarInCartDTO> cart = (List<CarInCartDTO>) session.getAttribute("CART");
            switch (action) {
                case "inc":
                    for (CarInCartDTO dto : cart) {
                        if (dto.getId() == Integer.parseInt(id)) {
                            dto.setQuantity(dto.getQuantity() + 1);
                            break;
                        }
                    }
                    Integer total = 0;
                    if (total == null) {
                        total = 0;
                    }
                    for (CarInCartDTO dto2 : cart) {
                        total = total + dto2.getPrice() * dto2.getQuantity();
                    }
                    session.setAttribute("TOTAL", total);
                    session.setAttribute("CART", cart);
                    break;
                case "dec":
                    total = (Integer) session.getAttribute("TOTAL");
                    for (CarInCartDTO dto : cart) {
                        if (dto.getId() == Integer.parseInt(id)) {
                            if (dto.getQuantity() > 0) {
                                dto.setQuantity(dto.getQuantity() - 1);
                                if (total == null) {
                                    total = 0;
                                }
                                total = total - dto.getPrice();
                                break;
                            }
                        }
                    }
                    session.setAttribute("TOTAL", total);
                    session.setAttribute("CART", cart);
                    break;
                default:
                    throw new NullPointerException();
            }

        } catch (NullPointerException e) {
            message = "Loi null pointer";
            logger.error("Error at UpdateCartServlet:" + e.toString());
        } catch (Exception e) {
            logger.error("Error at UpdateCartServlet:" + e.toString());
            message = "Loi khong xac dinh";
        } finally {
            request.setAttribute("MESSAGE", message);
            request.getRequestDispatcher("/user/view-cart.jsp").forward(request, response);
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
