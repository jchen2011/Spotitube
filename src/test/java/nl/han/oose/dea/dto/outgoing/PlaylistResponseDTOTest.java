package nl.han.oose.dea.dto.outgoing;

import nl.han.oose.dea.dto.incoming.PlaylistDTO;
import nl.han.oose.dea.dto.incoming.TrackDTO;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlaylistResponseDTOTest {
    private PlaylistResponseDTO sut = new PlaylistResponseDTO();
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
    @DisplayName("Test for PlaylistResponseDTO")
    public void playlistResponseDTOTest() {
        /* Arrange */
        List<PlaylistDTO> expectedPlaylists = new ArrayList<>();

        int playlistId = 1;
        String playlistName = "great playlist";
        boolean playlistOwner = true;
        List<TrackDTO> playlistTracks = new ArrayList<>();
        TrackDTO trackDTO = new TrackDTO(1, "Song for someone", "The frames", 350, "The cost", 100, "10-2-1999", "The greatest song ever", true);
        playlistTracks.add(trackDTO);

        expectedPlaylists.add(new PlaylistDTO(playlistId, playlistName, playlistOwner, playlistTracks));

        int expectedLength = 10;

        /* Act */
        sut.setPlaylists(expectedPlaylists);
        List<PlaylistDTO> receivedPlaylists = sut.getPlaylists();

        sut.setLength(expectedLength);
        int receivedLength = sut.getLength();

        /* Assert */
        assertEquals(expectedPlaylists, receivedPlaylists);
        assertEquals(expectedLength, receivedLength);
    }
}
