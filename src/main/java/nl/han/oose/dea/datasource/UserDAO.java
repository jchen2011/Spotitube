package nl.han.oose.dea.datasource;

import jakarta.inject.Inject;
import nl.han.oose.dea.dto.outgoing.LoginResponseDTO;
import nl.han.oose.dea.dto.outgoing.UserResponseDTO;


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

    public UserResponseDTO getUser(String username) {
        try (Connection connection = DriverManager.getConnection(databaseProperties.connectionString())) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM spotitube.users WHERE username = ?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            UserResponseDTO u = null;
            while (resultSet.next()) {
                String userName = resultSet.getString(2);
                String password = resultSet.getString(3);
                String token = resultSet.getString(4);

                u = new UserResponseDTO(userName, token, password);
            }
            return u;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Can't find user " + username, e);
        }
        return null;
    }

    public boolean addToken(LoginResponseDTO user) {
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


    public int getUserId(String token) {
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
