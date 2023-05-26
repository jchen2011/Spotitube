package nl.han.oose.dea.datasource;


import nl.han.oose.dea.dto.outgoing.LoginResponseDTO;
import nl.han.oose.dea.dto.outgoing.UserResponseDTO;
import nl.han.oose.dea.services.exceptions.InternalServerError;
import nl.han.oose.dea.utils.ResultSetCreator;
import org.junit.jupiter.api.DisplayName;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.*;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@DisplayName("Test for UserDAO")
public class UserDAOTest extends Tester {
    private static final ConnectionCreator CONNECTION_CREATOR = mock(ConnectionCreator.class);
    private static final PreparedStatement PREPARED_STATEMENT = mock(PreparedStatement.class);

    private UserDAO sut;

    @BeforeAll
    static void setup(TestInfo testInfo) {
        System.out.println("\033[0;1m" + testInfo.getDisplayName() + "\033[0m");
    }

    @BeforeEach
    void beforeMethod(TestInfo testInfo) {
        // Print display name
        System.out.println(testInfo.getDisplayName());

        // Run code before test
        sut = new UserDAO();
        sut.setConnectionCreator(CONNECTION_CREATOR);

        when(CONNECTION_CREATOR.create(anyString())).thenReturn(PREPARED_STATEMENT);
    }

    @AfterEach
    void afterMethod() {
        System.out.println("Completed!\n");
    }

    /* Arrange */
    /* Act */
    /* Assert */

    @Test
    @DisplayName("Test to get a user")
    public void getUserReturnsUser()  {
        try {
            /* Arrange */
            ResultSet mockedSet = ResultSetCreator.create(
                    new String[]{"userId", "username", "password", "token"},
                    new Object[][]{
                            {1, "jchen2011", "03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4", "79e8e7df-3cfe-44fc-a3bb-a9f0a0d9ca33"}
                    }
            );

            when(PREPARED_STATEMENT.executeQuery()).thenReturn(mockedSet);

            /* Act */
            UserResponseDTO actual = sut.getUser("jchen2011");
            UserResponseDTO expected = new UserResponseDTO("jchen2011","79e8e7df-3cfe-44fc-a3bb-a9f0a0d9ca33", "03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4");

            /* Assert */
            assertEquals(expected.getUsername(), actual.getUsername());
            assertEquals(expected.getPassword(), actual.getPassword());
            assertEquals(expected.getToken(), actual.getToken());
        } catch (Exception e){
          fail();
        }
    }

    @Test
    @DisplayName("Test to get a user that does not exist")
    public void getUserReturnsNoUser()  {
        try {
            /* Arrange */
            ResultSet mockedSet = ResultSetCreator.create(
                    new String[]{"userId", "username", "password", "token"},
                    new Object[][]{}
            );

            when(PREPARED_STATEMENT.executeQuery()).thenReturn(mockedSet);

            /* Act & Assert */
            assertThrows(InternalServerError.class, () -> sut.getUser("hahaha"));
        } catch (Exception e){
            fail();
        }
    }

    @Test
    @DisplayName("Test to add a token to an user")
    public void addTokenToUser() {
        try {
            /* Arrange */
            LoginResponseDTO user = new LoginResponseDTO();
            user.setUser("jchen2011");
            user.setToken("79e8e7df-3cfe-44fc-a3bb-a9f0a0d9ca33");

            /* Act */
            assertDoesNotThrow(() -> sut.addToken(user));
            /* Assert */
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    @DisplayName("Test to get all tokens")
    public void getAllTokensReturnsAllTokens() {
        try {
            /* Arrange */
            ResultSet mockedSet = ResultSetCreator.create(
                    new String[]{"token"},
                    new Object[][]{
                            {"79e8e7df-3cfe-44fc-a3bb-a9f0a0d9ca33"},
                            {"e0c3eacb-7e8f-4f4e-8645-6e9c3f936c25"}
                    }
            );

            when(PREPARED_STATEMENT.executeQuery()).thenReturn(mockedSet);

            /* Act */
            List<String> actual = sut.getAllTokens();
            List<String> expected = new ArrayList<>();

            expected.add("79e8e7df-3cfe-44fc-a3bb-a9f0a0d9ca33");
            expected.add("e0c3eacb-7e8f-4f4e-8645-6e9c3f936c25");

            /* Assert */
            for (int i = 0; i < expected.size(); i++) {
                assertEquals(expected.get(i), actual.get(i));
            }
        } catch (Exception e){
            fail();
        }
    }

    //TODO
    @Test
    @DisplayName("Test for to get userId")
    public void getUserIdReturnsUserId() {
        try {
            /* Arrange */
            ResultSet mockedSet = ResultSetCreator.create(
                    new String[]{"userId"},
                    new Object[][]{
                            {1}
                    }
            );

            when(PREPARED_STATEMENT.executeQuery()).thenReturn(mockedSet);

            /* Act */
            int actual = sut.getUserId("79e8e7df-3cfe-44fc-a3bb-a9f0a0d9ca33");
            int expected = mockedSet.getInt("userId");

            System.out.println(actual);
            System.out.println(mockedSet.getInt("userId"));

            /* Assert */
            assertEquals(expected, actual);
        } catch (Exception e){
            fail();
        }
    }
}
