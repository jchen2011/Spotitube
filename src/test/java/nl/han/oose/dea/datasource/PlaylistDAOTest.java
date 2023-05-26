package nl.han.oose.dea.datasource;

import nl.han.oose.dea.dto.incoming.PlaylistDTO;
import nl.han.oose.dea.dto.incoming.TrackDTO;
import nl.han.oose.dea.dto.outgoing.PlaylistResponseDTO;
import nl.han.oose.dea.utils.ResultSetCreator;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("Test for PlaylistDAO")
public class PlaylistDAOTest {
    private static final ConnectionCreator CONNECTION_CREATOR = mock(ConnectionCreator.class);
    private static final PreparedStatement PREPARED_STATEMENT = mock(PreparedStatement.class);

    private PlaylistDAO sut;
    private TrackDAO trackDAOFixture;

    @BeforeAll
    static void setup(TestInfo testInfo) {
        System.out.println("\033[0;1m" + testInfo.getDisplayName() + "\033[0m");
    }

    @BeforeEach
    void beforeMethod(TestInfo testInfo) {
        // Print display name
        System.out.println(testInfo.getDisplayName());

        // Run code before test
        sut = new PlaylistDAO();
        sut.setConnectionCreator(CONNECTION_CREATOR);

        trackDAOFixture = new TrackDAO();
        sut.setTrackDAO(trackDAOFixture);

        when(CONNECTION_CREATOR.create(anyString())).thenReturn(PREPARED_STATEMENT);
    }

    @AfterEach
    void afterMethod() {
        System.out.println("Completed!\n");
    }

    /* Arrange */
    /* Act */
    /* Assert */

    @Test
    @DisplayName("Test for to get all playlists")
    public void getAllPlaylistsReturnsAllPlaylist() {
        try {
            /* Arrange */
            sut.setConnectionCreator(CONNECTION_CREATOR);

            int userId = 1;
            int length = 500;

            ResultSet mockedSet = ResultSetCreator.create(
                    new String[]{"playlistId", "name", "owner"},
                    new Object[][]{
                            {1, "heyhyy", 1},
                            {7, "dagagaga", 1},
                            {9, "ye", 1}
                    }
            );

            when(PREPARED_STATEMENT.executeQuery()).thenReturn(mockedSet);

            TrackDTO paloma_faith = new TrackDTO(1, "Only Love Can Hurt Like This", "Paloma Faith", 251, "Sad", 100, "21-5-2002", "best song ever made", true);
            TrackDTO justin_bieber = new TrackDTO(16, "AHAHAHAH", "Justin Bieber", 350, "Hip", 100, "30-06-2022", "worst song ever made", false);
            TrackDTO lady_gaga = new TrackDTO(17, "XXXX", "Lady Gaga", 251, "Rap", 100, "01-01-2004", "best song ever made in 2004", true);

            List<TrackDTO> tracks = new ArrayList<>();
            tracks.add(paloma_faith);
            tracks.add(justin_bieber);
            tracks.add(lady_gaga);

            PlaylistDTO playlist1 = new PlaylistDTO(1, "heyhyy", true, tracks);
            PlaylistDTO playlist2 = new PlaylistDTO(7, "dagagaga", true, tracks);
            PlaylistDTO playlist3 = new PlaylistDTO(9, "ye", true, tracks);

            List<PlaylistDTO> playlists = new ArrayList<>();
            playlists.add(playlist1);
            playlists.add(playlist2);
            playlists.add(playlist3);


            /* Act */
            PlaylistResponseDTO actual = sut.getAllPlaylists(userId);
            PlaylistResponseDTO expected = new PlaylistResponseDTO(playlists, length);

            /* Assert */
            assertEquals(expected.getPlaylists(), actual.getPlaylists());
            assertEquals(expected.getLength(), actual.getLength());
        } catch(Exception e) {
            fail();
        }
    }


}