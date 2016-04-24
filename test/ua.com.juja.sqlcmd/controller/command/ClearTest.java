package ua.com.juja.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.sqlCmd.controller.command.Clear;
import ua.com.juja.sqlCmd.controller.command.Command;
import ua.com.juja.sqlCmd.model.DatabaseManager;
import ua.com.juja.sqlCmd.view.View;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by Julia on 24.04.2016.
 */
public class ClearTest {

    private DatabaseManager manager;
    private View view;
    private Command command;

    @Before
    public void setup(){
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new Clear(manager, view);
    }


    @Test
    public void testClearTable(){
        //given

        //when
        command.process("clear|user");

        //then
        verify(manager).clear("user");
        verify(view).write("Таблица user была успешно очищена!");
    }


    @Test
    public void testCanProcessClearWithParametersExitString() {
        //when
        boolean canProcess = command.canProcess("clear|user");

        //then
        assertTrue(canProcess);
    }

    @Test
    public void testCantProcessClearWithoutParametersString() {
        //when
        boolean canProcess = command.canProcess("clear");

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
}
