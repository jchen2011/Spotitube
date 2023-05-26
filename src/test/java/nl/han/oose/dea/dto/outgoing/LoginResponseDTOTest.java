package nl.han.oose.dea.dto.outgoing;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class LoginResponseDTOTest {
    private LoginResponseDTO sut = new LoginResponseDTO();
    @BeforeAll
    static void setup(TestInfo testInfo) {
        System.out.println( "\033[0;1m" + testInfo.getDisplayName() + "\033[0m" );
    }

    @BeforeEach
    void beforeMethod(TestInfo testInfo) {
        // Print display name
        System.out.println(testInfo.getDisplayName());
    }

    @AfterEach
    void afterMethod() {
        System.out.println("Completed!\n");
    }

    @Test
    @DisplayName("Test for LoginResponseDTO")
    public void loginResponseDTOTest() {
        /* Arrange */
        String expectedUser = "jchen2011";
        String expectedToken = "1234-1234-1234";

        /* Act */
        sut.setUser(expectedUser);
        String receivedUser = sut.getUser();

        sut.setToken(expectedToken);
        String receivedToken = sut.getToken();

        /* Assert */
        assertEquals(expectedUser, receivedUser);
        assertEquals(expectedToken, receivedToken);
    }
}
