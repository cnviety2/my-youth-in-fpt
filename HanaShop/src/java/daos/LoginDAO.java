/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dtos.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import utils.DBUtils;

/**
 *
 * @author dell
 */
public class LoginDAO {

    private Connection conn;
    private PreparedStatement stment;
    private ResultSet rs;

    private final Logger logger = Logger.getLogger(LoginDAO.class);

    public LoginDAO() {
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

    public User checkLogin(String userID, String password) throws SQLException {
        User result = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT user_id,password,role FROM tbl_users WHERE user_id = ? AND password = ?";
                stment = conn.prepareStatement(sql);
                stment.setString(1, userID);
                stment.setString(2, password);
                rs = stment.executeQuery();
                if (rs.next()) {
                    result = new User();
                    result.setRole(rs.getString("role"));
                    result.setUserID(rs.getString("user_id"));
                    result.setPassword(rs.getString("password"));
                }
            }

        } catch (Exception e) {
           logger.error("Error at LoginDAO-checkLogin:" + e.toString());
        } finally {
            closeConnection();
        }
        return result;
    }

}
