package nl.han.oose.dea.resource;

import jakarta.ws.rs.core.Response;
import nl.han.oose.dea.dto.incoming.PlaylistDTO;
import nl.han.oose.dea.dto.incoming.TrackDTO;
import nl.han.oose.dea.resource.PlaylistResource;
import nl.han.oose.dea.services.PlaylistService;
import nl.han.oose.dea.dto.outgoing.PlaylistResponseDTO;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class PlaylistResourceTest {
    private PlaylistResource sut = new PlaylistResource();
    private PlaylistService fixture = mock(PlaylistService.class);
    private PlaylistResponseDTO playlistResponseDTO = new PlaylistResponseDTO();

    private TrackDTO trackDTO = new TrackDTO(1, "Song for someone", "The frames", 350, "The cost", 100, "10-2-1999", "The greatest song ever", true);
    private PlaylistDTO playlistDTO = new PlaylistDTO(1, "great playlist", true, null);
    private static final int PLAYLIST_ID = 1;
    private static final int HTTP_OK = 200;
    private static final int HTTPS_NO_CONTENT = 204;
    @BeforeAll
    static void setup(TestInfo testInfo) {
        System.out.println( "\033[0;1m" + testInfo.getDisplayName() + "\033[0m" );
    }

    @BeforeEach
    void beforeMethod(TestInfo testInfo) {
        // Print display name
        System.out.println(testInfo.getDisplayName());

        sut.setPlaylistService(fixture);
    }

    @AfterEach
    void afterMethod() {
        System.out.println("Completed!\n");
    }

    @Test
    @DisplayName("Test for get all playlists function")
    public void getAllPlaylistsReturnsObjectFromServiceAsEntity() {
        /* Arrange */
        when(fixture.getPlaylists(anyString())).thenReturn(playlistResponseDTO);

        /* Act */
        Response expected = Response.ok(fixture.getPlaylists(anyString())).build();
        Response actual = sut.getAllPlaylists("");

        /* Assert */
        assertEquals(expected.getEntity(), actual.getEntity());
        assertEquals(expected.getStatus(), actual.getStatus());
    }

    @Test
    @DisplayName("Verify get all playlists function")
    public void getAllPlaylistsCallsGetPlaylists() {
        /* Arrange */

        /* Act */
        sut.getAllPlaylists("");

        /* Assert */
        verify(fixture).getPlaylists("");
    }

    @Test
    @DisplayName("Test for delete playlist function")
    public void deletePlaylistReturnsHttps200() {
        /* Arrange */

        /* Act */
        Response actual = sut.deletePlaylist(PLAYLIST_ID, "");

        /* Assert */
        assertEquals(HTTP_OK, actual.getStatus());
    }

    @Test
    @DisplayName("Verify the delete playlist function")
    public void deletePlaylistCallsDeletePlaylistOnService() {
        /* Arrange */

        /* Act */
        fixture.deletePlaylist(PLAYLIST_ID, "");

        /* Assert */
        verify(fixture).deletePlaylist(PLAYLIST_ID, "");
    }

    @Test
    @DisplayName("Test for add playlist function")
    public void addPlaylistReturnsHttps200() {
        /* Arrange */

        /* Act */
        Response actual = sut.addPlaylist("", playlistDTO);

        /* Assert */
        assertEquals(HTTP_OK, actual.getStatus());
    }

    @Test
    @DisplayName("Verify the add playlist function")
    public void addPlaylistCallsAddPlaylistOnService() {
        /* Arrange */

        /* Act */
        fixture.addPlaylist("", playlistDTO);

        /* Assert */
        verify(fixture).addPlaylist("", playlistDTO);
    }

    @Test
    @DisplayName("Test for update playlist function")
    public void updatePlaylistReturnsHttps200() {
        /* Arrange */

        /* Act */
        Response actual = sut.updatePlaylist(PLAYLIST_ID, playlistDTO, "");

        /* Assert */
        assertEquals(HTTP_OK, actual.getStatus());
    }

    @Test
    @DisplayName("Verify the update playlist function")
    public void updatePlaylistCallsUpdatePlaylistOnService() {
        /* Arrange */

        /* Act */
        fixture.updatePlaylist(PLAYLIST_ID, playlistDTO.getName(), "");

        /* Assert */
        verify(fixture).updatePlaylist(PLAYLIST_ID, playlistDTO.getName(), "");
    }

    @Test
    @DisplayName("Test for get all tracks from playlist function")
    public void getAllTracksFromPlaylistReturnsHttps200() {
        /* Arrange */

        /* Act */
        Response actual = sut.getAllTracksFromPlaylist("", PLAYLIST_ID);

        /* Assert */
        assertEquals(HTTP_OK, actual.getStatus());
    }

    @Test
    @DisplayName("Verify the get all tracks from playlist function")
    public void getAllTracksFromPlaylistCallsGetAllTracksFromPlaylistOnService() {
        /* Arrange */

        /* Act */
        fixture.getAllTracksFromPlaylist("", PLAYLIST_ID);

        /* Assert */
        verify(fixture).getAllTracksFromPlaylist("", PLAYLIST_ID);
    }

    @Test
    @DisplayName("Test for add track to playlist")
    public void addTrackToPlaylistReturnsHttp200() {
        /* Arrange */

        /* Act */
        Response actual = sut.addTrackToPlaylist(PLAYLIST_ID, trackDTO, "");

        /* Assert */
        assertEquals(HTTP_OK, actual.getStatus());
    }

    @Test
    @DisplayName("Verify the add track to playlist function")
    public void addTrackToPlaylistCallsAddTrackToPlaylistOnService() {
        /* Arrange */

        /* Act */
        fixture.addTrackToPlaylist(PLAYLIST_ID, trackDTO, "");

        /* Assert */
        verify(fixture).addTrackToPlaylist(PLAYLIST_ID, trackDTO, "");
    }

    @Test
    @DisplayName("Test for delete track from playlist")
    public void deleteTrackFromPlaylistReturnsHttp204() {
        /* Arrange */

        /* Act */
        Response actual = sut.deleteTrackFromPlaylist(PLAYLIST_ID, trackDTO.getId(), "");

        /* Assert */
        assertEquals(HTTPS_NO_CONTENT, actual.getStatus());
    }

    @Test
    @DisplayName("Verify delete track from playlist function")
    public void deleteTrackFromPlaylistCallsDeleteTrackFromPlaylistOnService() {
        /* Arrange */

        /* Act */
        fixture.deleteTrackFromPlaylist(PLAYLIST_ID, trackDTO.getId(), "");

        /* Assert */
        verify(fixture).deleteTrackFromPlaylist(PLAYLIST_ID, trackDTO.getId(), "");
    }
}
