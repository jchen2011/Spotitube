package nl.han.oose.dea.services;

import nl.han.oose.dea.datasource.TrackDAO;
import nl.han.oose.dea.dto.incoming.TrackDTO;
import nl.han.oose.dea.dto.outgoing.TrackResponseDTO;
import nl.han.oose.dea.services.exceptions.TokenDoesNotExistException;
import nl.han.oose.dea.utils.UserAuth;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TrackServiceTest {
    private TrackService sut = new TrackService();
    private UserAuth userAuthFixture = mock(UserAuth.class);
    private TrackDAO trackDAOFixture = mock(TrackDAO.class);
    private static final int PLAYLIST_ID = 1;
    private static final String TOKEN = "1234-1234-1234";

    private List<TrackDTO> tracks;

    @BeforeAll
    static void setup(TestInfo testInfo) {
        System.out.println( "\033[0;1m" + testInfo.getDisplayName() + "\033[0m" );
    }

    @BeforeEach
    void beforeMethod(TestInfo testInfo) {
        // Print display name
        System.out.println(testInfo.getDisplayName());

        this.tracks = new ArrayList<>();
        TrackDTO track = new TrackDTO(1, "Another Love", "Tom Odell", 100, "Sad", 1, "21-2-2022", "best song ever made", false);
        tracks.add(track);

        sut.setTrackService(trackDAOFixture, userAuthFixture);
    }

    @AfterEach
    void afterMethod() {
        System.out.println("Completed!\n");
    }

    @Test
    @DisplayName("Test for get available tracks")
    public void getAvailableTracksReturnTracks() {
        /* Arrange */
        TrackResponseDTO allTracks = new TrackResponseDTO(tracks);

        when(userAuthFixture.checkIfTokenExists(TOKEN)).thenReturn(true);
        when(trackDAOFixture.getAvailableTracks(PLAYLIST_ID)).thenReturn(allTracks);

        /* Act */
        TrackResponseDTO actual = sut.getAvailableTracks(PLAYLIST_ID, TOKEN);

        /* Assert */
        assertEquals(allTracks, actual);
    }

    @Test
    @DisplayName("Test for get available tracks exception")
    public void getAvailableTracksReturnsTokenDoesNotExistException() {
        /* Arrange */
        doThrow(TokenDoesNotExistException.class).when(userAuthFixture).checkIfTokenExists(TOKEN);

        /* Act & Assert */
        assertThrows(TokenDoesNotExistException.class, () -> sut.getAvailableTracks(PLAYLIST_ID, TOKEN));
    }
}
