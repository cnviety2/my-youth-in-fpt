/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.admin;

import daos.AdminDAO;
import dtos.Food;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.UUID;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;
import web_constants.PathConstants;

/**
 *
 * @author dell
 */
@WebServlet(urlPatterns = {"/admin/add"})
@MultipartConfig
public class AddNewFoodController extends HttpServlet {

    private final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(AddNewFoodController.class);
    private PathConstants path;

    @Override
    public void init() throws ServletException {
        logger.addAppender(new ConsoleAppender(new PatternLayout("%d{yyyy-MM-dd HH:mm:ss} %p %c{1}: %C %M - %m%n")));
        path = new PathConstants(getServletContext());
        try {
            logger.addAppender(new RollingFileAppender(new PatternLayout("%d{yyyy-MM-dd HH:mm:ss} %p %c{1}: %C %M - %m%n"), path.getProjectLogPath()));
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(AddNewFoodController.class.getName()).log(Level.SEVERE, null, ex);
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
        String name = request.getParameter("name").trim();
        String txtPrice = request.getParameter("price").trim();
        String category = request.getParameter("category");
        String message = "OK,add thanh cong,so luong them vao la 500";
        try {
            String image = saveImage("image", request);
            if (name.isEmpty() || txtPrice.isEmpty() || category.isEmpty()) {
                message = "Du lieu khong duoc empty";
            } else {
                int price = Integer.parseInt(txtPrice);
                if (price < 0 || price > 20000) {
                    message = "Gia tien khong qua 20000 hoac nho hon 0";
                    throw new Exception();
                }
                Date now = Date.valueOf(LocalDate.now());
                Food food = new Food();
                food.setCategory(category);
                food.setCreateDate(now);
                food.setImage(image);
                food.setName(name);
                food.setPrice(price);
                food.setQuantity(500);
                food.setStatus(true);
                AdminDAO dao = new AdminDAO();
                boolean flag = dao.addFood(food);
                if (!flag) {
                    message = "Add chua thanh cong";
                }
            }
        } catch (SQLException ex) {
            logger.error("Error at AddNewFoodController:" + ex.toString());
        } catch (NumberFormatException e) {
            message = "Gia phai la so";
            logger.error("Error at AddNewFoodController:" + e.toString());
        } catch (Exception e) {
            logger.error("Error at AddNewFoodController:" + e.toString());
        } finally {
            request.setAttribute("MESSAGE", message);
            request.getRequestDispatcher("/admin").forward(request, response);
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
