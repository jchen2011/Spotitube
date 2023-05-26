package nl.han.oose.dea.utils;

import org.mockito.stubbing.Answer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ResultSetCreator {

    private final String[] columnNames;
    private final Object[][] data;
    private int rowIndex;

    private ResultSetCreator(String[] columnNames, Object[][] data) {
        this.columnNames = columnNames;
        this.data = data;
        this.rowIndex = -1;
    }

    private ResultSet buildMock() throws SQLException {
        ResultSet mockResultSet = mock(ResultSet.class);

        Answer<?> getDataWithString = invocation -> {
            String columnName = invocation.getArgument(0, String.class);
            int columnIndex = Arrays.asList(columnNames).indexOf(columnName);
            return data[rowIndex][columnIndex];
        };

        // mock .getString(columnName)
        when(mockResultSet.getString(anyString())).then(getDataWithString);

        // mock .getInt(columnName)
        when(mockResultSet.getInt(anyString())).then(getDataWithString);

        // mock .getFloat(columnName)
        when(mockResultSet.getFloat(anyString())).then(getDataWithString);

        // mock .next()
        when(mockResultSet.next()).then(invocation -> ++rowIndex < data.length);

        return mockResultSet;
    }

    public static ResultSet create(String[] columnNames, Object[][] data) throws SQLException {
        return new ResultSetCreator(columnNames, data).buildMock();
    }
}
