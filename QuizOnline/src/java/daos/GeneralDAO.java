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

    public User checkLogin(String name, String password) throws SQLException {
        User result = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT email,name,password,role,status FROM tbl_users WHERE email = ? AND password = ?";
                stment = conn.prepareStatement(sql);
                stment.setString(1, name);
                stment.setString(2, password);
                rs = stment.executeQuery();
                if (rs.next()) {
                    result = new User();
                    result.setRole(rs.getString("role"));
                    result.setEmail(rs.getString("email"));
                    result.setPassword(rs.getString("password"));
                    result.setStatus(rs.getString("status"));
                    result.setName(rs.getString("name"));
                }
            }

        } catch (Exception e) {
            logger.error("Error at LoginDAO-checkLogin:" + e.toString());
        } finally {
            closeConnection();
        }
        return result;
    }

    public boolean addAUser(User user) throws SQLException {
        boolean result = false;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "INSERT INTO tbl_users(email,name,password,role,status) values(?,?,?,?,?)";
                stment = conn.prepareStatement(sql);
                stment.setString(1, user.getEmail());
                stment.setString(2, user.getName());
                stment.setString(3, user.getPassword());
                stment.setString(4, "ROLE_STUDENT");
                stment.setString(5, "new");
                int flag = stment.executeUpdate();
                if (flag > 0) {
                    result = true;
                }
            }

        } catch (Exception e) {
            logger.error("Error at addAUser in UserDAO:" + e.getMessage());
        } finally {
            closeConnection();
        }
        return result;
    }

}
