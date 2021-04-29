package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author dell
 */
public class DBUtils {

    private static final String DB_NAME = "lab231_2";
    private static final String USER = "sa";
    private static final String PASSWORD = "12345678";

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Connection conn = null;
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String url = "jdbc:sqlserver://localhost:1433;databaseName = " + DB_NAME;
        conn = DriverManager.getConnection(url, USER, PASSWORD);
        return conn;
    }
}
