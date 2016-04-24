package ua.com.juja.sqlcmd.controller.command;


import org.junit.Test;
import ua.com.juja.sqlCmd.controller.command.Command;
import ua.com.juja.sqlCmd.controller.command.Exit;
import ua.com.juja.sqlCmd.controller.command.ExitException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;

/**
 * Created by Julia on 24.04.2016.
 */
public class ExitTest {

    private FakeView view = new FakeView();

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
        assertEquals("До скорой встречи!\n", view.getContent());
        //throws ExitException
    }
}
