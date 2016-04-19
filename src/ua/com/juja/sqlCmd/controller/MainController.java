package ua.com.juja.sqlCmd.controller;

import ua.com.juja.sqlCmd.model.DatabaseManager;
import ua.com.juja.sqlCmd.view.View;

/**
 * Created by Юлия on 19.04.2016.
 */
public class MainController {

    private View view;
    private DatabaseManager manager;

    public MainController(View view, DatabaseManager manager){
        this.view = view;
        this.manager = manager;
    }

    public void run(){
        connectToDb();
        // smth
    }

    private void connectToDb() {
        view.write("Привет пользователь!");
        view.write("Введи, пожалуйста, имя базы данных, имя пользователя и пароль в формате: database|userName|password");

        while (true) {
            String string = view.read();

            String[] data = string.split("[|]");
            String databaseName = data[0];
            String userName = data[1];
            String password = data[2];
            try {
                manager.connect(databaseName, userName, password);
                break;
            } catch (Exception e) {
                String message = e.getMessage();
                if(e.getCause() != null){
                    message += " " + e.getCause().getMessage();
                }
                view.write("Неудача! По причине: " + message);
                view.write("Повторите попытку.");
            }
        }
        view.write("Успех!");
    }
}
