/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dtos.CarInViewHistoryDTO;
import dtos.ViewHistoryDTO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import utils.DBUtils;

/**
 *
 * @author dell
 */
public class HistoryDAO {

    private Connection conn;
    private PreparedStatement stment;
    private ResultSet rs;
    private final Logger logger = Logger.getLogger(HistoryDAO.class);

    public HistoryDAO() {
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

    public List<ViewHistoryDTO> getHistoryByEmail(String email) throws SQLException {
        List<ViewHistoryDTO> result = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                result = new ArrayList<>();
                String sql = "SELECT * FROM tbl_history WHERE user_id = ? ORDER BY order_date DESC";
                stment = conn.prepareStatement(sql);
                stment.setString(1, email);
                rs = stment.executeQuery();
                while (rs.next()) {
                    ViewHistoryDTO dto = new ViewHistoryDTO();
                    dto.setId(rs.getString("id"));
                    dto.setTotalPrice(rs.getInt("total_price"));
                    dto.setOrderDate(rs.getDate("order_date"));
                    dto.setStatus(rs.getBoolean("status"));
                    dto.setUserEmail(email);
                    result.add(dto);
                }
                sql = "SELECT cih.id,cih.history_id,cih.car_id,cih.expired_date,cih.quantity,cih.price,c.name as name FROM tbl_cars_in_history_payment cih JOIN tbl_cars c ON c.id = cih.car_id WHERE history_id = ?";
                stment = conn.prepareStatement(sql);
                for (ViewHistoryDTO dto : result) {
                    List<CarInViewHistoryDTO> listCar = new ArrayList<>();
                    stment.setString(1, dto.getId());
                    rs = stment.executeQuery();
                    while (rs.next()) {
                        CarInViewHistoryDTO temp = new CarInViewHistoryDTO();
                        temp.setId(rs.getInt("id"));
                        temp.setHistoryID(dto.getId());
                        temp.setCarID(rs.getInt("car_id"));
                        Date expiredDate = rs.getDate("expired_date");
                        boolean expiredFlag = false;
                        Date now = Date.valueOf(LocalDate.now());
                        if (now.after(expiredDate)) {
                            expiredFlag = true;
                        }
                        temp.setExpiredDate(rs.getDate("expired_date"));
                        temp.setExpired(expiredFlag);
                        temp.setQuantity(rs.getInt("quantity"));
                        temp.setPrice(rs.getInt("price"));
                        temp.setCarName(rs.getString("name"));
                        listCar.add(temp);
                    }
                    dto.setListCar(listCar);
                }
            }
        } catch (Exception e) {
            logger.error("Error at getHistoryByEmail in HistoryDAO:" + e.getMessage());
        } finally {
            closeConnection();
        }
        return result;
    }

    public boolean updateHistoryByIDS(String id) throws SQLException {
        boolean result = false;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "UPDATE tbl_history SET status = 'false' WHERE id = ?";
                stment = conn.prepareStatement(sql);
                stment.setString(1, id);
                int flag = stment.executeUpdate();
                if (flag > 0) {
                    result = true;
                }
            }

        } catch (Exception e) {
            logger.error("Error at updateHistoryByIDS in HistoryDAO:" + e.getMessage());
        } finally {
            closeConnection();
        }
        return result;
    }

    public List<ViewHistoryDTO> search(Date orderDate, String carName, String email) throws SQLException {
        List<ViewHistoryDTO> result = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                result = new ArrayList<>();
                Set<String> setSearchResult = new HashSet<>();
                String sql = "SELECT h.id FROM tbl_history h \n"
                        + "  JOIN tbl_cars_in_history_payment cih ON h.id = cih.history_id \n"
                        + "  JOIN tbl_cars c ON c.id = cih.car_id WHERE h.user_id = ?";
                if (!carName.isEmpty()) {
                    sql = sql.concat(" AND c.name LIKE ?");
                }
                if (orderDate != null) {
                    sql = sql.concat(" AND h.order_date = ?");
                }
                stment = conn.prepareStatement(sql);
                stment.setString(1, email);
                if (!carName.isEmpty()) {
                    stment.setString(2, "%" + carName + "%");
                }
                if (carName.isEmpty() && orderDate != null) {
                    stment.setDate(2, orderDate);
                }
                if (!carName.isEmpty() && orderDate != null) {
                    stment.setDate(3, orderDate);
                }
                rs = stment.executeQuery();
                while (rs.next()) {
                    setSearchResult.add(rs.getString("id"));
                }
                List<String> listSearchResult = new ArrayList<>();
                listSearchResult.addAll(setSearchResult);
                sql = "SELECT * FROM tbl_history WHERE id = ?";
                stment = conn.prepareStatement(sql);
                for (String id : listSearchResult) {
                    stment.setString(1, id);
                    rs = stment.executeQuery();
                    while (rs.next()) {
                        ViewHistoryDTO dto = new ViewHistoryDTO();
                        dto.setId(rs.getString("id"));
                        dto.setTotalPrice(rs.getInt("total_price"));
                        dto.setOrderDate(rs.getDate("order_date"));
                        dto.setStatus(rs.getBoolean("status"));
                        dto.setUserEmail(email);
                        result.add(dto);
                    }
                }
                sql = "SELECT cih.id,cih.history_id,cih.car_id,cih.expired_date,cih.quantity,cih.price,c.name as name FROM tbl_cars_in_history_payment cih JOIN tbl_cars c ON c.id = cih.car_id WHERE history_id = ?";
                stment = conn.prepareStatement(sql);
                for (ViewHistoryDTO dto : result) {
                    List<CarInViewHistoryDTO> listCar = new ArrayList<>();
                    stment.setString(1, dto.getId());
                    rs = stment.executeQuery();
                    while (rs.next()) {
                        CarInViewHistoryDTO temp = new CarInViewHistoryDTO();
                        temp.setId(rs.getInt("id"));
                        temp.setHistoryID(dto.getId());
                        temp.setCarID(rs.getInt("car_id"));
                        Date expiredDate = rs.getDate("expired_date");
                        boolean expiredFlag = false;
                        Date now = Date.valueOf(LocalDate.now());
                        if (now.after(expiredDate)) {
                            expiredFlag = true;
                        }
                        temp.setExpiredDate(rs.getDate("expired_date"));
                        temp.setExpired(expiredFlag);
                        temp.setQuantity(rs.getInt("quantity"));
                        temp.setPrice(rs.getInt("price"));
                        temp.setCarName(rs.getString("name"));
                        listCar.add(temp);
                    }
                    dto.setListCar(listCar);
                }
            }
        } catch (Exception e) {
            logger.error("Error at search in HistoryDAO:" + e.getMessage());
        } finally {
            closeConnection();
        }
        return result;
    }

    public Set<Date> getAllOrderDateByEmail(String email) throws SQLException {
        Set<Date> result = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                result = new HashSet<>();
                String sql = "SELECT order_date FROM tbl_history WHERE user_id = ?";
                stment = conn.prepareStatement(sql);
                stment.setString(1, email);
                rs = stment.executeQuery();
                while (rs.next()) {
                    result.add(rs.getDate("order_date"));
                }
            }
        } catch (Exception e) {
            logger.error("Error at getAllOrderDateByEmail in HistoryDAO:" + e.getMessage());
        } finally {
            closeConnection();
        }
        return result;
    }
}
