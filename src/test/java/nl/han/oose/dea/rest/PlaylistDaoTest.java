package nl.han.oose.dea.rest;

import nl.han.oose.dea.rest.datasource.PlaylistDAO;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

public class PlaylistDaoTest {
    private PlaylistDAO sut;

    @BeforeEach
    void setup() {
        sut = Mockito.spy(new PlaylistDAO());
    }
}
