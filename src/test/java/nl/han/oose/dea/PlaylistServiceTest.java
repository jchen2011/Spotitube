package nl.han.oose.dea;

import nl.han.oose.dea.datasource.PlaylistDAO;
import nl.han.oose.dea.datasource.UserDAO;
import nl.han.oose.dea.services.PlaylistService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

public class PlaylistServiceTest {
    private PlaylistService sut;
    private PlaylistDAO mockPlaylistDAO;
    private UserDAO mockUserDAO;

    @BeforeEach
    void setup() {
        this.sut = new PlaylistService();

        mockPlaylistDAO = mock(PlaylistDAO.class);
        mockUserDAO = mock(UserDAO.class);

        sut.setPlaylistDAO(mockPlaylistDAO);
        sut.setUserDAO(mockUserDAO);
    }

    @Test
    void getAllPlaylistsFromDatabase() {
        // Arrange

        //Act

        //Assert
    }
}
