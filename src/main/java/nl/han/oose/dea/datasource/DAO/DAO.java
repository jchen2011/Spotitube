package nl.han.oose.dea.datasource.DAO;

import jakarta.inject.Inject;
import nl.han.oose.dea.datasource.ConnectionCreator;
import nl.han.oose.dea.services.exceptions.InternalServerError;
import nl.han.oose.dea.utils.Log;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Super class for the Data Access Object.
 * This class encapsulate the most important methods for a database connection
 * and query execution.
 *
 * @param <E> The type of {@link Object} as return value
 */
public abstract class DAO<E> {

    private ConnectionCreator connectionCreator;

    private static final String FAILED_TO_RECEIVE_DATA = "Failed to receive data from ";

    @Inject
    public void setConnectionCreator(ConnectionCreator connectionCreator) {
        this.connectionCreator = connectionCreator;
    }

    /**
     * Method for the creation of a {@link PreparedStatement}.
     * Will automatically create a connection to a database.
     *
     * @param sql The SQL query as a {@link String}
     * @return A {@link PreparedStatement}
     */
    protected PreparedStatement connect(String sql) {
        connectionCreator.connect();
        return connectionCreator.create(sql);
    }

    /**
     * Method to set a parameter in a {@link PreparedStatement}.
     * Will throw {@link InternalServerError} when this method fails.
     *
     * @param statement The {@link PreparedStatement} to be set
     * @param value The {@link String}
     * @param index The index of the parameters.
     */
    protected void setString(PreparedStatement statement, String value, int index) {
        try {
            statement.setString(index, value);
        } catch (Exception e) {
            Log.console("Failed to set string.\n" + value + "\n At index: " + index + ".", e);
            throw new InternalServerError();
        }
    }

    /**
     * Method to set a parameter in a {@link PreparedStatement}.
     * Will throw {@link InternalServerError} when this method fails.
     *
     * @param statement The {@link PreparedStatement} to be set
     * @param value The {@link Integer}
     * @param index The index of the parameters.
     */
    protected void setInt(PreparedStatement statement, int value, int index) {
        try {
            statement.setInt(index, value);
        } catch (Exception e) {
            Log.console("Failed to set string.\n" + value + "\n At index: " + index + ".", e);
            throw new InternalServerError();
        }
    }

    /**
     * Method to execute the {@link PreparedStatement}.
     * This method has no return value.
     * Will throw {@link InternalServerError} when this execution fails.
     *
     * @param statement The {@link PreparedStatement} to be executed
     */
    protected void execute(PreparedStatement statement) {
        try {
            statement.execute();
        } catch (Exception e) {
            Log.console("Failed to execute query.", e);
            throw new InternalServerError();
        }
    }

    /**
     * Method to execute the {@link PreparedStatement}.
     * Will throw {@link InternalServerError} when this execution fails.
     *
     * @param statement The {@link PreparedStatement} to be executed
     * @return A {@link ResultSet}
     */
    protected ResultSet executeQuery(PreparedStatement statement) {
        try {
            return statement.executeQuery();
        } catch (Exception e) {
            Log.console("Failed to execute query.", e);
            throw new InternalServerError();
        }
    }

    /**
     * Method to get a {@link String} from a {@link ResultSet}.
     * Will throw {@link InternalServerError} when failed to receive data.
     *
     * @param set The {@link ResultSet} used to get data
     * @param column The column name in {@link ResultSet} as a {@link String}
     * @return Data as a {@link String}
     */
    protected String getString(ResultSet set, String column) {
        try {
            return set.getString(column);
        } catch (Exception e) {
            Log.console(FAILED_TO_RECEIVE_DATA + column + " with datatype String.", e);
            throw new InternalServerError();
        }
    }

    /**
     * Method to get a {@link Boolean} from a {@link ResultSet}.
     * Will throw {@link InternalServerError} when failed to receive data.
     *
     * @param set The {@link ResultSet} used to get data
     * @param column The column name in {@link ResultSet} as a {@link Boolean}
     * @return Data as a {@link Boolean}
     */
    protected boolean getBoolean(ResultSet set, String column) {
        try {
            return set.getBoolean(column);
        } catch (Exception e) {
            Log.console(FAILED_TO_RECEIVE_DATA + column + " with datatype Boolean.", e);
            throw new InternalServerError();
        }
    }

    /**
     * Method to get a {@link Integer} from a {@link ResultSet}.
     * Will throw {@link InternalServerError} when failed to receive data.
     *
     * @param set The {@link ResultSet} used to get data
     * @param column The column name in {@link ResultSet} as a {@link Integer}
     * @return Data as a {@link Integer}
     */
    protected int getInt(ResultSet set, String column) {
        try {
                return set.getInt(column);
        } catch (SQLException e) {
            Log.console(FAILED_TO_RECEIVE_DATA + column + " with datatype Integer.", e);
            throw new InternalServerError();
        }
    }

    /**
     * Method to get a {@link Integer} from a {@link ResultSet}.
     * Will throw {@link InternalServerError} when failed to receive data.
     *
     * @param set The {@link ResultSet} used to get data
     * @param column The column name in {@link ResultSet} as a {@link Integer}
     * @return Data as a {@link Integer}
     */
    protected int getIntFirstRow(ResultSet set, String column) {
        try {
            if (set.isBeforeFirst() && set.next()) {
                return set.getInt(column);
            } else {
                throw new NoSuchElementException();
            }
        } catch (SQLException e) {
            Log.console(FAILED_TO_RECEIVE_DATA + column + " with datatype Integer.", e);
            throw new InternalServerError();
        }
    }


    /**
     * Method for the conversion of a {@link ResultSet} with multiple rows.
     * Will throw {@link InternalServerError} when these conversions fail.
     *
     * @param set The {@link ResultSet} to be converted
     * @return A {@link List} of {@link Object} set in the class generic
     */
    protected synchronized List<E> convertMultiple(ResultSet set, int id) {
        List<E> results = new ArrayList<>();

        try {
            while (set.next()) {
                results.add(convert(set, id));
            }
        } catch (Exception e) {
            Log.console("Failed to execute multiple conversions.", e);
            throw new InternalServerError();
        }

        return results;
    }

    /**
     * Method for the conversion a {@link ResultSet} with one row.
     * Will throw {@link InternalServerError} when this conversion fails.
     *
     * @param set The {@link ResultSet} to be converted
     * @return A {@link Object} set in the class generic
     */
    protected synchronized E convertOne(ResultSet set, int id) {
        try {
            if (set.next()) {
                return convert(set, id);
            } else {
                throw new InternalServerError();
            }
        } catch (Exception e) {
            Log.console("Failed to execute a conversion.", e);
            throw new InternalServerError();
        }
    }

    /**
     * Abstract method for the conversion of one {@link ResultSet} row to one {@link Object}.
     *
     * @param set The {@link ResultSet} to be converted
     * @return A {@link Object} set in the class generic
     */
    protected abstract E convert(ResultSet set, int id);
}
