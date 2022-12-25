package nl.han.oose.dea.utils;

import jakarta.inject.Inject;
import nl.han.oose.dea.datasource.UserDAO;
import nl.han.oose.dea.dto.incoming.UserDTO;
import nl.han.oose.dea.dto.outgoing.UserResponseDTO;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.UUID;

public abstract class UserAuth {

    private UserDAO userDAO;

    public UserAuth() {

    }

    @Inject
    public UserAuth(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
    public String generateToken() {
        return UUID.randomUUID().toString();
    }

    public boolean checkPassword(UserDTO user, UserResponseDTO userDB) {
        return DigestUtils.sha256Hex(user.getPassword()).equals(userDB.getPassword()) && userDB.getUserName().equalsIgnoreCase(user.getUser());
    }

    public boolean checkIfTokenExists(String token) {
        return userDAO.getAllTokens().contains(token);
    }
}
