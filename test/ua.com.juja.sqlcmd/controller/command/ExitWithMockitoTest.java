package ua.com.juja.sqlcmd.controller.command;


import org.junit.Test;
import org.mockito.Mockito;
import ua.com.juja.sqlCmd.controller.command.Command;
import ua.com.juja.sqlCmd.controller.command.Exit;
import ua.com.juja.sqlCmd.controller.command.ExitException;
import ua.com.juja.sqlCmd.view.View;

import static junit.framework.Assert.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;

/**
 * Created by Julia on 24.04.2016.
 */
public class ExitWithMockitoTest {

    private View view = Mockito.mock(View.class);

    @Test
    public void testCanProcessExitString() {
        //given
        Command command = new Exit(view);

        //when
        boolean canProcess = command.canProcess("exit");

        //then
        assertTrue(canProcess);
    }

    @Test
    public void testCantProcessEqweString() {
        //given
        Command command = new Exit(view);

        //when
        boolean canProcess = command.canProcess("eqwe");

        //then
        assertFalse(canProcess);
    }

    @Test
    public void testProcessExitCommand_throwsExitException() {
        //given
        Command command = new Exit(view);

        //when
        try {
            command.process("exit");
            fail("Expected ExitException");
        } catch (ExitException e){

        }

        //then
        Mockito.verify(view).write("До скорой встречи!");
    }
}
