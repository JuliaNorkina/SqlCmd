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

    public MainController(View view, DatabaseManager manager){
        this.view = view;
        this.commands = new Command[]{
                new Connect(manager, view),
                new Help(view),
                new Exit(view),
                new IsConnected(manager, view),
                new Tables(manager, view),
                new Clear(manager, view),
                new Create(manager, view),
                new Find(manager, view),
                new Unsupported(view)};
    }

    public void run(){
        try {
            doWork();
        } catch (ExitException e){
            //do nothing
        }
    }

    private void doWork() {
        view.write("Привет пользователь!");
        view.write("Введи, пожалуйста, имя базы данных, имя пользователя и " +
                "пароль в формате: connect|database|userName|password");
        while (true) {
            String input = view.read();

            for (Command command : commands) {
                try {
                    if (command.canProcess(input)) {
                            command.process(input);
                        break;
                    }
                } catch (Exception e){
                    if(e instanceof ExitException){
                        throw e;
                        }
                    printError(e);
                    break;
                }
            }
            view.write("Введи команду или help для помощи: ");
        }
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
