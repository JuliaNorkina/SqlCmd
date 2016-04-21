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
                new List(manager, view),
                new Find(manager, view),
                new Unsupported(view)};
    }

    public void run(){
        view.write("Привет пользователь!");
        view.write("Введи, пожалуйста, имя базы данных, имя пользователя и " +
                "пароль в формате: connect|database|userName|password");
        while (true) {
            String input = view.read();
            if(input == null){
               new Exit(view).process(input);
            }
            for (Command command : commands) {
                if (command.canProcess(input)) {
                    command.process(input);
                    break;
                }
            }
            view.write("Введи команду или help для помощи: ");
        }
    }


}
