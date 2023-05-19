package nl.han.oose.dea.datasource;

import jakarta.inject.Inject;
import nl.han.oose.dea.services.exceptions.InternalServerError;
import nl.han.oose.dea.utils.Log;
import nl.han.oose.dea.utils.PropertyLoader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 * Class for the creation of a connection to a database.
 */
public class ConnectionCreator {

    private Connection connection;
    private PropertyLoader propertyLoader;

    @Inject
    public void setPropertyLoader(PropertyLoader propertyLoader) {
        this.propertyLoader = propertyLoader;
    }

    /**
     * Method to create a {@link Connection} to a database.
     * Will throw a {@link InternalServerError} if it failed to connect.
     */
    public void connect() {
        propertyLoader.load("database.properties");
        String url = propertyLoader.getProperty("connectionString");

        try {
            connection = DriverManager.getConnection(url);
        } catch (Exception e) {
            Log.console("Failed to find database at " + url + ".", e);
            throw new InternalServerError();
        }
    }

    /**
     * Method to create a {@link PreparedStatement} from the {@link Connection}.
     * Will throw a {@link InternalServerError} if a {@link PreparedStatement} cannot be created.
     *
     * @param sql The SQL query as a {@link String}
     * @return The {@link PreparedStatement}
     */
    public PreparedStatement create(String sql) {
        try {
            return connection.prepareStatement(sql);
        } catch (Exception e) {
            Log.console("Failed to create a query to the database.", e);
            throw new InternalServerError();
        }
    }
}
