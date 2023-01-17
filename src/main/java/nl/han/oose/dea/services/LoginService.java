package nl.han.oose.dea.services;

import jakarta.inject.Inject;
import nl.han.oose.dea.datasource.UserDAO;
import nl.han.oose.dea.dto.outgoing.UserResponseDTO;
import nl.han.oose.dea.dto.outgoing.LoginResponseDTO;
import nl.han.oose.dea.dto.incoming.UserDTO;
import nl.han.oose.dea.services.exceptions.AuthenticationException;
import nl.han.oose.dea.utils.UserAuth;


public class LoginService {

    private UserAuth userAuth;
    private UserDAO userDAO;

    public LoginService() {

    }

    @Inject
    public void setUserAuth(UserAuth userAuth) {
        this.userAuth = userAuth;
    }


    @Inject
    public void setLoginService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * Authenticates the user. If the credentials are correct it will generate a token for the user.
     *
     * @param u the user that is trying to login
     * @return the username and token as a {@link LoginResponseDTO}
     * @throws AuthenticationException the credentials are invalid
     */
    public LoginResponseDTO authenticateAndGenerateToken(UserDTO u) {
        UserResponseDTO userDB = userDAO.getUser(u.getUser());
        if (userAuth.checkPassword(u, userDB)) {
            LoginResponseDTO result = new LoginResponseDTO();

            String token = userAuth.generateToken();

            result.setUser(u.getUser());
            result.setToken(token);

            userDAO.addToken(result);

            return result;
        } else {
            throw new AuthenticationException("Invalid credentials");
        }
    }
}
