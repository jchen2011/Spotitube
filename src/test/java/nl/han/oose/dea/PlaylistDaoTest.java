package nl.han.oose.dea;

import nl.han.oose.dea.datasource.PlaylistDAO;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

public class PlaylistDaoTest {
    private PlaylistDAO sut;

    @BeforeEach
    void setup() {
        sut = Mockito.spy(new PlaylistDAO());
    }
}
