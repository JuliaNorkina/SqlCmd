package ua.com.juja.sqlCmd.controller.command;

import ua.com.juja.sqlCmd.model.DatabaseManager;
import ua.com.juja.sqlCmd.view.View;

import java.util.Set;

/**
 * Created by Юлия on 21.04.2016.
 */
public class Tables implements Command{

    private DatabaseManager manager;
    private View view;

    public Tables(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.equals("list");
    }//TODO replace

    @Override
    public void process(String command) {
        Set<String> tableNames = manager.getTableNames();
        String message = tableNames.toString();
        view.write(message);
    }
}
