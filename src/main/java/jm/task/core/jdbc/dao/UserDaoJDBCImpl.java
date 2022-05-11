package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection connection = Util.getConnection()){
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users (" +
                            "  `id` BIGINT NOT NULL AUTO_INCREMENT," +
                            "  `Name` VARCHAR(45) NOT NULL," +
                            "  `LastName` VARCHAR(45) NOT NULL," +
                            "  `Age` TINYINT NOT NULL," +
                            "  PRIMARY KEY (`id`))");
            System.out.println("The table was created");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConnection()){
            Statement statement = connection.createStatement();
            statement.executeUpdate("DROP TABLE IF EXISTS users");
            System.out.println("The table was deleted");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO users (name, lastname, age) VALUES (?, ?, ?)";
        try (Connection connection = Util.getConnection()){
            PreparedStatement ppsm = connection.prepareStatement(sql);
            ppsm.setString(1, name);
            ppsm.setString(2, lastName);
            ppsm.setByte(3, age);
            ppsm.executeUpdate();
            System.out.println("User " + name + " was added to table");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getConnection()){
            PreparedStatement ppsm = connection.prepareStatement("DELETE FROM user WHERE id = ?");
            ppsm.setLong(1, id);
            System.out.println("User " + id + " was deleted");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT id, name, lastName, age from users";
        try (Connection connection = Util.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection()){
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM users");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
