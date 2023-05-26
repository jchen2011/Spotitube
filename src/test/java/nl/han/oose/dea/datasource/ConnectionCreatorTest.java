package nl.han.oose.dea.datasource;

import nl.han.oose.dea.services.exceptions.InternalServerError;
import nl.han.oose.dea.utils.PropertyLoader;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@DisplayName("Test for ConnectionCreator")
@ExtendWith(MockitoExtension.class)
public class ConnectionCreatorTest {

    @Mock
    private PropertyLoader propertyLoader;

    @Mock
    private Connection connection;

    @InjectMocks
    private ConnectionCreator sut;

    private MockedStatic<DriverManager> driverManager;

    @BeforeAll
    static void setup(TestInfo testInfo) {
        System.out.println("\033[0;1m" + testInfo.getDisplayName() + "\033[0m");
    }

    @BeforeEach
    void beforeMethod(TestInfo testInfo) {
        // Print display name
        System.out.println(testInfo.getDisplayName());

        driverManager = Mockito.mockStatic(DriverManager.class);
    }

    @AfterEach
    void afterMethod() {
        System.out.println("Completed!\n");
        driverManager.close();
    }

    @Test
    @DisplayName("Test for creating valid preparedstatement")
    void preparedStatementValid() throws SQLException {
        /* Arrange */
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(connection.prepareStatement("test")).thenReturn(preparedStatement);

        /* Act */
        PreparedStatement actual = sut.create("test");

        /* Assert */
        assertEquals(preparedStatement, actual);
    }

    @Test
    @DisplayName("Test for creating preparedstatement exception")
    void preparedStatementException() throws SQLException {
        /* Arrange */
        when(connection.prepareStatement("test")).thenThrow(InternalServerError.class);

        /* Act & Assert */
        assertThrows(InternalServerError.class, () -> {
            sut.create("test");
        });
    }

    @Test
    @DisplayName("Test for creating connection exception")
    void connectionCreateException() throws SQLException {
        /* Arrange */
        doNothing().when(propertyLoader).load("database.properties");
        when(propertyLoader.getProperty("connectionString")).thenReturn("");
        when(DriverManager.getConnection("")).thenThrow(InternalServerError.class);

        /* Act & Assert */
        assertThrows(InternalServerError.class, () -> {
            sut.connect();
        });
    }
}
