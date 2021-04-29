/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dtos.DiscountCoupon;
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
public class DiscountDAO {

    private Connection conn;
    private PreparedStatement stment;
    private ResultSet rs;
    private final Logger logger = Logger.getLogger(DiscountDAO.class);

    public DiscountDAO() {
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

    public DiscountCoupon getCouponByID(String id) throws SQLException {
        DiscountCoupon result = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT discount,expired_date  FROM tbl_discount_coupons WHERE id = ?";
                stment = conn.prepareStatement(sql);
                stment.setString(1, id);
                rs = stment.executeQuery();
                if (rs.next()) {
                    result = new DiscountCoupon(id, rs.getInt("discount"),rs.getDate("expired_date"));
                }
            }

        } catch (Exception e) {
            logger.error("Error at getCouponByID in DiscountDAO:" + e.getMessage());
        } finally {
            closeConnection();
        }
        return result;
    }
}
