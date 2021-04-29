/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dtos.Food;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import utils.DBUtils;

/**
 *
 * @author dell
 */
public class AdminDAO {

    private Connection conn;
    private PreparedStatement stment;
    private ResultSet rs;
    private final Logger logger = Logger.getLogger(AdminDAO.class);

    public AdminDAO() {
        logger.addAppender(new ConsoleAppender(new PatternLayout("%d{yyyy-MM-dd HH:mm:ss} %p %c{1}: %C %M - %m%n")));
    }

    private void closeConnection() throws SQLException {
        if (conn != null) {
            conn.close();
        }
        if (stment != null) {
            stment.close();
        }
        if (rs != null) {
            rs.close();
        }

    }

    public List<Food> getAllActiveFoods() throws SQLException {
        List<Food> result = new ArrayList();
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT f.id,"
                        + "f.image,"
                        + "f.name,"
                        + "f.price,"
                        + "f.quantity,"
                        + "f.create_date,"
                        + "c.name AS name2 FROM tbl_foods f JOIN tbl_categories c ON c.code = f.category WHERE f.status = 'true'";
                stment = conn.prepareStatement(sql);
                rs = stment.executeQuery();
                while (rs.next()) {
                    Food food = new Food();
                    food.setId(rs.getInt("id"));
                    food.setImage(rs.getString("image"));
                    food.setName(rs.getString("name"));
                    food.setPrice(rs.getInt("price"));
                    food.setQuantity(rs.getInt("quantity"));
                    food.setStatus(true);
                    food.setCategory(rs.getString("name2"));
                    food.setCreateDate(rs.getDate("create_date"));
                    result.add(food);
                }
            }

        } catch (Exception e) {
            logger.error("Error at AdminDAO-getAllActiveFood:" + e.toString());
        } finally {
            closeConnection();
        }
        return result;
    }

    public boolean addFood(Food food) throws SQLException {
        boolean result = false;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "INSERT INTO tbl_foods(name,image,price,category,quantity,status,create_date) VALUES(?,?,?,?,?,?,?)";
                stment = conn.prepareStatement(sql);
                stment.setString(1, food.getName().trim());
                stment.setString(2, food.getImage().trim());
                stment.setInt(3, food.getPrice());
                stment.setString(4, food.getCategory().trim());
                stment.setInt(5, food.getQuantity());
                stment.setBoolean(6, food.isStatus());
                stment.setDate(7, food.getCreateDate());
                int flag = stment.executeUpdate();
                if (flag > 0) {
                    result = true;
                }
            }
        } catch (Exception e) {
            logger.error("Error at AdminDAO-addFood:" + e.toString());
        } finally {
            closeConnection();
        }
        return result;
    }

    public Food getFoodByID(int id) throws SQLException {
        Food result = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT name,image,price,category,quantity,status FROM tbl_foods WHERE id = ?";
                stment = conn.prepareStatement(sql);
                stment.setInt(1, id);
                rs = stment.executeQuery();
                if (rs.next()) {
                    result = new Food();
                    result.setId(id);
                    result.setName(rs.getString("name").trim());
                    result.setImage(rs.getString("image").trim());
                    result.setPrice(rs.getInt("price"));
                    result.setCategory(rs.getString("category").trim());
                    result.setQuantity(rs.getInt("quantity"));
                    result.setStatus(rs.getBoolean("status"));
                }
            }

        } catch (Exception e) {
            logger.error("Error at AdminDAO-getFoodByID:" + e.toString());
        } finally {
            closeConnection();
        }
        return result;
    }

    public boolean updateFood(Food food) throws SQLException {
        boolean result = false;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "UPDATE tbl_foods SET name = ?,image = ?,price = ?,category = ?,quantity = ? WHERE id = ?";
                stment = conn.prepareStatement(sql);
                stment.setString(1, food.getName());
                stment.setString(2, food.getImage());
                stment.setInt(3, food.getPrice());
                stment.setString(4, food.getCategory());
                stment.setInt(5, food.getQuantity());
                stment.setInt(6, food.getId());
                int flag = stment.executeUpdate();
                if (flag > 0) {
                    result = true;
                }
            }

        } catch (Exception e) {
            logger.error("Error at AdminDAO-updateFood:" + e.toString());
        } finally {
            closeConnection();
        }
        return result;
    }

    public boolean deleteFood(int id, String user) throws SQLException {
        boolean result = false;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "UPDATE tbl_foods SET status = 'false',update_date = ?,update_user = ? WHERE id = ?";
                stment = conn.prepareStatement(sql);
                Date now = Date.valueOf(LocalDate.now());
                stment.setDate(1, now);
                stment.setString(2, user);
                stment.setInt(3, id);
                int flag = stment.executeUpdate();
                if (flag > 0) {
                    result = true;
                }
            }

        } catch (Exception e) {
            logger.error("Error at AdminDAO-deleteFood:" + e.toString());
        } finally {
            closeConnection();
        }
        return result;
    }

}
