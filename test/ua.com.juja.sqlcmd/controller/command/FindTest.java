package ua.com.juja.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import ua.com.juja.sqlCmd.controller.command.Command;
import ua.com.juja.sqlCmd.controller.command.Find;
import ua.com.juja.sqlCmd.model.DataSet;
import ua.com.juja.sqlCmd.model.DataSetImpl;
import ua.com.juja.sqlCmd.model.DatabaseManager;
import ua.com.juja.sqlCmd.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

/**
 * Created by Julia on 24.04.2016.
 */
public class FindTest {

    private DatabaseManager manager;
    private View view;
    private Command command;

    @Before
    public void setup(){
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new Find(manager, view);
    }


    @Test
    public void testPrintTableData(){
        setupTableColumns("user", "id", "name", "password");

        DataSet user1 = new DataSetImpl();
        user1.put("id", 12);
        user1.put("name", "Stiven");
        user1.put("password", "*****");

        DataSet user2 = new DataSetImpl();
        user2.put("id", 13);
        user2.put("name", "Eva");
        user2.put("password", "+++++");

        when(manager.getTableData("user")).
                thenReturn(Arrays.asList(user1, user2));

                //when
        command.process("find|user");

        //then
        shouldPrint("[---------------------------, " +
                "| id | name | password | ," +
                " ---------------------------, " +
                "| 12 | Stiven | ***** | , " +
                "| 13 | Eva | +++++ | " +
                ", ---------------------------]");
    }

    private void setupTableColumns(String tableName, String... columns) {
        when(manager.getTableColumns(tableName)).
                thenReturn(new LinkedHashSet<String>(Arrays.asList(columns)));
    }

    private void shouldPrint(String expected) {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeastOnce()).write(captor.capture());
        assertEquals(expected, captor.getAllValues().toString());
    }

    @Test
    public void testCanProcessFindWithParametersExitString() {
        //when
        boolean canProcess = command.canProcess("find|user");

        //then
        assertTrue(canProcess);
    }

    @Test
    public void testCantProcessFindWithoutParametersString() {
        //when
        boolean canProcess = command.canProcess("find");

        //then
        assertFalse(canProcess);
    }

    @Test
    public void testCantProcessQweString() {
        //when
        boolean canProcess = command.canProcess("qwe|user");

        //then
        assertFalse(canProcess);
    }

    @Test
    public void testPrintEmptyTableData(){
        //given
        setupTableColumns("user", "id", "name", "password");

        when(manager.getTableData("user")).thenReturn(new ArrayList<DataSet>());

        //when
        command.process("find|user");

        //then
        shouldPrint("[---------------------------, " +
                        "| id | name | password | ," +
                        " ---------------------------," +
                        " ---------------------------]");
    }

    @Test
    public void testErrorWhenBadCommandFormat(){
        //when
        try {
            command.process("find|user|blabla");
            fail("Expected exception");
        } catch (IllegalArgumentException e){
            //then
            assertEquals("Формат команды 'find|tableName', а ты ввел: find|user|blabla", e.getMessage());
        }
    }

    @Test
    public void testPrintTableDataWithOneColumn(){
        setupTableColumns("test", "id");

        DataSet user1 = new DataSetImpl();
        user1.put("id", 12);

        DataSet user2 = new DataSetImpl();
        user2.put("id", 13);

        when(manager.getTableData("test")).thenReturn(Arrays.asList(user1, user2));

        //when
        command.process("find|test");

        //then
        shouldPrint("[---------------------------, " +
                "| id | ," +
                " ---------------------------, " +
                "| 12 | , " +
                "| 13 | " +
                ", ---------------------------]");
    }

}
