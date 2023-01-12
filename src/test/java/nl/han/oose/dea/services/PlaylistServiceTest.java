package nl.han.oose.dea.services;

import nl.han.oose.dea.datasource.PlaylistDAO;
import nl.han.oose.dea.datasource.UserDAO;
import nl.han.oose.dea.dto.incoming.PlaylistDTO;
import nl.han.oose.dea.dto.outgoing.PlaylistResponseDTO;
import nl.han.oose.dea.services.PlaylistService;
import nl.han.oose.dea.utils.UserAuth;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


public class PlaylistServiceTest {
    private PlaylistService sut = new PlaylistService();
    private PlaylistDAO playlistDAOFixture = mock(PlaylistDAO.class);
    private UserDAO userDAOFixture = mock(UserDAO.class);
    private UserAuth userAuthFixture = mock(UserAuth.class);

    private String token = "1234-1234-1234";
    private int user_id = 1;
    @BeforeAll
    static void setup(TestInfo testInfo) {
        System.out.println( "\033[0;1m" + testInfo.getDisplayName() + "\033[0m" );
    }

    @BeforeEach
    void beforeMethod(TestInfo testInfo) {
        // Print display name
        System.out.println(testInfo.getDisplayName());

        sut.setPlaylistDAO(playlistDAOFixture);
        sut.setUserDAO(userDAOFixture);
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
        tokens.add(token);

        //when(userAuthFixture.checkIfTokenExists(token)).thenReturn(true);
        //when(userDAOFixture.getAllTokens()).thenReturn(tokens);
        when(userDAOFixture.getUserId(token)).thenReturn(user_id);
        when(playlistDAOFixture.getAllPlaylists(user_id)).thenReturn(playlist);

        /* Act */
        PlaylistResponseDTO actual = sut.getPlaylists(token);

        /* Assert */
        assertEquals(playlist.getPlaylists(), actual.getPlaylists());
        assertEquals(playlist.getLength(), actual.getLength());
    }
}
