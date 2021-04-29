/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dtos.User;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import utils.DBUtils;

/**
 *
 * @author dell
 */
public class RegistrationDAO {

    private Connection conn;
    private PreparedStatement stment;
    private ResultSet rs;
    private final Logger logger = Logger.getLogger(RegistrationDAO.class);

    public RegistrationDAO() {
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

    public boolean addAUser(User user) throws SQLException {
        boolean result = false;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "INSERT INTO tbl_users(email,phone,name,address,status,role,create_date,password) "
                        + "VALUES(?,?,?,?,?,?,?,?)";
                stment = conn.prepareStatement(sql);
                stment.setString(1, user.getEmail());
                stment.setString(2, user.getPhone());
                stment.setString(3, user.getName());
                stment.setString(4, user.getAddress());
                stment.setString(5, "New");
                stment.setString(6, "ROLE_USER");
                Date now = Date.valueOf(LocalDate.now());
                stment.setDate(7, now);
                stment.setString(8, user.getPassword());
                int flag = stment.executeUpdate();
                if (flag > 0) {
                    result = true;
                }
            }
        } catch (Exception e) {
            logger.error("Error at addAUser in RegistrationDAO:" + e.getMessage());
        } finally {
            closeConnection();
        }
        return result;
    }

    public boolean isEmailExist(String email) throws SQLException {
        boolean result = false;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT email FROM tbl_users WHERE email = ?";
                stment = conn.prepareStatement(sql);
                stment.setString(1, email);
                rs = stment.executeQuery();
                if (rs.next()) {
                    result = true;
                }
            }
        } catch (Exception e) {
            logger.error("Error at isEmailExist in RegistrationDAO:" + e.getMessage());
        } finally {
            closeConnection();
        }
        return result;
    }

    public boolean updateConfirmedUser(String email) throws SQLException {
        boolean result = false;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "UPDATE tbl_users SET status = 'Active' WHERE email = ?";
                stment = conn.prepareStatement(sql);
                stment.setString(1, email);
                int flag = stment.executeUpdate();
                if (flag > 0) {
                    result = true;
                }
            }
        } catch (Exception e) {
            logger.error("Error at updateConfirmedUser in RegistrationDAO:" + e.getMessage());
        } finally {
            closeConnection();
        }
        return result;
    }

}
