package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

    private static final String db_Driver = "com.mysql.cj.jdbc.Driver";
    private static final String dbUrl = "jdbc:mysql://localhost:3306/test";
    private static final String dbUserName = "root";
    private static final String dbPassword = "root";
    private static Connection connection;

    public static Connection getConnection() {

        try {
            Class.forName(db_Driver);
            connection = DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
            System.out.println("Соединение с БД установлено");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
