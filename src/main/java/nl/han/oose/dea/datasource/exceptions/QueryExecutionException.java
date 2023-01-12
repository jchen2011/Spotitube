package nl.han.oose.dea.datasource.exceptions;

import java.sql.SQLException;

public class QueryExecutionException extends SQLException {
    public QueryExecutionException(String name) {
        super(name);
    }
}
