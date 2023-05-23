package nl.han.oose.dea.datasource;

import nl.han.oose.dea.dto.outgoing.LoginResponseDTO;
import nl.han.oose.dea.dto.outgoing.UserResponseDTO;
import nl.han.oose.dea.services.exceptions.InternalServerError;
import nl.han.oose.dea.utils.Log;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends DAO<UserResponseDTO>{


    public UserDAO() {

    }

    public UserResponseDTO getUser(String username) {
        String sql = "SELECT * FROM spotitube.users WHERE username = ?";

        PreparedStatement statement = connect(sql);
        setString(statement, username, 1);
        ResultSet set = executeQuery(statement);

        List<UserResponseDTO> user = convertMultiple(set, 0);

        return user.get(0);
    }

    public void addToken(LoginResponseDTO user) {
        String sql = "UPDATE spotitube.users SET token = ? WHERE username = ?";

        PreparedStatement statement = connect(sql);
        setString(statement, user.getToken(), 1);
        setString(statement, user.getUser(), 2);
        execute(statement);
    }

    public List<String> getAllTokens() {
        String sql = "SELECT spotitube.users.token FROM spotitube.users";

        PreparedStatement statement = connect(sql);
        ResultSet set = executeQuery(statement);

        return convertAllTokens(set);
    }


    public int getUserId(String token) {
        String sql = "SELECT spotitube.users.userId FROM spotitube.users WHERE spotitube.users.token = ?";

        PreparedStatement statement = connect(sql);
        setString(statement, token, 1);
        ResultSet set = executeQuery(statement);

        return getIntFirstRow(set, "userId");
    }

    @Override
    protected UserResponseDTO convert(ResultSet set, int id) {
        String username = getString(set, "username");
        String token = getString(set, "token");
        String password = getString(set, "password");

        return new UserResponseDTO(username, token, password);
    }

    private List<String> convertAllTokens(ResultSet set) {
        List<String> results = new ArrayList<>();

        try {
            while (set.next()) {
                String token = getString(set, "token");
                results.add(token);
            }
        } catch (Exception e) {
            Log.console("Failed to convert all tokens.", e);
            throw new InternalServerError();
        }

        return results;
    }
}
