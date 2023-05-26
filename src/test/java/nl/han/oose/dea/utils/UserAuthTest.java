package nl.han.oose.dea.utils;

import nl.han.oose.dea.datasource.DAO.UserDAO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import nl.han.oose.dea.dto.incoming.UserDTO;
import nl.han.oose.dea.dto.outgoing.UserResponseDTO;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class UserAuthTest {
    private UserAuth sut = new UserAuth();

    private UserDAO userDAOFixture = mock(UserDAO.class);

    private static final String TOKEN = "1234-1234-1234";

    @BeforeAll
    static void setup(TestInfo testInfo) {
        System.out.println("\033[0;1m" + testInfo.getDisplayName() + "\033[0m");
    }

    @BeforeEach
    void beforeMethod(TestInfo testInfo) {
        // Print display name
        System.out.println(testInfo.getDisplayName());

        sut.setUserDAO(userDAOFixture);
    }

    @AfterEach
    void afterMethod() {
        System.out.println("Completed!\n");
    }

    @Test
    @DisplayName("Test for generate token")
    public void generateTokenReturnsAToken() {
        /* Arrange */

        /* Act */
        String actual = sut.generateToken();

        /* Assert */
        assertNotNull(actual);
        assertTrue(actual.matches("[\\w\\-]+"));
    }

    @Test
    @DisplayName("Test to check if password is correct")
    public void checkPasswordReturnsBoolean() {
        /* Arrange */
        UserDTO user = new UserDTO("username", "password");
        UserResponseDTO userDB = new UserResponseDTO("username", TOKEN, DigestUtils.sha256Hex("password"));

        /* Act */
        boolean result = sut.checkPassword(user, userDB);

        /* Assert */
        assertTrue(result);
    }

    @Test
    @DisplayName("Test to check if token exists")
    public void testCheckIfTokenExists() {
        /* Arrange */
        String token = UUID.randomUUID().toString();
        List<String> tokens = Arrays.asList(token, "2352352362-6272-26262");
        when(userDAOFixture.getAllTokens()).thenReturn(tokens);

        /* Act */
        boolean result = sut.checkIfTokenExists(token);

        /* Assert */
        assertTrue(result);
    }
}
