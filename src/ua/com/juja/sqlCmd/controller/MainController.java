package ua.com.juja.sqlCmd.controller;

import ua.com.juja.sqlCmd.controller.command.*;
import ua.com.juja.sqlCmd.model.DatabaseManager;
import ua.com.juja.sqlCmd.view.View;

/**
 * Created by Юлия on 19.04.2016.
 */
public class MainController {

    private Command[] commands;
    private View view;
    private DatabaseManager manager;

    public MainController(View view, DatabaseManager manager){
        this.view = view;
        this.manager = manager;
        this.commands = new Command[] {new Exit(view), new Help(view),
                new List(manager,view), new Find(manager,view),
                new Unsupported(view)};
    }

    public void run(){
        connectToDb();
        while (true) {
            view.write("Введи команду или help для помощи: ");
            String input = view.read();
            for (Command command : commands) {
                if (command.canProcess(input)) {
                    command.proces(input);
                    break;
                }
            }
        }
    }

    private void connectToDb() {
        view.write("Привет пользователь!");
        view.write("Введи, пожалуйста, имя базы данных, имя пользователя и пароль в формате: database|userName|password");

        while (true) {
            try {
                String string = view.read();
                String[] data = string.split("[|]");
                if (data.length != 3) {
                    throw new IllegalArgumentException("Неверно количество параметров разделенных знаком '|'," +
                            " ожидается 3, но есть: " + data.length);
                }
                String databaseName = data[0];
                String userName = data[1];
                String password = data[2];

                    manager.connect(databaseName, userName, password);
                    break;
            } catch (Exception e) {
                printError(e);
            }
        }
        view.write("Успех!");
    }

    private void printError(Exception e) {
        String message = e.getMessage();
        if(e.getCause() != null){
            message += " " + e.getCause().getMessage();
        }
        view.write("Неудача! По причине: " + message);
        view.write("Повторите попытку.");
    }
}
