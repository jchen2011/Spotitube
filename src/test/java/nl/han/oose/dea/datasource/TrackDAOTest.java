package nl.han.oose.dea.datasource;

import nl.han.oose.dea.dto.incoming.TrackDTO;
import nl.han.oose.dea.dto.outgoing.TrackResponseDTO;
import nl.han.oose.dea.services.exceptions.InternalServerError;
import nl.han.oose.dea.utils.ResultSetCreator;
import org.junit.jupiter.api.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@DisplayName("Test for TrackDAO")
public class TrackDAOTest {
    private static final ConnectionCreator CONNECTION_CREATOR = mock(ConnectionCreator.class);
    private static final PreparedStatement PREPARED_STATEMENT = mock(PreparedStatement.class);

    private TrackDAO sut;

    @BeforeAll
    static void setup(TestInfo testInfo) {
        System.out.println("\033[0;1m" + testInfo.getDisplayName() + "\033[0m");
    }

    @BeforeEach
    void beforeMethod(TestInfo testInfo) {
        // Print display name
        System.out.println(testInfo.getDisplayName());

        // Run code before test
        sut = new TrackDAO();
        sut.setConnectionCreator(CONNECTION_CREATOR);

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
    @DisplayName("Test to add track to playlist")
    public void addTrackToPlaylist() {
        /* Arrange */
        int playlistId = 1;
        TrackDTO track = new TrackDTO(5, "Only Love Can Hurt Like This", "Paloma Faith", 251, "Sad", 100, "21-5-2002", "best song ever made", true);

        /* Act */
        assertDoesNotThrow(() -> sut.addTrackToPlaylist(playlistId, track));
        /* Assert */
    }

    @Test
    @DisplayName("Test to get all tracks by playlistId")
    public void getAllTracksByIdReturnsListOfTracks() {
        try {
            /* Arrange */
            TrackDTO track = new TrackDTO(5, "Only Love Can Hurt Like This", "Paloma Faith", 251, "Sad", 100, "21-5-2002", "best song ever made", true);

            ResultSet mockedSet = ResultSetCreator.create(
                    new String[]{"trackId", "title", "performer", "duration", "album", "playcount", "publicationDate", "description", "offlineAvailable"},
                    new Object[][]{
                            {5, "Only Love Can Hurt Like This", "Paloma Faith", 251, "Sad", 100, "21-5-2002", "best song ever made", true}
                    }
            );

            when(PREPARED_STATEMENT.executeQuery()).thenReturn(mockedSet);

            /* Act */
            List<TrackDTO> actual = sut.getAllTracksById(1);
            List<TrackDTO> expected = new ArrayList<>();

            expected.add(track);
            /* Assert */
            for (int i = 0; i < expected.size(); i++) {
                assertEquals(expected.get(i).getId(), actual.get(i).getId());
                assertEquals(expected.get(i).getTitle(), actual.get(i).getTitle());
                assertEquals(expected.get(i).getPerformer(), actual.get(i).getPerformer());
                assertEquals(expected.get(i).getDuration(), actual.get(i).getDuration());
                assertEquals(expected.get(i).getAlbum(), actual.get(i).getAlbum());
                assertEquals(expected.get(i).getPlaycount(), actual.get(i).getPlaycount());
                assertEquals(expected.get(i).getPublicationDate(), actual.get(i).getPublicationDate());
                assertEquals(expected.get(i).getDescription(), actual.get(i).getDescription());
            }
        } catch (Exception e){
            fail();
        }
    }

    @Test
    @DisplayName("Test for to get tracklist total length")
    public void getTracklistTotalLengthReturnsTotalLength() {
        try {
            /* Arrange */
            int trackLength = 500;
            ResultSet mockedSet = ResultSetCreator.create(
                    new String[]{"duration"},
                    new Object[][]{
                            {trackLength}
                    }
            );

            /* Act */
            int actual = sut.calculateTotalDuration(mockedSet);
            int expected = trackLength;

            /* Assert */
            assertEquals(expected, actual);
        } catch (Exception e){
            fail();
        }
    }

    @Test
    @DisplayName("Test to calculate total duration of a resultset")
    public void calculateTotalDurationReturnsTotalDuration() {
        try {
            /* Arrange */
            int playlistLength = 423;
            ResultSet mockedSet = ResultSetCreator.create(
                    new String[]{"duration"},
                    new Object[][]{
                            {playlistLength}
                    }
            );

            when(PREPARED_STATEMENT.executeQuery()).thenReturn(mockedSet);

            /* Act */
            int actual = sut.getTracklistTotalLength();


            /* Assert */
            assertEquals(playlistLength, actual);
        } catch (Exception e){
            fail();
        }
    }

    @Test
    @DisplayName("Test to calculate total duration returns an error")
    public void calculateTotalDurationReturnsInternalServerError() {
        /* Arrange */
        try {
            int playlistLength = 423;
            ResultSet mockedSet = ResultSetCreator.create(
                    new String[]{"duration"},
                    new Object[][]{
                            {playlistLength}
                    }
            );
            doThrow(InternalServerError.class).when(mockedSet).next();

            /* Act & Assert */
            assertThrows(InternalServerError.class, () -> sut.calculateTotalDuration(mockedSet));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    @DisplayName("Test for deleting track from a playlist")
    public void deleteTrackFromPlaylist() {
        /* Arrange */
        int playlistId = 5;
        int trackId = 4;

        /* Act */
        assertDoesNotThrow(() -> sut.deleteTrackFromPlaylist(playlistId, trackId));
        /* Assert */
    }

    @Test
    @DisplayName("Test for getting the available tracks")
    public void getAvailableTracksReturnsAllAvailableTracks() {
        try {
            /* Arrange */
            int playlistId = 5;

            ResultSet mockedSet = ResultSetCreator.create(
                    new String[]{"trackId", "title", "performer", "duration", "album", "playcount", "publicationDate", "description", "offlineAvailable"},
                    new Object[][]{
                            {1, "Only Love Can Hurt Like This", "Paloma Faith", 251, "Sad", 100, "21-5-2002", "best song ever made", true},
                            {16, "AHAHAHAH", "Justin Bieber", 350, "Hip", 100, "30-06-2022", "worst song ever made", false},
                            {17, "XXXX", "Lady Gaga", 251, "Rap", 100, "01-01-2004", "best song ever made in 2004", true}
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

            /* Act */
            TrackResponseDTO actual = sut.getAvailableTracks(playlistId);
            TrackResponseDTO expected = new TrackResponseDTO(tracks);

            /* Assert */
            assertEquals(expected.getTracks().size(), actual.getTracks().size());
        } catch (Exception e){
            fail();
        }
    }
}