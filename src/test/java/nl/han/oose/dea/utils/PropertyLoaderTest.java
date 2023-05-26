package nl.han.oose.dea.utils;

import nl.han.oose.dea.datasource.Tester;
import nl.han.oose.dea.services.exceptions.InternalServerError;
import org.junit.jupiter.api.*;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
@DisplayName( "Tests for PropertyLoader" )
public class PropertyLoaderTest extends Tester<PropertyLoader> {

    private static final Properties PROPERTIES = mock(Properties.class);

    @BeforeAll
    static void setup(TestInfo testInfo) {

        System.out.println("\033[0;1m" + testInfo.getDisplayName() + "\033[0m");
    }

    @BeforeEach
    void beforeMethod(TestInfo testInfo) {
        // Print display name
        System.out.println( testInfo.getDisplayName());

        // Run code before test
        sut = new PropertyLoader();
    }

    @AfterEach
    void afterMethod() {

        System.out.println("Completed!\n");
    }

    @Test
    @DisplayName("Test: Set property filename")
    void test_setPropertyFileName() {

        /* Arrange */
        sut.setProperty(PROPERTIES);
        String propertyFileName = "lol";

        /* Act */
        sut.load(propertyFileName);

        /* Assert */
    }

    @Test
    @DisplayName("Test: Set property filename with incorrect name")
    void test_setPropertyFileName_incorrectName() {

        /* Arrange */
        String propertyFileName = "unknown";

        /* Act */
        assertThrows(InternalServerError.class, () -> sut.load(propertyFileName));

        /* Assert */
    }

    @Test
    @DisplayName("Test: get property with key")
    void test_getProperty() {

        /* Arrange */
        sut.setProperty(PROPERTIES);

        String key = "real";
        String value = "Super key";

        when(PROPERTIES.getProperty(key )).thenReturn(value);

        /* Act */
        String actual = sut.getProperty(key);

        /* Assert */
        assertEquals(value, actual);
    }

    @Test
    @DisplayName( "Test: get property with incorrect key" )
    void test_getProperty_incorrectKey() {

        /* Arrange */
        sut.setProperty(PROPERTIES);

        String key = "fake";

        when(PROPERTIES.getProperty(key)).thenReturn(null);

        /* Act & Assert */
        assertThrows(InternalServerError.class, () -> sut.getProperty( key ));
    }

}
