package nl.han.oose.dea.dto.incoming;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class TrackDTOTest {
    private TrackDTO sut = new TrackDTO();
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
    @DisplayName("Test for TrackDTO")
    public void trackDTOTest() {
        /* Arrange */
        int expectedId = 1;
        String expectedTitle = "Song for someone";
        String expectedPerformer = "The frames";
        int expectedDuration = 350;
        String expectedAlbum = "The cost";
        int expectedPlaycount = 100;
        String expectedPublicationDate = "10-2-1999";
        String expectedDescription = "The greatest song ever";
        boolean expectedOfflineAvailable = true;

        /* Act */
        sut.setId(expectedId);
        int receivedId = sut.getId();

        sut.setTitle(expectedTitle);
        String receivedTitle = sut.getTitle();

        sut.setPerformer(expectedPerformer);
        String receivedPerformer = sut.getPerformer();

        sut.setDuration(expectedDuration);
        int receivedDuration = sut.getDuration();

        sut.setAlbum(expectedAlbum);
        String receivedAlbum = sut.getAlbum();

        sut.setPlaycount(expectedPlaycount);
        int receivedPlaycount = sut.getPlaycount();

        sut.setPublicationDate(expectedPublicationDate);
        String receivedPublicationDate = sut.getPublicationDate();

        sut.setDescription(expectedDescription);
        String receivedDescription = sut.getDescription();

        sut.setOfflineAvailable(expectedOfflineAvailable);
        boolean receivedOfflineAvailable = sut.isOfflineAvailable();

        /* Assert */
        assertEquals(expectedId, receivedId);
        assertEquals(expectedTitle, receivedTitle);
        assertEquals(expectedPerformer, receivedPerformer);
        assertEquals(expectedDuration, receivedDuration);
        assertEquals(expectedAlbum, receivedAlbum);
        assertEquals(expectedPlaycount, receivedPlaycount);
        assertEquals(expectedPublicationDate, receivedPublicationDate);
        assertEquals(expectedDescription, receivedDescription);
        assertEquals(expectedOfflineAvailable, receivedOfflineAvailable);
    }
}
