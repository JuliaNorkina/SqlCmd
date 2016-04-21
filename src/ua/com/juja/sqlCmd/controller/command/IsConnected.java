package ua.com.juja.sqlCmd.controller.command;

import ua.com.juja.sqlCmd.model.DatabaseManager;
import ua.com.juja.sqlCmd.view.View;

/**
 * Created by Юлия on 21.04.2016.
 */
public class IsConnected implements Command {
    private final DatabaseManager manager;
    private final View view;

    public IsConnected(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return !manager.IsConnected();
    }

    @Override
    public void process(String command) {
        view.write(String.format("Вы не можете пользоватся командой '%s' пока " +
                "не подключитесь с помощью команды " +
                "connect|databaseName|userName|password", command));
    }
}
