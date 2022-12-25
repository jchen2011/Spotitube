package nl.han.oose.dea.services;

import jakarta.inject.Inject;
import nl.han.oose.dea.datasource.UserDAO;
import nl.han.oose.dea.dto.outgoing.UserResponseDTO;
import nl.han.oose.dea.dto.outgoing.LoginResponseDTO;
import nl.han.oose.dea.dto.incoming.UserDTO;
import nl.han.oose.dea.utils.UserAuth;
import org.apache.commons.codec.digest.DigestUtils;


import java.util.UUID;


public class LoginService extends UserAuth {
    private UserDAO userDAO;

    public LoginService() {

    }

    @Inject
    public LoginService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public LoginResponseDTO doLogin(UserDTO user) {
        UserResponseDTO userDB = userDAO.getUser(user.getUser());
        if (checkPassword(user, userDB)) {
            LoginResponseDTO result = new LoginResponseDTO();
            String token = generateToken();
            result.setUser(user.getUser());
            result.setToken(token);
            userDAO.addToken(result);
            return result;
        }
        return null;
    }




}
