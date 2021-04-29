/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dtos.FoodInCart;
import dtos.FoodInCartDTO;
import dtos.HistoryFoodInPaymentDTO;
import dtos.HistoryPaymentDTO;
import dtos.User;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import utils.DBUtils;

/**
 *
 * @author dell
 */
public class UserDAO {

    private Connection conn;
    private PreparedStatement stment;
    private ResultSet rs;
    private final Logger logger = Logger.getLogger(AdminDAO.class);

    public UserDAO() {
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

    public FoodInCartDTO getFoodNameByID(int id) throws SQLException {
        FoodInCartDTO result = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT name,price FROM tbl_foods WHERE id = ?";
                stment = conn.prepareStatement(sql);
                stment.setInt(1, id);
                rs = stment.executeQuery();
                if (rs.next()) {
                    result = new FoodInCartDTO(rs.getInt("price"), rs.getString("name"));
                }
            }
        } catch (Exception e) {
            logger.error("Error at UserDAO-getFoodNameByID:" + e.toString());
        } finally {
            closeConnection();
        }
        return result;
    }

    public Map getFoodQuantitiesByIds(List<Integer> list) throws SQLException {
        Map<Integer, Integer> result = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                result = new HashMap<>();
                for (Integer id : list) {
                    String sql = "SELECT quantity FROM tbl_foods WHERE id = ?";
                    stment = conn.prepareStatement(sql);
                    stment.setInt(1, id);
                    rs = stment.executeQuery();
                    if (rs.next()) {
                        result.put(id, rs.getInt("quantity"));
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Error at UserDAO-getFoodNameByID:" + e.toString());
        } finally {
            closeConnection();
        }
        return result;
    }

    public boolean shoppingProcess(List<FoodInCart> listOfFoodInCart) throws SQLException {
        boolean result = false;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                Map<Integer, Integer> dataFromDB = new HashMap<>();
                for (FoodInCart food : listOfFoodInCart) {
                    String sql = "SELECT quantity FROM tbl_foods WHERE id = ?";
                    stment = conn.prepareStatement(sql);
                    stment.setInt(1, food.getId());
                    rs = stment.executeQuery();
                    if (rs.next()) {
                        dataFromDB.put(food.getId(), rs.getInt("quantity"));
                    }
                }
                Map<Integer, Integer> dataUpdateToDB = new HashMap<>(dataFromDB.size());
                for (FoodInCart food : listOfFoodInCart) {
                    int quantityInDB = dataFromDB.get(food.getId());
                    int quantityAfter = quantityInDB - food.getAmount();
                    dataUpdateToDB.put(food.getId(), quantityAfter);
                }
                for (Map.Entry<Integer, Integer> entry : dataUpdateToDB.entrySet()) {
                    String sql = "UPDATE tbl_foods SET quantity = ? WHERE id =?";
                    stment = conn.prepareStatement(sql);
                    stment.setInt(1, entry.getValue());
                    stment.setInt(2, entry.getKey());
                    stment.executeUpdate();
                }
                result = true;
            }
        } catch (Exception e) {
            result = false;
            logger.error("Error at UserDAO-shoppingProcess:" + e.toString());
        } finally {
            closeConnection();
        }
        return result;
    }

    public boolean storePaymentHistoryOfAUser(User user, int totalPrice, List<FoodInCart> cart) throws SQLException {
        boolean result = false;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                Date now = Date.valueOf(LocalDate.now());
                String sql = "INSERT INTO tbl_payments(payment_date,user_id,total_price,id) VALUES(?,?,?,?)";
                stment = conn.prepareStatement(sql);
                stment.setDate(1, now);
                stment.setString(2, user.getUserID());
                stment.setInt(3, totalPrice);
                String randomKey = UUID.randomUUID().toString();
                stment.setString(4, randomKey);
                stment.executeUpdate();
                for (FoodInCart food : cart) {
                    sql = "INSERT INTO tbl_foods_in_a_payment(payment_id,food_id,food_price,quantity) VALUES(?,?,?,?)";
                    stment = conn.prepareStatement(sql);
                    stment.setString(1, randomKey);
                    stment.setInt(2, food.getId());
                    stment.setInt(3, food.getPrice());
                    stment.setInt(4, food.getAmount());
                    stment.executeUpdate();
                }
                result = true;
            }
        } catch (Exception e) {
            result = false;
            logger.error("Error at UserDAO-storePaymentHistoryOfAUser:" + e.toString());
        } finally {
            closeConnection();
        }
        return result;
    }

    public List<HistoryPaymentDTO> getHistoryPaymentByUserID(String userID) throws SQLException {
        List<HistoryPaymentDTO> result = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                result = new ArrayList<>();
                String sql = "SELECT id,total_price,payment_date FROM tbl_payments WHERE user_id = ?";
                stment = conn.prepareStatement(sql);
                stment.setString(1, userID);
                rs = stment.executeQuery();
                while (rs.next()) {
                    HistoryPaymentDTO temp = new HistoryPaymentDTO();
                    temp.setId(rs.getString("id"));
                    temp.setPaymentDate(rs.getDate("payment_date"));
                    temp.setTotalPrice(rs.getInt("total_price"));
                    result.add(temp);
                }
                List<HistoryFoodInPaymentDTO> listHistoryFood = new ArrayList<>();
                sql = "SELECT tp.id AS payment_id,tf.name AS food_name, tfp.food_id AS food_id,tfp.food_price AS food_price,tfp.quantity AS food_quantity\n"
                        + "FROM tbl_payments tp \n"
                        + "JOIN tbl_foods_in_a_payment tfp ON tp.id = tfp.payment_id \n"
                        + "JOIN tbl_foods tf ON tf.id = tfp.food_id\n"
                        + "WHERE tp.user_id = ?";
                stment = conn.prepareStatement(sql);
                stment.setString(1, userID);
                rs = stment.executeQuery();
                while (rs.next()) {
                    HistoryFoodInPaymentDTO food = new HistoryFoodInPaymentDTO();
                    food.setId(rs.getInt("food_id"));
                    food.setName(rs.getString("food_name"));
                    food.setPaymentID(rs.getString("payment_id"));
                    food.setPrice(rs.getInt("food_price"));
                    food.setQuantity(rs.getInt("food_quantity"));
                    listHistoryFood.add(food);
                }
                for (HistoryFoodInPaymentDTO food : listHistoryFood) {
                    for (HistoryPaymentDTO payment : result) {
                        if (food.getPaymentID().equals(payment.getId())) {
                            payment.getListHistoryFood().add(food);
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Error at UserDAO-getHistoryPaymentByUserID:" + e.toString());
        } finally {
            closeConnection();
        }
        return result;
    }

    public List<HistoryPaymentDTO> searchHistoryPaymentOfAUserByFoodNameAndPaymentDate(String userID, String foodName, Date paymentDate) throws SQLException {
        List<HistoryPaymentDTO> result = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                result = new ArrayList<>();
                String sql = "";
                if (paymentDate != null) {
                    sql = "SELECT id,total_price,payment_date FROM tbl_payments WHERE user_id = ? AND payment_date = ?";
                    stment = conn.prepareStatement(sql);
                    stment.setString(1, userID);
                    System.out.println(paymentDate.toString());
                    stment.setString(2, paymentDate.toString());
                    rs = stment.executeQuery();
                } else {
                    sql = "SELECT id,total_price,payment_date FROM tbl_payments WHERE user_id = ?";
                    stment = conn.prepareStatement(sql);
                    stment.setString(1, userID);
                    rs = stment.executeQuery();
                }
                if (!rs.isBeforeFirst()) {
                    return null;
                }
                while (rs.next()) {
                    HistoryPaymentDTO temp = new HistoryPaymentDTO();
                    temp.setId(rs.getString("id"));
                    temp.setPaymentDate(rs.getDate("payment_date"));
                    temp.setTotalPrice(rs.getInt("total_price"));
                    result.add(temp);
                }
                List<HistoryFoodInPaymentDTO> listHistoryFood = new ArrayList<>();
                if (foodName != null && !foodName.isEmpty()) {
                    sql = "SELECT tp.id AS payment_id,tf.name AS food_name, tfp.food_id AS food_id,tfp.food_price AS food_price,tfp.quantity AS food_quantity\n"
                            + "FROM tbl_payments tp \n"
                            + "JOIN tbl_foods_in_a_payment tfp ON tp.id = tfp.payment_id \n"
                            + "JOIN tbl_foods tf ON tf.id = tfp.food_id\n"
                            + "WHERE tp.user_id = ? AND tf.name LIKE ?";
                    stment = conn.prepareStatement(sql);
                    stment.setString(1, userID);
                    stment.setString(2, "%" + foodName + "%");
                    rs = stment.executeQuery();
                } else {
                    sql = "SELECT tp.id AS payment_id,tf.name AS food_name, tfp.food_id AS food_id,tfp.food_price AS food_price,tfp.quantity AS food_quantity\n"
                            + "FROM tbl_payments tp \n"
                            + "JOIN tbl_foods_in_a_payment tfp ON tp.id = tfp.payment_id \n"
                            + "JOIN tbl_foods tf ON tf.id = tfp.food_id\n"
                            + "WHERE tp.user_id = ?";
                    stment = conn.prepareStatement(sql);
                    stment.setString(1, userID);
                    rs = stment.executeQuery();

                }
                while (rs.next()) {
                    HistoryFoodInPaymentDTO food = new HistoryFoodInPaymentDTO();
                    food.setId(rs.getInt("food_id"));
                    food.setName(rs.getString("food_name"));
                    food.setPaymentID(rs.getString("payment_id"));
                    food.setPrice(rs.getInt("food_price"));
                    food.setQuantity(rs.getInt("food_quantity"));
                    listHistoryFood.add(food);
                }
                for (HistoryFoodInPaymentDTO food : listHistoryFood) {
                    for (HistoryPaymentDTO payment : result) {
                        if (food.getPaymentID().equals(payment.getId())) {
                            payment.getListHistoryFood().add(food);
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Error at UserDAO-searchHistoryPaymentOfAUserByFoodNameAndPaymentDate:" + e.toString());
        } finally {
            closeConnection();
        }
        return result;
    }

    public boolean isExist(String userName) throws SQLException {
        boolean result = false;
        try {
            conn = DBUtils.getConnection();
            String sql = "SELECT user_id FROM tbl_users WHERE user_id = ?";
            stment = conn.prepareStatement(sql);
            stment.setString(1, userName);
            rs = stment.executeQuery();
            if (rs.next()) {
                result = true;
            }
        } catch (Exception e) {
            logger.error("Error at isExist in UserDAO:" + e.getMessage());
        } finally {
            closeConnection();
        }
        return result;
    }

    public boolean addAGoogleUser(User user) throws SQLException {
        boolean result = false;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "INSERT INTO tbl_users(user_id,password,role) values(?,?,?)";
                stment = conn.prepareStatement(sql);
                stment.setString(1, user.getUserID());
                stment.setString(2, user.getPassword());
                stment.setString(3, user.getRole());
                boolean flag = stment.execute();
                if (flag) {
                    result = true;
                }
            }

        } catch (Exception e) {
            logger.error("Error at addAGoogleUser in UserDAO:" + e.getMessage());
        } finally {
            closeConnection();
        }
        return result;
    }

    public Set<Date> getPaymentDateOfAUserByUserID(String userID) throws SQLException {
        List<Date> temp = new ArrayList<>();
        Set<Date> result = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT payment_date FROM tbl_payments WHERE user_id = ?";
                stment = conn.prepareStatement(sql);
                stment.setString(1, userID);
                rs = stment.executeQuery();
                while (rs.next()) {
                    temp.add(rs.getDate("payment_date"));
                }
                result = new HashSet<>();
                result.addAll(temp);
            }

        } catch (Exception e) {
            logger.error("Error at addAGoogleUser in UserDAO:" + e.getMessage());
        } finally {
            closeConnection();
        }
        return result;
    }
}
