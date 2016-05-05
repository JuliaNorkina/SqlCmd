package ua.com.juja.sqlCmd.model;

import java.util.Set;

/**
 * Created by Юлия on 19.04.2016.
 */
public interface DatabaseManager {
    DataSet[] getTableData(String tableName);

    Set<String> getTableNames();

    void connect(String database, String userName, String password);

    void create(String tableName, DataSet input);

    void clear(String tableName);

    void update(String tableName, int id, DataSet newValue);

    Set<String> getTableColumns(String tableName);

    boolean IsConnected();
}
