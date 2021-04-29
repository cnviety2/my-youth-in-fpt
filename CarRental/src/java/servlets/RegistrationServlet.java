/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import daos.RegistrationDAO;
import daos.SearchDAO;
import dtos.Car;
import dtos.User;
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
import utils.MailUtils;
import utils.PathConstants;

/**
 *
 * @author dell
 */
public class RegistrationServlet extends HttpServlet {

    private PathConstants path;

    private final Logger logger = Logger.getLogger(RegistrationServlet.class);

    @Override
    public void init() throws ServletException {
        logger.addAppender(new ConsoleAppender(new PatternLayout("%d{yyyy-MM-dd HH:mm:ss} %p %c{1}: %C %M - %m%n")));
        path = new PathConstants(getServletContext());
        try {
            logger.addAppender(new RollingFileAppender(new PatternLayout("%d{yyyy-MM-dd HH:mm:ss} %p %c{1}: %C %M - %m%n"), path.getProjectLogPath()));
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(RegistrationServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        String url = "/registration.jsp";
        try {
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String rePassword = request.getParameter("rePassword");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || rePassword.isEmpty()
                    || phone.isEmpty() || address.isEmpty()) {
                throw new NullPointerException();
            } else {
                int phoneNumber = Integer.parseInt(phone);//kiem tra SDT
                if (phone.length() > 10) {
                    throw new NumberFormatException();
                }
                RegistrationDAO dao = new RegistrationDAO();
                if (!password.equals(rePassword)) {
                    message = "Password va password nhap lai khong trung nhau";
                    url = "/registration.jsp";
                } else if (dao.isEmailExist(email)) {
                    message = "Email da trung,hay chon email khac";
                    url = "/registration.jsp";
                } else {
                    User user = new User();
                    user.setEmail(email);
                    user.setName(name);
                    user.setPassword(password);
                    user.setPhone(phone);
                    user.setAddress(address);
                    boolean flag = dao.addAUser(user);
                    if (flag) {
                        message = "Dang ky thanh cong,email xac nhan da duoc gui cho ban";
                        url = "/user/home.jsp";
                        HttpSession session = request.getSession();
                        session.setAttribute("USER", user);
                        SearchDAO searchDAO = new SearchDAO();
                        List<Car> listCar = searchDAO.searchCar("", "", null, null, 0);
                        request.setAttribute("LIST_CAR", listCar);
                        MailUtils.sendConfirmedEmail(user);
                    }
                }
            }
        } catch (NumberFormatException e) {
            message = "So dien thoai phai la so va it hon 10 ky tu";
            logger.error("Error at RegistrationServlet:" + e.toString());
        } catch (NullPointerException e) {
            message = "Khong duoc nhap thieu";
            logger.error("Error at RegistrationServlet:" + e.toString());
        } catch (Exception e) {
            message = "Loi khong xac dinh";
            logger.error("Error at RegistrationServlet:" + e.toString());
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
        response.sendRedirect("/CarRental/registration.jsp");
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
