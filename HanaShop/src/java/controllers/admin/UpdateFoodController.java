/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.admin;

import daos.AdminDAO;
import daos.GeneralDAO;
import dtos.Category;
import dtos.Food;
import dtos.User;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;
import web_constants.PathConstants;

/**
 *
 * @author dell
 */
@WebServlet(urlPatterns = {"/admin/update"})
@MultipartConfig
public class UpdateFoodController extends HttpServlet {

    private final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(UpdateFoodController.class);
    private PathConstants path;

    @Override
    public void init() throws ServletException {
        logger.addAppender(new ConsoleAppender(new PatternLayout("%d{yyyy-MM-dd HH:mm:ss} %p %c{1}: %C %M - %m%n")));
        path = new PathConstants(getServletContext());
        try {
            logger.addAppender(new RollingFileAppender(new PatternLayout("%d{yyyy-MM-dd HH:mm:ss} %p %c{1}: %C %M - %m%n"), path.getProjectLogPath()));
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(UpdateFoodController.class.getName()).log(Level.SEVERE, null, ex);
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
        String idString = request.getParameter("id");
        String message = "";
        String url = "/";
        try {
            if (idString == null || idString.isEmpty()) {
                throw new NullPointerException();
            }
            int id = Integer.parseInt(idString.trim());
            AdminDAO dao = new AdminDAO();
            GeneralDAO generalDAO = new GeneralDAO();
            Food food = dao.getFoodByID(id);
            if (food != null) {
                url = "/admin/update.jsp";
                List<Category> listCate = generalDAO.getAllCategories();
                request.setAttribute("LIST_CATE", listCate);
                request.setAttribute("FOOD", food);
            } else {
                throw new NullPointerException();
            }
        } catch (NullPointerException e) {
            message = "Loi null pointer";
            url = "/admin";
            logger.error("Error at UpdateFoodController:" + e.toString());
        } catch (NumberFormatException e) {
            message = "ID phai la so";
            url = "/admin";
            logger.error("Error at UpdateFoodController:" + e.toString());
        } catch (Exception e) {
            logger.error("Error at UpdateFoodController:" + e.toString());
        } finally {
            request.setAttribute("MESSAGE", message);
            request.getRequestDispatcher(url).forward(request, response);
        }
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
        String message = "";
        String url = "/admin";
        try {
            String idString = request.getParameter("id");
            String name = request.getParameter("name");
            String priceString = request.getParameter("price");
            String category = request.getParameter("category");
            String quantityString = request.getParameter("quantity");
            String image = saveImage("image", request);
            if (idString.isEmpty() || name.isEmpty() || priceString.isEmpty() || category.isEmpty()
                    || quantityString.isEmpty() || image.isEmpty()) {
                throw new NullPointerException();
            } else {
                int id = Integer.parseInt(idString);
                int price = Integer.parseInt(priceString);
                int quantity = Integer.parseInt(quantityString);
                if (price < 0 || price > 20000) {
                    message = "Gia tien khong qua 20000 hoac nho hon 0";
                    throw new Exception();
                }
                if(quantity < 0){
                    message = "So luong phai len hon hoac bang 0";
                    throw new Exception();
                }
                AdminDAO dao = new AdminDAO();
                Food food = new Food();
                food.setId(id);
                food.setCategory(category);
                food.setImage(image);
                food.setName(name);
                food.setPrice(price);
                food.setQuantity(quantity);
                Date now = Date.valueOf(LocalDate.now());
                food.setUpdateDate(now);
                HttpSession session = request.getSession();
                String userID = ((User) session.getAttribute("USER")).getUserID();
                food.setUpdateUser(userID.trim());
                boolean flag = dao.updateFood(food);
                if (flag) {
                    message = "Update thanh cong";
                    url = "/admin";
                } else {
                    message = "Update chua thanh cong";
                    url = "/admin";
                }
            }
        } catch (NumberFormatException e) {
            message = "Loi khi chuyen sang dang so";
            url = "/admin";
            logger.error("Error at UpdateFoodController:" + e.toString());
        } catch (NullPointerException e) {
            message = "Loi null pointer";
            url = "/admin";
            logger.error("Error at UpdateFoodController:" + e.toString());
        } catch (SQLException ex) {
            logger.error("Error at UpdateFoodController:" + ex.toString());
        } catch (Exception e) {
            logger.error("Error at UpdateFoodController:" + e.toString());
        } finally {
            request.setAttribute("MESSAGE", message);
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    private String saveImage(String parameterName, HttpServletRequest request) throws IOException, ServletException, SQLException {
        Part filePart = request.getPart(parameterName);
        String content = filePart.getContentType();
        File uploads = new File(path.getProjectPublicImgDirPath());//biến file này chỉ để lưu đường dẫn đến thư mục sẽ lưu ảnh
        if (content.equals("application/octet-stream")) {
            return "/images/no-image.png";
        }
        String fileName = UUID.randomUUID().toString() + ".png";
        File file = new File(uploads, fileName);//tạo 1 file mới ở trong đường dẫn của đã lưu,file này vẫn đang trống
        try (InputStream input = filePart.getInputStream()) {
            Files.copy(input, file.toPath());//đọc từ filePart lúc nãy đã lấy từ request lưu vào file đã tạo trước,kết thúc lệnh này sẽ có ảnh trong thư mục lưu ảnh
        } catch (Exception e) {
            logger.error("Error at UpdatePhoneController-saveImage:" + e.toString());
        }
        return "/images/" + fileName;
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
