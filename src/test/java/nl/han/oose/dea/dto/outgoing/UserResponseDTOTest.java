package nl.han.oose.dea.dto.outgoing;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserResponseDTOTest {
    private UserResponseDTO sut = new UserResponseDTO();
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
    @DisplayName("Test for UserResponseDTO")
    public void userResponseDTOTest() {
        /* Arrange */
        String expectedUsername = "jchen2011";
        String expectedToken = "1234-1234-1234";
        String expectedPassword = "hallo1!";

        /* Act */
        sut.setUsername(expectedUsername);
        String receivedUsername = sut.getUsername();

        sut.setToken(expectedToken);
        String receivedToken = sut.getToken();

        sut.setPassword(expectedPassword);
        String receivedPassword = sut.getPassword();

        /* Assert */
        assertEquals(expectedUsername, receivedUsername);
        assertEquals(expectedToken, receivedToken);
        assertEquals(expectedPassword, receivedPassword);
    }
}
