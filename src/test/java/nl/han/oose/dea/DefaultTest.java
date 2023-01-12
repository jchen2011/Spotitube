package nl.han.oose.dea;

import org.junit.jupiter.api.*;

public class DefaultTest {
    /* Arrange */

    /* Act */

    /* Assert */

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
    @DisplayName("Placeholder")
    public void placeholderTest() {
        /* Arrange */

        /* Act */

        /* Assert */
    }
}
