package nl.han.oose.dea.datasource;

import jakarta.inject.Inject;
import nl.han.oose.dea.datasource.exceptions.QueryExecutionException;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class DAO {
    private Logger logger = Logger.getLogger(getClass().getName());
    private DatabaseProperties databaseProperties;

    @Inject
    public void setDatabaseProperties(DatabaseProperties databaseProperties) {
        this.databaseProperties = databaseProperties;
    }

    public ResultSet executeQuery(String query) throws QueryExecutionException {
        try(Connection connection = DriverManager.getConnection(databaseProperties.connectionString())) {
            PreparedStatement statement = connection.prepareStatement(query);
            return statement.executeQuery();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Can't execute query", e);
            throw new QueryExecutionException("Unable to execute query");
        }
    }
}
