package ua.com.juja.sqlcmd.model;

import ua.com.juja.sqlCmd.model.DatabaseManager;
import ua.com.juja.sqlCmd.model.JDBSDatabaseManager;

/**
 * Created by Юлия on 19.04.2016.
 */
public class JDBCDatabaseManagerTest extends DatabaseManagerTest{
    @Override
    public DatabaseManager getDatabaseManager() {
        return new JDBSDatabaseManager();
    }
}
