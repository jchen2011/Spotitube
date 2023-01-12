package nl.han.oose.dea.resource;

import jakarta.ws.rs.core.Response;
import nl.han.oose.dea.dto.incoming.UserDTO;
import nl.han.oose.dea.dto.outgoing.LoginResponseDTO;
import nl.han.oose.dea.services.LoginService;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LoginResourceTest {
    private LoginResource sut = new LoginResource();
    private LoginService fixture = mock(LoginService.class);
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

        loginResponseDTO.setUser("jchen2011");
        loginResponseDTO.setToken("1234-1234-1234");

        sut.setLoginResource(fixture);
    }

    @AfterEach
    void afterMethod() {
        System.out.println("Completed!\n");
    }

    @Test
    @DisplayName("Test the login function")
    public void testLogin() {
        /* Arrange */
        when(fixture.authenticateAndGenerateToken(userDTO)).thenReturn(loginResponseDTO);

        /* Act */
        Response expected = Response.ok(fixture.authenticateAndGenerateToken(userDTO)).build();
        Response actual = sut.login(userDTO);

        /* Assert */
        assertEquals(expected.getEntity(), actual.getEntity());
        assertEquals(expected.getStatus(), actual.getStatus());
    }
}
