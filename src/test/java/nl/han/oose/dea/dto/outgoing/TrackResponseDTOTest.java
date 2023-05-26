package nl.han.oose.dea.dto.outgoing;

import nl.han.oose.dea.dto.incoming.TrackDTO;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TrackResponseDTOTest {
    private TrackResponseDTO sut = new TrackResponseDTO();
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
    @DisplayName("Test for TrackResponseDTO")
    public void trackResponseDTOTest() {
        /* Arrange */
        List<TrackDTO> expectedTracks = new ArrayList<>();
        TrackDTO trackDTO = new TrackDTO(1, "Song for someone", "The frames", 350, "The cost", 100, "10-2-1999", "The greatest song ever", true);
        expectedTracks.add(trackDTO);

        /* Act */
        sut.setTracks(expectedTracks);
        List<TrackDTO> receivedTracks = sut.getTracks();

        /* Assert */
        assertEquals(expectedTracks, receivedTracks);
    }
}
