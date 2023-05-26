package nl.han.oose.dea.resource.exceptionmappers;
import jakarta.ws.rs.core.Response;
import nl.han.oose.dea.services.exceptions.InternalServerError;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName( "Tests for InternalServerErrorMapper" )
public class InternalServerErrorMapperTest {

    private final InternalServerErrorMapper sut = new InternalServerErrorMapper();

    @BeforeAll
    static void setup(TestInfo testInfo) {
        System.out.println("\033[0;1m" + testInfo.getDisplayName() + "\033[0m");
    }

    @BeforeEach
    void beforeMethod(TestInfo testInfo) {
        // Print display name
        System.out.println(testInfo.getDisplayName());

        // Run code before test

    }

    @AfterEach
    void afterMethod() {
        System.out.println("Completed!\n");
    }

    @Test
    @DisplayName( "Test the toResponse" )
    void toResponse() {

        /* Arrange */
        InternalServerError internalServerError = new InternalServerError();

        /* Act */
        Response actual = sut.toResponse( internalServerError );

        /* Assert */
        assertEquals( 500, actual.getStatus() );

    }

}
