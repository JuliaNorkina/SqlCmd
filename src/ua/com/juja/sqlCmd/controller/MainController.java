package ua.com.juja.sqlCmd.controller;

import ua.com.juja.sqlCmd.controller.command.Command;
import ua.com.juja.sqlCmd.controller.command.Exit;
import ua.com.juja.sqlCmd.controller.command.Help;
import ua.com.juja.sqlCmd.controller.command.List;
import ua.com.juja.sqlCmd.model.DataSet;
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
        this.commands = new Command[] {new Exit(view),
                new Help(view), new List(manager,view)};
    }

    public void run(){
        connectToDb();
        while (true) {
            view.write("Введи команду или help для помощи: ");
            String command = view.read();

            if (commands[2].canProcess(command)) {
                commands[2].proces(command);
            } else if (commands[1].canProcess(command)) {
                commands[1].proces(command);
            } else if (commands[0].canProcess(command)) {
                commands[0].proces(command);
            } else if (command.startsWith("find|")) {
                dofind(command);
            } else{
                view.write("Несуществующая команда: "+ command);
            }
        }
    }

    private void dofind(String command) {
        String[] data = command.split("[|]");
        String tableName = data[1];

        DataSet[] tableData = manager.getTableData(tableName);

        String[] tableColumns = manager.getTableColumns(tableName);
        printHeader(tableColumns);
        printTable(tableData);
    }

    private void printTable(DataSet[] tableData) {

        for(DataSet row: tableData){
            printRow(row);
        }
    }

    private void printRow(DataSet row) {
        Object[] values = row.getValues();
        String result = "| ";
        for(Object value: values){
            result += value + " | ";
        }
        view.write(result);
    }

    private void printHeader(String[] tableColumns) {

        String result = "| ";
        for(String name: tableColumns){
            result += name + " | ";
        }
        view.write("---------------------------");
        view.write(result);
        view.write("---------------------------");
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
