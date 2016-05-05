package ua.com.juja.sqlcmd.model;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.sqlCmd.model.DataSet;
import ua.com.juja.sqlCmd.model.DataSetImpl;
import ua.com.juja.sqlCmd.model.DatabaseManager;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
        Set<String> tableNames = manager.getTableNames();
        assertEquals("[user, test]",tableNames.toString());
    }

    @Test
    public void testGetTableData(){
        //given
        manager.clear("user");
        //when
        DataSet input = new DataSetImpl();
        input.put("name", "Stiven");
        input.put("password", "pass");
        input.put("id", 13);
        manager.create("user", input);

        //then
        List<DataSet> users = manager.getTableData("user");
        assertEquals(1, users.size());

        DataSet user = users.get(0);
        assertEquals("[name, password, id]",Arrays.toString(user.getNames()));
        assertEquals("[Stiven, pass, 13]",Arrays.toString(user.getValues()));
    }

    @Test
    public void testUpdateTableData() {
        //given
        manager.clear("user");

        DataSet input = new DataSetImpl();
        input.put("name", "Stiven");
        input.put("password", "pass");
        input.put("id", 13);
        manager.create("user", input);

        //when
        DataSet newValue = new DataSetImpl();
        newValue.put("password", "pass2");
        newValue.put("name", "Pup");
        manager.update("user", 13, newValue);

        //then
        List<DataSet> users = manager.getTableData("user");
        assertEquals(1, users.size());

        DataSet user = users.get(0);
        assertEquals("[name, password, id]",Arrays.toString(user.getNames()));
        assertEquals("[Pup, pass2, 13]",Arrays.toString(user.getValues()));
    }


    @Test
    public void testGetColumnNames() {
        //given
        manager.clear("user");

        //when
        Set<String> columnNames = manager.getTableColumns("user");

        //then
        assertEquals("[name, password, id]",columnNames.toString());
    }

    @Test
    public void testIsConnected() {
        //given
        //when
        //then
        assertTrue(manager.IsConnected());
    }
}
