package ua.com.juja.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import ua.com.juja.sqlCmd.controller.command.Command;
import ua.com.juja.sqlCmd.controller.command.Find;
import ua.com.juja.sqlCmd.model.DataSet;
import ua.com.juja.sqlCmd.model.DatabaseManager;
import ua.com.juja.sqlCmd.view.View;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created by Julia on 24.04.2016.
 */
public class FindTest {

    private DatabaseManager manager;
    private View view;

    @Before
    public void setup(){
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
    }


    @Test
    public void testPrintTableData(){
        //given
        Command command = new Find(manager, view);
        when(manager.getTableColumns("user")).
                thenReturn(new String[]{"id", "name", "password"});

        DataSet user1 = new DataSet();
        user1.put("id", 12);
        user1.put("name", "Stiven");
        user1.put("password", "*****");

        DataSet user2 = new DataSet();
        user2.put("id", 13);
        user2.put("name", "Eva");
        user2.put("password", "+++++");

        DataSet[] data = new DataSet[] {user1, user2};
        when(manager.getTableData("user")).
                thenReturn(data);

                //when
        command.process("find|user");

        //then
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeastOnce()).write(captor.capture());
        assertEquals("[---------------------------, " +
                     "| id | name | password | ," +
                     " ---------------------------, " +
                     "| 12 | Stiven | ***** | , " +
                     "| 13 | Eva | +++++ | " +
                    ", ---------------------------]",
                        captor.getAllValues().toString());
    }

}
