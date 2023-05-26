package nl.han.oose.dea.services;

import nl.han.oose.dea.datasource.PlaylistDAO;
import nl.han.oose.dea.datasource.TrackDAO;
import nl.han.oose.dea.datasource.UserDAO;
import nl.han.oose.dea.dto.incoming.PlaylistDTO;
import nl.han.oose.dea.dto.incoming.TrackDTO;
import nl.han.oose.dea.dto.outgoing.PlaylistResponseDTO;
import nl.han.oose.dea.dto.outgoing.TrackResponseDTO;
import nl.han.oose.dea.services.exceptions.TokenDoesNotExistException;
import nl.han.oose.dea.utils.UserAuth;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class PlaylistServiceTest {
    private PlaylistService sut = new PlaylistService();
    private PlaylistDAO playlistDAOFixture = mock(PlaylistDAO.class);
    private UserDAO userDAOFixture = mock(UserDAO.class);
    private UserAuth userAuthFixture = mock(UserAuth.class);
    private TrackDAO trackDAOFixture = mock(TrackDAO.class);

    private static final String TOKEN = "1234-1234-1234";
    private static final int USER_ID = 1;
    private static final int PLAYLIST_ID = 1;
    private List<TrackDTO> tracks;


    @BeforeAll
    static void setup(TestInfo testInfo) {
        System.out.println("\033[0;1m" + testInfo.getDisplayName() + "\033[0m");
    }

    @BeforeEach
    void beforeMethod(TestInfo testInfo) {
        // Print display name
        System.out.println(testInfo.getDisplayName());

        this.tracks = new ArrayList<>();
        TrackDTO track = new TrackDTO(1, "Another Love", "Tom Odell", 100, "Sad", 1, "21-2-2022", "best song ever made", false);
        tracks.add(track);

        sut.setPlaylistDAO(playlistDAOFixture);
        sut.setUserDAO(userDAOFixture);
        sut.setUserAuth(userAuthFixture);
        sut.setTrackDAO(trackDAOFixture);
    }

    @AfterEach
    void afterMethod() {
        System.out.println("Completed!\n");
    }

    @Test
    @DisplayName("Test for get playlists")
    public void getPlaylistReturnsAPlaylist() {
        /* Arrange */
        PlaylistResponseDTO playlist = new PlaylistResponseDTO();

        List<PlaylistDTO> playlists = new ArrayList<>();
        PlaylistDTO playlistDTO = new PlaylistDTO(1, "great playlist", true, null);

        playlists.add(playlistDTO);
        int length = 100;

        playlist.setPlaylists(playlists);
        playlist.setLength(length);

        List<String> tokens = new ArrayList<>();
        tokens.add(TOKEN);

        when(userAuthFixture.checkIfTokenExists(TOKEN)).thenReturn(true);
        when(userDAOFixture.getUserId(TOKEN)).thenReturn(USER_ID);
        when(playlistDAOFixture.getAllPlaylists(USER_ID)).thenReturn(playlist);

        /* Act */
        PlaylistResponseDTO actual = sut.getPlaylists(TOKEN);

        /* Assert */
        assertEquals(playlist.getPlaylists(), actual.getPlaylists());
        assertEquals(playlist.getLength(), actual.getLength());
    }

    @Test
    @DisplayName("Test for get playlist exception")
    public void getPlaylistReturnsTokenDoesNotExistException() {
        /* Arrange */
        doThrow(TokenDoesNotExistException.class).when(userAuthFixture).checkIfTokenExists(TOKEN);

        /* Act & Assert */
        assertThrows(TokenDoesNotExistException.class, () -> sut.getPlaylists(TOKEN));
    }

    @Test
    @DisplayName("Test for add playlist")
    public void addPlaylistReturnsAPlaylist() {
        /* Arrange */
        PlaylistResponseDTO playlist = new PlaylistResponseDTO();

        PlaylistDTO playlistDTO = new PlaylistDTO(1, "new playlist", true, null);

        when(userAuthFixture.checkIfTokenExists(TOKEN)).thenReturn(true);
        when(userDAOFixture.getUserId(TOKEN)).thenReturn(USER_ID);
        when(playlistDAOFixture.getAllPlaylists(USER_ID)).thenReturn(playlist);

        /* Act */
        PlaylistResponseDTO actual = sut.addPlaylist(TOKEN, playlistDTO);

        /* Assert */
        assertEquals(playlist, actual);
    }

    @Test
    @DisplayName("Test for add playlist exception")
    public void addPlaylistReturnsTokenDoesNotExistException() {
        /* Arrange */
        PlaylistDTO playlistDTO = new PlaylistDTO();
        doThrow(TokenDoesNotExistException.class).when(userAuthFixture).checkIfTokenExists(TOKEN);

        /* Act & Assert */
        assertThrows(TokenDoesNotExistException.class, () -> sut.addPlaylist(TOKEN, playlistDTO));
    }

    @Test
    @DisplayName("Test for delete playlist")
    public void deletePlaylistReturnsAPlaylist() {
        /* Arrange */
        PlaylistResponseDTO playlist = new PlaylistResponseDTO();

        when(userAuthFixture.checkIfTokenExists(TOKEN)).thenReturn(true);
        when(userDAOFixture.getUserId(TOKEN)).thenReturn(USER_ID);
        when(playlistDAOFixture.getAllPlaylists(USER_ID)).thenReturn(playlist);

        /* Act */
        PlaylistResponseDTO actual = sut.deletePlaylist(PLAYLIST_ID, TOKEN);

        /* Assert */
        assertEquals(playlist, actual);
    }

    @Test
    @DisplayName("Test for delete playlist exception")
    public void deletePlaylistReturnsTokenDoesNotExistException() {
        /* Arrange */
        doThrow(TokenDoesNotExistException.class).when(userAuthFixture).checkIfTokenExists(TOKEN);

        /* Act & Assert */
        assertThrows(TokenDoesNotExistException.class, () -> sut.deletePlaylist(PLAYLIST_ID, TOKEN));
    }

    @Test
    @DisplayName("Test for update playlist")
    public void updatePlaylistReturnsAPlaylist() {
        /* Arrange */
        PlaylistResponseDTO playlist = new PlaylistResponseDTO();

        String playlistName = "new name";

        when(userAuthFixture.checkIfTokenExists(TOKEN)).thenReturn(true);
        when(userDAOFixture.getUserId(TOKEN)).thenReturn(USER_ID);
        when(playlistDAOFixture.getAllPlaylists(USER_ID)).thenReturn(playlist);

        /* Act */
        PlaylistResponseDTO actual = sut.updatePlaylist(PLAYLIST_ID, playlistName, TOKEN);

        /* Assert */
        assertEquals(playlist, actual);
    }

    @Test
    @DisplayName("Test for update playlist exception")
    public void updatePlaylistReturnsTokenDoesNotExistException() {
        /* Arrange */
        String playlistName = "new name";
        doThrow(TokenDoesNotExistException.class).when(userAuthFixture).checkIfTokenExists(TOKEN);

        /* Act & Assert */
        assertThrows(TokenDoesNotExistException.class, () -> sut.updatePlaylist(PLAYLIST_ID, playlistName, TOKEN));
    }

    @Test
    @DisplayName("Test for get all tracks from playlist")
    public void getAllTracksFromPlaylistReturnsTracks() {
        /* Arrange */
        TrackResponseDTO allTracks = new TrackResponseDTO(tracks);

        when(userAuthFixture.checkIfTokenExists(TOKEN)).thenReturn(true);
        when(trackDAOFixture.getAllTracksById(PLAYLIST_ID)).thenReturn(tracks);

        /* Act */
        TrackResponseDTO actual = sut.getAllTracksFromPlaylist(TOKEN, PLAYLIST_ID);

        /* Assert */
        assertEquals(allTracks.getTracks(), actual.getTracks());
    }

    @Test
    @DisplayName("Test for get all tracks from playlist exception")
    public void getAllTracksFromPlaylistReturnsTokenDoesNotExistException() {
        /* Arrange */
        doThrow(TokenDoesNotExistException.class).when(userAuthFixture).checkIfTokenExists(TOKEN);

        /* Act & Assert */
        assertThrows(TokenDoesNotExistException.class, () -> sut.getAllTracksFromPlaylist(TOKEN, PLAYLIST_ID));
    }

    @Test
    @DisplayName("Test for delete track from playlist")
    public void deleteTrackFromPlaylistReturnsTracks() {
        /* Arrange */
        TrackResponseDTO allTracks = new TrackResponseDTO(tracks);
        int trackId = 1;

        when(userAuthFixture.checkIfTokenExists(TOKEN)).thenReturn(true);
        when(trackDAOFixture.getAllTracksById(PLAYLIST_ID)).thenReturn(tracks);

        /* Act */
        TrackResponseDTO actual = sut.deleteTrackFromPlaylist(PLAYLIST_ID, trackId, TOKEN);

        /* Assert */
        assertEquals(allTracks.getTracks(), actual.getTracks());
    }

    @Test
    @DisplayName("Test for delete track from playlist exception")
    public void deleteTrackFromPlaylistReturnsTokenDoesNotExist() {
        /* Arrange */
        int trackId = 1;
        doThrow(TokenDoesNotExistException.class).when(userAuthFixture).checkIfTokenExists(TOKEN);

        /* Act & Assert */
        assertThrows(TokenDoesNotExistException.class, () -> sut.deleteTrackFromPlaylist(PLAYLIST_ID, trackId, TOKEN));
    }

    @Test
    @DisplayName("Test for add track to playlist")
    public void addTrackToPlaylistReturnsTracks() {
        /* Arrange */
        TrackResponseDTO allTracks = new TrackResponseDTO(tracks);
        TrackDTO track = new TrackDTO(5, "Only Love Can Hurt Like This", "Paloma Faith", 251, "Sad", 100, "21-5-2002", "best song ever made", true);

        when(userAuthFixture.checkIfTokenExists(TOKEN)).thenReturn(true);
        when(trackDAOFixture.getAllTracksById(PLAYLIST_ID)).thenReturn(tracks);

        /* Act */
        TrackResponseDTO actual = sut.addTrackToPlaylist(PLAYLIST_ID, track, TOKEN);

        /* Assert */
        assertEquals(allTracks.getTracks(), actual.getTracks());
    }

    @Test
    @DisplayName("Test for add track to playlist exception")
    public void addTrackToPlaylistReturnsTokenDoesNotExist() {
        /* Arrange */
        TrackDTO track = new TrackDTO(10, "Elastic Heart", "Sia", 10, "Sad", 1000, "15-9-2012", "best song ever made", true);
        doThrow(TokenDoesNotExistException.class).when(userAuthFixture).checkIfTokenExists(TOKEN);

        /* Act & Assert */
        assertThrows(TokenDoesNotExistException.class, () -> sut.addTrackToPlaylist(PLAYLIST_ID, track, TOKEN));
    }
}