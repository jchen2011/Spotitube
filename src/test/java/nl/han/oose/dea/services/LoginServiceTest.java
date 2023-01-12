package nl.han.oose.dea.services;

import nl.han.oose.dea.datasource.UserDAO;
import nl.han.oose.dea.dto.incoming.UserDTO;
import nl.han.oose.dea.dto.outgoing.LoginResponseDTO;
import nl.han.oose.dea.dto.outgoing.UserResponseDTO;
import nl.han.oose.dea.services.exceptions.AuthenticationException;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class LoginServiceTest {
    private LoginService sut = new LoginService();
    private UserDAO fixture = mock(UserDAO.class);

    private UserDTO userDTO = new UserDTO();
    private LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
    @BeforeAll
    static void setup(TestInfo testInfo) {
        System.out.println( "\033[0;1m" + testInfo.getDisplayName() + "\033[0m" );
    }

    @BeforeEach
    void beforeMethod(TestInfo testInfo) {
        // Print display name
        System.out.println(testInfo.getDisplayName());

        userDTO.setUser("jchen2011");
        userDTO.setPassword("1234");

        sut.setLoginService(fixture);
    }

    @AfterEach
    void afterMethod() {
        System.out.println("Completed!\n");
    }

    @Test
    @DisplayName("Test for authenticate and generate token")
    public void authenticateAndGenerateTokenTest() {
        /* Arrange */
        UserResponseDTO userDB = new UserResponseDTO();
        userDB.setUsername("jchen2011");
        userDB.setPassword("03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4");

        when(fixture.getUser(userDTO.getUser())).thenReturn(userDB);

        /* Act */
        LoginResponseDTO actual = sut.authenticateAndGenerateToken(userDTO);

        /* Assert */
        assertEquals(userDTO.getUser(), actual.getUser());
        assertNotNull(actual.getToken());
    }

    @Test
    @DisplayName("Test for authenticate and generate token exception")
    public void authenticateAndGenerateTokenReturnsAuthenticationException() {
        /* Arrange */
        doThrow(AuthenticationException.class).when(fixture).getUser(userDTO.getUser());

        /* Act & Assert */
        assertThrows(AuthenticationException.class, () -> sut.authenticateAndGenerateToken(userDTO));
    }
}
