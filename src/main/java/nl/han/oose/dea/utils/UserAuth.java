package nl.han.oose.dea.utils;

import jakarta.inject.Inject;
import nl.han.oose.dea.datasource.UserDAO;
import nl.han.oose.dea.dto.incoming.UserDTO;
import nl.han.oose.dea.dto.outgoing.UserResponseDTO;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.UUID;

public class UserAuth {
    private UserDAO userDAO;

    @Inject
    public UserAuth(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * Generates a new token with the UUID library.
     *
     * @return a new generated token as {@link String}
     */
    public String generateToken() {
        return UUID.randomUUID().toString();
    }

    /**
     * Checks the user password with the password settled in the database.
     *
     * @param user the user that tries to login
     * @param userDB the user that is settled in the database
     * @return a true or false as {@link boolean} whether the password are correct
     */
    public boolean checkPassword(UserDTO user, UserResponseDTO userDB) {
        return DigestUtils.sha256Hex(user.getPassword()).equals(userDB.getPassword());
    }

    /**
     * Checks if the user token is in the database.
     *
     * @param token the user token
     * @return a true or false as {@link boolean} whether it is in the database or not
     */
    public boolean checkIfTokenExists(String token) {
        return this.userDAO.getAllTokens().contains(token);
    }
}
