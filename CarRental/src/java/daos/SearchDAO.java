/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dtos.Car;
import dtos.CarInCartDTO;
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
public class SearchDAO {

    private Connection conn;
    private PreparedStatement stment;
    private ResultSet rs;
    private final Logger logger = Logger.getLogger(SearchDAO.class);

    public SearchDAO() {
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

    public List<Car> searchCar(String name, String category, Integer rentalDate, Integer quantity, Integer page) throws SQLException {
        List<Car> result = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                result = new ArrayList<>();
                String sql = "SELECT id,name,color,year,category,price,quantity FROM tbl_cars WHERE";
                sql = sql.concat(" name LIKE '%" + name + "%'");
                if (category != null && !category.isEmpty()) {
                    sql = sql.concat(" AND category = '" + category + "'");
                }
                if (rentalDate != null) {
                    sql = sql.concat(" AND rental_date = " + rentalDate);
                }
                if (quantity != null) {
                    sql = sql.concat("AND quantity > " + quantity);
                }
                sql = sql.concat(" ORDER BY create_date ASC OFFSET ? ROWS FETCH NEXT 2 ROWS ONLY");
                stment = conn.prepareStatement(sql);
                stment.setInt(1, page * 2);
                rs = stment.executeQuery();
                while (rs.next()) {
                    Car car = new Car();
                    car.setName(rs.getString("name"));
                    car.setColor(rs.getString("color"));
                    car.setYear(rs.getInt("year"));
                    car.setCategory(rs.getString("category"));
                    car.setPrice(rs.getInt("price"));
                    car.setQuantity(rs.getInt("quantity"));
                    car.setId(rs.getInt("id"));
                    result.add(car);
                }
            }
        } catch (Exception e) {
            logger.error("Error at searchCar in SearchDAO:" + e.getMessage());
        } finally {
            closeConnection();
        }
        return result;
    }

    public int getAllRecordsOfThatSearch(String name, String category, Integer rentalDate, Integer quantity) throws SQLException {
        int result = 0;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT name,color,year,category,price,quantity FROM tbl_cars WHERE";
                sql = sql.concat(" name LIKE '%" + name + "%'");
                if (category != null && !category.isEmpty()) {
                    sql = sql.concat(" AND category = '" + category + "'");
                }
                if (rentalDate != null) {
                    sql = sql.concat(" AND rental_date = " + rentalDate);
                }
                if (quantity != null) {
                    sql = sql.concat("AND quantity > " + quantity);
                }
                stment = conn.prepareStatement(sql);
                rs = stment.executeQuery();
                while (rs.next()) {
                    result++;
                }
            }
        } catch (Exception e) {
            logger.error("Error at searchCar in SearchDAO:" + e.getMessage());
        } finally {
            closeConnection();
        }
        return result;
    }

    public CarInCartDTO getCarById(int id) throws SQLException {
        CarInCartDTO result = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT name,category,price,rental_date FROM tbl_cars WHERE id = ?";
                stment = conn.prepareStatement(sql);
                stment.setInt(1, id);
                rs = stment.executeQuery();
                if (rs.next()) {
                    result = new CarInCartDTO();
                    result.setName(rs.getString("name"));
                    result.setId(id);
                    result.setCategory(rs.getString("category"));
                    result.setPrice(rs.getInt("price"));
                    result.setRentalDate(rs.getInt("rental_date"));
                }
            }

        } catch (Exception e) {
            logger.error("Error at getCarById in SearchDAO:" + e.getMessage());
        } finally {
            closeConnection();
        }
        return result;
    }

}
