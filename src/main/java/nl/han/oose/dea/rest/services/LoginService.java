package nl.han.oose.dea.rest.services;

import jakarta.inject.Inject;
import nl.han.oose.dea.rest.datasource.UserDAO;
import nl.han.oose.dea.rest.services.dto.UserDataDTO;
import nl.han.oose.dea.rest.services.dto.LoginResultDTO;
import nl.han.oose.dea.rest.services.dto.UserDTO;
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
        UserDataDTO userDB = userDAO.getUser(user.getUser());
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

    public boolean checkPassword(UserDTO user, UserDataDTO userDB) {
        return DigestUtils.sha256Hex(user.getPassword()).equals(userDB.getPassword()) && userDB.getUserName().equalsIgnoreCase(user.getUser());
    }
}
