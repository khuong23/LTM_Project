package model.DAO;

import model.Bean.Users;
import utils.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class UsersDAO {
    
    public Users getUser(String username) throws SQLException {
        Connection connection = DBConnection.getConnection();
        String query = "SELECT user_id, username, password, role FROM Users WHERE username = ?";
        try (var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            try (var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int userId = resultSet.getInt("user_id");
                    String userUsername = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    String role = resultSet.getString("role");
                    return new Users(userId, userUsername, password, role);
                } else {
                    return null;
                }
            }
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }
    
    public Users setUser(String username, String password, String role) throws SQLException {
        Connection connection = DBConnection.getConnection();
        String query = "INSERT INTO Users (username, password, role) VALUES (?, ?, ?)";
        try (var preparedStatement = connection.prepareStatement(query, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, role);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }
            try (var generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int userId = generatedKeys.getInt(1);
                    return new Users(userId, username, password, role);
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }
}
