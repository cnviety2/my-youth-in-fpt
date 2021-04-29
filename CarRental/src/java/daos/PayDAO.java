/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dtos.CarInCartDTO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import utils.DBUtils;

/**
 *
 * @author dell
 */
public class PayDAO {

    private Connection conn;
    private PreparedStatement stment;
    private ResultSet rs;
    private final Logger logger = Logger.getLogger(PayDAO.class);

    public PayDAO() {
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

    public List<String> checkQuantityInDB(List<CarInCartDTO> cart) throws SQLException {
        List<String> listOutOfStock = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                listOutOfStock = new ArrayList<>();
                List<CarInCartDTO> listDataFromDB = new ArrayList<>();
                String sql = "SELECT id,name,quantity FROM tbl_cars WHERE id = ?";
                stment = conn.prepareStatement(sql);
                for (CarInCartDTO dto : cart) {
                    stment.setInt(1, dto.getId());
                    rs = stment.executeQuery();
                    if (rs.next()) {
                        CarInCartDTO tempData = new CarInCartDTO();
                        tempData.setId(dto.getId());
                        tempData.setName(rs.getString("name"));
                        tempData.setQuantity(rs.getInt("quantity"));
                        listDataFromDB.add(tempData);
                    }
                }
                for (CarInCartDTO dto : cart) {
                    for (CarInCartDTO dto2 : listDataFromDB) {
                        if (dto.getId() == dto2.getId()) {
                            if (dto.getQuantity() > dto2.getQuantity()) {
                                listOutOfStock.add(dto.getName());
                                break;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Error at checkQuantityInDB in PayDAO:" + e.getMessage());
        } finally {
            closeConnection();
        }
        return listOutOfStock;
    }

    public boolean payProcessing(String email, List<CarInCartDTO> cart) throws SQLException {
        boolean result = false;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                List<CarInCartDTO> listDataFromDB = new ArrayList<>();
                String sql = "SELECT id,quantity FROM tbl_cars WHERE id = ?";
                stment = conn.prepareStatement(sql);
                for (CarInCartDTO dto : cart) {
                    stment.setInt(1, dto.getId());
                    rs = stment.executeQuery();
                    if (rs.next()) {
                        CarInCartDTO tempData = new CarInCartDTO();
                        tempData.setId(dto.getId());
                        tempData.setQuantity(rs.getInt("quantity"));
                        listDataFromDB.add(tempData);
                    }
                }
                for (CarInCartDTO carFromDB : listDataFromDB) {
                    for (CarInCartDTO carFromCart : cart) {
                        if (carFromDB.getId() == carFromCart.getId()) {
                            carFromDB.setQuantity(carFromDB.getQuantity() - carFromCart.getQuantity());
                            break;
                        }
                    }
                }
                sql = "UPDATE tbl_cars SET quantity = ? WHERE id = ?";
                stment = conn.prepareStatement(sql);
                for (CarInCartDTO carToUpdateDB : listDataFromDB) {
                    stment.setInt(1, carToUpdateDB.getQuantity());
                    stment.setInt(2, carToUpdateDB.getId());
                    stment.executeUpdate();
                }
                result = true;
            }
        } catch (Exception e) {
            result = false;
            logger.error("Error at payProcessing in PayDAO:" + e.getMessage());
        } finally {
            closeConnection();
        }
        return result;
    }

    public boolean saveToHistory(String email, List<CarInCartDTO> cart, int total) throws SQLException {
        boolean result = false;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "INSERT INTO tbl_history(id,total_price,order_date,status,user_id) VALUES(?,?,?,?,?)";
                stment = conn.prepareStatement(sql);
                String id = UUID.randomUUID().toString();
                Date now = Date.valueOf(LocalDate.now());
                stment.setString(1, id);
                stment.setInt(2, total);
                stment.setDate(3, now);
                stment.setBoolean(4, true);
                stment.setString(5, email);
                int flag = stment.executeUpdate();
                if (flag > 0) {
                    sql = "INSERT INTO tbl_cars_in_history_payment(history_id,car_id,expired_date,quantity,price) VALUES(?,?,?,?,?)";
                    stment = conn.prepareStatement(sql);
                    for (CarInCartDTO dto : cart) {
                        stment.setString(1, id);
                        String dt = now.toString();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Calendar c = Calendar.getInstance();
                        c.setTime(sdf.parse(dt));
                        c.add(Calendar.DATE, dto.getRentalDate());  // number of days to add
                        dt = sdf.format(c.getTime());
                        Date expiredDate = Date.valueOf(dt);
                        stment.setInt(2, dto.getId());
                        stment.setDate(3, expiredDate);
                        stment.setInt(4, dto.getQuantity());
                        stment.setInt(5, dto.getPrice());
                        stment.execute();
                    }
                    result = true;
                }
            }
        } catch (Exception e) {
            result = false;
            logger.error("Error at checkQuantityInDB in PayDAO:" + e.getMessage());
        } finally {
            closeConnection();
        }
        return result;
    }

}
