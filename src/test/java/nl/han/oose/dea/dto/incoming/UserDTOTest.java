package nl.han.oose.dea.dto.incoming;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserDTOTest {
    private UserDTO sut = new UserDTO();
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
    @DisplayName("Test for UserDTO")
    public void userDTOTest() {
        /* Arrange */
        String expectedUser = "jchen2011";
        String expectedPassword = "hallo1!";

        /* Act */
        sut.setUser(expectedUser);
        String receivedUser = sut.getUser();

        sut.setPassword(expectedPassword);
        String receivedPassword = sut.getPassword();

        /* Assert */
        assertEquals(expectedUser, receivedUser);
        assertEquals(expectedPassword, receivedPassword);
    }
}
