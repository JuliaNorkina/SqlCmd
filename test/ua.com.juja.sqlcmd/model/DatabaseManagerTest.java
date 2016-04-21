package ua.com.juja.sqlcmd.model;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.sqlCmd.model.DataSet;
import ua.com.juja.sqlCmd.model.DatabaseManager;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Created by Юлия on 10.04.2016.
 */
public abstract class DatabaseManagerTest {

    private DatabaseManager manager;

    public abstract DatabaseManager getDatabaseManager();

    @Before
    public void setup() {
        manager = getDatabaseManager();
        manager.connect("sqlcmd", "postgres", "12345678");
    }

    @Test
    public void testGetAllTableNames(){
        String[] tableNames = manager.getTableNames();
        assertEquals("[user]", Arrays.toString(tableNames));
    }

    @Test
    public void testGetTableData(){
        //given
        manager.clear("user");
        //when
        DataSet input = new DataSet();
        input.put("name", "Stiven");
        input.put("password", "pass");
        input.put("id", 13);
        manager.create("user", input);

        //then
        DataSet[] users = manager.getTableData("user");
        assertEquals(1, users.length);

        DataSet user = users[0];
        assertEquals("[name, password, id]",Arrays.toString(user.getNames()));
        assertEquals("[Stiven, pass, 13]",Arrays.toString(user.getValues()));
    }

    @Test
    public void testUpdateTableData() {
        //given
        manager.clear("user");

        DataSet input = new DataSet();
        input.put("name", "Stiven");
        input.put("password", "pass");
        input.put("id", 13);
        manager.create("user", input);

        //when
        DataSet newValue = new DataSet();
        newValue.put("password", "pass2");
        newValue.put("name", "Pup");
        manager.update("user", 13, newValue);

        //then
        DataSet[] users = manager.getTableData("user");
        assertEquals(1, users.length);

        DataSet user = users[0];
        assertEquals("[name, password, id]",Arrays.toString(user.getNames()));
        assertEquals("[Pup, pass2, 13]",Arrays.toString(user.getValues()));
    }


    @Test
    public void testGetColumnNames() {
        //given
        manager.clear("user");

        //when
        String[] columnNames = manager.getTableColumns("user");

        //then
        assertEquals("[name, password, id]", Arrays.toString(columnNames));
    }
}