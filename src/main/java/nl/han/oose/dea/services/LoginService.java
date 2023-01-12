package nl.han.oose.dea.services;

import jakarta.inject.Inject;
import nl.han.oose.dea.datasource.UserDAO;
import nl.han.oose.dea.dto.outgoing.UserResponseDTO;
import nl.han.oose.dea.dto.outgoing.LoginResponseDTO;
import nl.han.oose.dea.dto.incoming.UserDTO;
import nl.han.oose.dea.services.exceptions.AuthenticationException;
import nl.han.oose.dea.utils.UserAuth;


public class LoginService extends UserAuth {
    private UserDAO userDAO;

    public LoginService() {

    }

    @Inject
    public void setLoginService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public LoginResponseDTO authenticateAndGenerateToken(UserDTO u) {
        UserResponseDTO userDB = userDAO.getUser(u.getUser());
        if (checkPassword(u, userDB)) {
            LoginResponseDTO result = new LoginResponseDTO();

            String token = generateToken();
            result.setToken(token);
            userDAO.addToken(result);

            result.setUser(u.getUser());
            return result;
        } else {
            throw new AuthenticationException("Invalid credentials");
        }
    }
}
