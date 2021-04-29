/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dtos.Category;
import dtos.Food;
import dtos.SearchFoodPaginationDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class GeneralDAO {

    private Connection conn;
    private PreparedStatement stment;
    private ResultSet rs;
    private final Logger logger = Logger.getLogger(GeneralDAO.class);

    public GeneralDAO() {
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

    public SearchFoodPaginationDTO getFoodWithPaging(int page, int rows) throws SQLException {
        SearchFoodPaginationDTO result = new SearchFoodPaginationDTO();
        result.setPage(page);
        result.setQuantity(rows);
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT f.id,"
                        + "f.image,"
                        + "f.name,"
                        + "f.price,"
                        + "f.quantity,"
                        + "f.create_date,"
                        + "c.name AS name2 FROM tbl_foods f "
                        + "JOIN tbl_categories c ON c.code = f.category "
                        + "WHERE f.status = 'true' AND f.quantity > 0 ORDER BY create_date ASC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
                stment = conn.prepareStatement(sql);
                stment.setInt(1, page * rows);
                stment.setInt(2, rows);
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
                    result.getListFood().add(food);
                }
            }

        } catch (Exception e) {
            logger.error("Error at GeneralDAO-getFoodWithPaging:" + e.toString());
        } finally {
            closeConnection();
        }
        return result;
    }

    public int getFoodQuantities() throws SQLException {
        int result = 0;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT id FROM tbl_foods";
                stment = conn.prepareCall(sql);
                rs = stment.executeQuery();
                while (rs.next()) {
                    result += 1;
                }
            }

        } catch (Exception e) {
            logger.error("Error at GeneralDAO-getFoodQuantities:" + e.toString());
        } finally {
            closeConnection();
        }
        return result;
    }

    public List<Category> getAllCategories() throws SQLException {
        List<Category> result = new ArrayList<>();
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT id,name,code FROM tbl_categories";
                stment = conn.prepareStatement(sql);
                rs = stment.executeQuery();
                while (rs.next()) {
                    Category cate = new Category();
                    cate.setId(rs.getInt("id"));
                    cate.setCode(rs.getString("code").trim());
                    cate.setName(rs.getString("name").trim());
                    result.add(cate);
                }
            }

        } catch (Exception e) {
            logger.error("Error at GeneralDAO-getAllCategories:" + e.toString());
        } finally {
            closeConnection();
        }
        return result;
    }

    public SearchFoodPaginationDTO searchFoodWithPagingByName(int page, int rows, String nameSearch) throws SQLException {
        SearchFoodPaginationDTO result = new SearchFoodPaginationDTO();
        result.setPage(page);
        result.setQuantity(rows);
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT f.id,"
                        + "f.image,"
                        + "f.name,"
                        + "f.price,"
                        + "f.quantity,"
                        + "f.create_date,"
                        + "c.name AS name2 FROM tbl_foods f "
                        + "JOIN tbl_categories c ON c.code = f.category "
                        + "WHERE f.status = 'true' AND f.quantity > 0 AND f.name LIKE ? ORDER BY create_date ASC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
                stment = conn.prepareStatement(sql);
                stment.setString(1, "%" + nameSearch + "%");
                stment.setInt(2, page * rows);
                stment.setInt(3, rows);
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
                    result.getListFood().add(food);
                }
            }

        } catch (Exception e) {
            logger.error("Error at GeneralDAO-searchFoodWithPagingByName:" + e.toString());
        } finally {
            closeConnection();
        }
        return result;
    }

    public SearchFoodPaginationDTO searchFoodWithPagingByPriceRange(int page, int rows, int maxPrice) throws SQLException {
        SearchFoodPaginationDTO result = new SearchFoodPaginationDTO();
        result.setPage(page);
        result.setQuantity(rows);
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT f.id,"
                        + "f.image,"
                        + "f.name,"
                        + "f.price,"
                        + "f.quantity,"
                        + "f.create_date,"
                        + "c.name AS name2 FROM tbl_foods f "
                        + "JOIN tbl_categories c ON c.code = f.category "
                        + "WHERE f.status = 'true' AND f.quantity > 0 AND f.price <= ? ORDER BY create_date ASC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
                stment = conn.prepareStatement(sql);
                stment.setInt(1, maxPrice);
                stment.setInt(2, page * rows);
                stment.setInt(3, rows);
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
                    result.getListFood().add(food);
                }
            }

        } catch (Exception e) {
            logger.error("Error at GeneralDAO-searchFoodWithPagingByPriceRange:" + e.toString());
        } finally {
            closeConnection();
        }
        return result;
    }

    public SearchFoodPaginationDTO searchFoodWithPagingByCategory(int page, int rows, String category) throws SQLException {
        SearchFoodPaginationDTO result = new SearchFoodPaginationDTO();
        result.setPage(page);
        result.setQuantity(rows);
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT f.id,"
                        + "f.image,"
                        + "f.name,"
                        + "f.price,"
                        + "f.quantity,"
                        + "f.create_date,"
                        + "c.name AS name2 FROM tbl_foods f "
                        + "JOIN tbl_categories c ON c.code = f.category "
                        + "WHERE f.status = 'true' AND f.quantity > 0 AND f.category = ? ORDER BY create_date ASC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
                stment = conn.prepareStatement(sql);
                stment.setString(1, category);
                stment.setInt(2, page * rows);
                stment.setInt(3, rows);
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
                    result.getListFood().add(food);
                }
            }

        } catch (Exception e) {
            logger.error("Error at GeneralDAO-searchFoodWithPagingByCategory:" + e.toString());
        } finally {
            closeConnection();
        }
        return result;
    }

    public int getSizeWhenSearchByName(String nameSearch) throws SQLException {
        int result = 0;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT f.id,"
                        + "f.image,"
                        + "f.name,"
                        + "f.price,"
                        + "f.quantity,"
                        + "f.create_date,"
                        + "c.name AS name2 FROM tbl_foods f "
                        + "JOIN tbl_categories c ON c.code = f.category "
                        + "WHERE f.status = 'true' AND f.quantity > 0 AND f.name LIKE ?";
                stment = conn.prepareStatement(sql);
                stment.setString(1, "%" + nameSearch + "%");
                rs = stment.executeQuery();
                while (rs.next()) {
                    result += 1;
                }
            }

        } catch (Exception e) {
            logger.error("Error at GeneralDAO-getSizeWhenSearchByName:" + e.toString());
        } finally {
            closeConnection();
        }
        return result;
    }

    public int getSizeWhenSearchByPrice(int price) throws SQLException {
        int result = 0;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT f.id,"
                        + "f.image,"
                        + "f.name,"
                        + "f.price,"
                        + "f.quantity,"
                        + "f.create_date,"
                        + "c.name AS name2 FROM tbl_foods f "
                        + "JOIN tbl_categories c ON c.code = f.category "
                        + "WHERE f.status = 'true' AND f.quantity > 0 AND f.price <= ?";
                stment = conn.prepareStatement(sql);
                stment.setInt(1, price);
                rs = stment.executeQuery();
                while (rs.next()) {
                    result += 1;
                }
            }
        } catch (Exception e) {
            logger.error("Error at GeneralDAO-getSizeWhenSearchByPrice:" + e.toString());
        } finally {
            closeConnection();
        }
        return result;
    }

    public int getSizeWhenSearchByCategory(String category) throws SQLException {
        int result = 0;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT f.id,"
                        + "f.image,"
                        + "f.name,"
                        + "f.price,"
                        + "f.quantity,"
                        + "f.create_date,"
                        + "c.name AS name2 FROM tbl_foods f "
                        + "JOIN tbl_categories c ON c.code = f.category "
                        + "WHERE f.status = 'true' AND f.quantity > 0 AND f.category = ?";
                stment = conn.prepareStatement(sql);
                stment.setString(1, category);
                rs = stment.executeQuery();
                while (rs.next()) {
                    result += 1;
                }
            }
        } catch (Exception e) {
            logger.error("Error at GeneralDAO-getSizeWhenSearchByCategory:" + e.toString());
        } finally {
            closeConnection();
        }
        return result;
    }

}
