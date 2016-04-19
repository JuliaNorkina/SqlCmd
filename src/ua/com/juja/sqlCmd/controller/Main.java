package ua.com.juja.sqlCmd.controller;

import ua.com.juja.sqlCmd.model.DatabaseManager;
import ua.com.juja.sqlCmd.model.JDBSDatabaseManager;
import ua.com.juja.sqlCmd.view.Console;
import ua.com.juja.sqlCmd.view.View;

/**
 * Created by Юлия on 19.04.2016.
 */
public class Main {
    public static void main(String[] args) {
        View view = new Console();
        DatabaseManager manager = new JDBSDatabaseManager();

        MainController controller = new MainController(view, manager);
        controller.run();
    }
}
