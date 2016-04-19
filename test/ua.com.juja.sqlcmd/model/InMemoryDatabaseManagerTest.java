package ua.com.juja.sqlcmd.model;

import ua.com.juja.sqlCmd.model.DatabaseManager;
import ua.com.juja.sqlCmd.model.InMemoryDatabaseManager;

/**
 * Created by Юлия on 19.04.2016.
 */
public class InMemoryDatabaseManagerTest extends DatabaseManagerTest {

    @Override
    public DatabaseManager getDatabaseManager() {
        return new InMemoryDatabaseManager();
    }


}
