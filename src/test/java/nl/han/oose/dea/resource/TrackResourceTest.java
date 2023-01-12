package nl.han.oose.dea.resource;

import jakarta.ws.rs.core.Response;
import nl.han.oose.dea.services.TrackService;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TrackResourceTest {
    private TrackResource sut = new TrackResource();
    private TrackService fixture = mock(TrackService.class);
    private static final int PLAYLIST_ID = 1;
    private static final int HTTP_OK = 200;
    @BeforeAll
    static void setup(TestInfo testInfo) {
        System.out.println( "\033[0;1m" + testInfo.getDisplayName() + "\033[0m" );
    }

    @BeforeEach
    void beforeMethod(TestInfo testInfo) {
        // Print display name
        System.out.println(testInfo.getDisplayName());

        sut.setTrackResource(fixture);
    }

    @AfterEach
    void afterMethod() {
        System.out.println("Completed!\n");
    }

    @Test
    @DisplayName("Test for get available tracks function")
    public void getAvailableTracksReturnsHttp200() {
        /* Arrange */

        /* Act */
        Response actual = sut.getAvailableTracks("", PLAYLIST_ID);

        /* Assert */
        assertEquals(HTTP_OK, actual.getStatus());
    }

    @Test
    @DisplayName("Verify the get available tracks function")
    public void getAvailableTracksCallsGetAvailableTracksOnService() {
        /* Arrange */

        /* Act */
        fixture.getAvailableTracks(PLAYLIST_ID, "");

        /* Assert */
        verify(fixture).getAvailableTracks(PLAYLIST_ID, "");
    }
}
