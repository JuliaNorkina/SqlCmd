package ua.com.juja.sqlCmd.controller.command;

import ua.com.juja.sqlCmd.view.View;

/**
 * Created by Юлия on 21.04.2016.
 */
public class Unsupported implements Command {
    private View view;

    public Unsupported(View view) {
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return true;
    }

    @Override
    public void proces(String command) {
        view.write("Несуществующая команда: "+ command);
    }
}
