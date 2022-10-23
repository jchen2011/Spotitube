package nl.han.oose.dea.rest.datasource;

import jakarta.inject.Inject;
import nl.han.oose.dea.rest.datasource.util.DatabaseProperties;
import nl.han.oose.dea.rest.services.dto.LoginResultDTO;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDAO {
    private Logger logger = Logger.getLogger(getClass().getName());

    private DatabaseProperties databaseProperties;

    public UserDAO() {

    }
    @Inject
    public UserDAO(DatabaseProperties databaseProperties) {
        this.databaseProperties = databaseProperties;
    }

    public UserData getUser(String username) {
        try (Connection connection = DriverManager.getConnection(databaseProperties.connectionString())) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM spotitube.users WHERE username = ?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            UserData u = null;
            while (resultSet.next()) {
                String userName = resultSet.getString(2);
                String password = resultSet.getString(3);
                String token = resultSet.getString(4);
                String fullName = resultSet.getString(5);

                u = new UserData(userName, fullName, token, password);
            }
            return u;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Can't find user " + username, e);
        }
        return null;
    }

    public boolean addToken(LoginResultDTO user) {
        try (Connection connection = DriverManager.getConnection(databaseProperties.connectionString())) {
            PreparedStatement statement = connection.prepareStatement("UPDATE users SET token = ? WHERE username = ?");
            statement.setString(1, user.getToken());
            statement.setString(2, user.getUser());
            return statement.execute();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Can't insert token " + user.getToken(), e);
        }
        return false;
    }

    public List<String> getAllTokens() {
        List<String> tokens = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(databaseProperties.connectionString())) {
            PreparedStatement statement = connection.prepareStatement("SELECT spotitube.users.token FROM spotitube.users");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String token = resultSet.getString(1);
                tokens.add(token);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Can't find token", e);
        }
        return tokens;
    }

    public String getUsernameByToken(String token) {
        try (Connection connection = DriverManager.getConnection(databaseProperties.connectionString())) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM spotitube.users WHERE spotitube.users.token = ?");
            statement.setString(1, token);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String username = resultSet.getString(2);
                return username;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Can't find username", e);
        }
        return null;
    }

    public int getUserIdByToken(String token) {
        try (Connection connection = DriverManager.getConnection(databaseProperties.connectionString())) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM spotitube.users WHERE spotitube.users.token = ?");
            statement.setString(1, token);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int userId = resultSet.getInt(1);
                return userId;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Can't find userId", e);
        }
        return 0;
    }

}
