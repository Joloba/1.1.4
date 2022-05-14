package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.sql.*;

public class Util {

    private static final String db_Driver = "com.mysql.cj.jdbc.Driver";
    private static final String dbUrl = "jdbc:mysql://localhost:3306/test";
    private static final String dbUserName = "root";
    private static final String dbPassword = "root";
    private static Connection connection;
    private static SessionFactory sessionFactory = null;
    private static Session session;
    private static Util util;

    public static Connection connect() {
        if (util == null) {
            util = new Util();
        }

        try {
            if (connection == null || connection.isClosed()) {
                Driver driver = new com.mysql.cj.jdbc.Driver();
                DriverManager.registerDriver(driver);
                connection = DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
            }
            return connection;
        } catch (SQLException e) {
        }
        return null;
    }

    public static boolean disconnect() {
        try {
            connection.close();
            return true;
        } catch (SQLException e) {
        }
        return false;
    }

    public static Session getSession() {
        if (sessionFactory == null || sessionFactory.isClosed()) {
            try {
                Configuration configuration = new Configuration()
                        .setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver")
                        .setProperty("hibernate.connection.url", dbUrl)
                        .setProperty("hibernate.connection.username", dbUserName)
                        .setProperty("hibernate.connection.password", dbPassword)
                        .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
                        .addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
                session = sessionFactory.openSession();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (session.isConnected()) {
            return sessionFactory.openSession();
        }
        return session;
    }
}
