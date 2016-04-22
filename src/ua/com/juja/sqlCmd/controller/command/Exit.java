package ua.com.juja.sqlCmd.controller.command;

import ua.com.juja.sqlCmd.view.View;

/**
 * Created by Юлия on 21.04.2016.
 */
public class Exit implements Command {

    private View view;

    public Exit(View view) {
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.equals("exit");
    }

    @Override
    public void process(String command) {
        view.write("До скорой встречи!");
        throw new ExitException();
    }
}
