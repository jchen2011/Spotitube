package nl.han.oose.dea.services;

import jakarta.inject.Inject;
import nl.han.oose.dea.datasource.UserDAO;
import nl.han.oose.dea.dto.outgoing.UserResponseDTO;
import nl.han.oose.dea.dto.outgoing.LoginResultDTO;
import nl.han.oose.dea.dto.incoming.UserDTO;
import org.apache.commons.codec.digest.DigestUtils;


import java.util.UUID;


public class LoginService {
    private UserDAO userDAO;

    public LoginService() {

    }

    @Inject
    public LoginService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public LoginResultDTO doLogin(UserDTO user) {
        UserResponseDTO userDB = userDAO.getUser(user.getUser());
        if (checkPassword(user, userDB)) {
            LoginResultDTO result = new LoginResultDTO();
            String token = generateToken();
            result.setUser(user.getUser());
            result.setToken(token);
            userDAO.addToken(result);
            return result;
        }
        return null;
    }

    public String generateToken() {
        return UUID.randomUUID().toString();
    }

    public boolean checkPassword(UserDTO user, UserResponseDTO userDB) {
        return DigestUtils.sha256Hex(user.getPassword()).equals(userDB.getPassword()) && userDB.getUserName().equalsIgnoreCase(user.getUser());
    }
}
