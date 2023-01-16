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

    public String generateToken() {
        return UUID.randomUUID().toString();
    }

    public boolean checkPassword(UserDTO user, UserResponseDTO userDB) {
        return DigestUtils.sha256Hex(user.getPassword()).equals(userDB.getPassword());
    }

    public boolean checkIfTokenExists(String token) {
        return this.userDAO.getAllTokens().contains(token);
    }
}
