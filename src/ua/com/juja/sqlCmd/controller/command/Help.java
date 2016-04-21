package ua.com.juja.sqlCmd.controller.command;

import ua.com.juja.sqlCmd.view.View;

/**
 * Created by Юлия on 21.04.2016.
 */
public class Help implements Command{
    private View view;

    public Help(View view) {
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.equals("help");
    }

    @Override
    public void process(String command) {
            view.write("Существующие команды: ");

            view.write("\tconnect|databaseName|userName|password");
            view.write("\t\tдля подключения к базе данныхб с которой будем работать.");

            view.write("\tlist");
            view.write("\t\tдля получения списка все таблиц базы, к которой подключились.");

            view.write("\tfind|tableName");
            view.write("\t\tдля получения содержимого таблицы 'tableName'.");

            view.write("\thelp");
            view.write("\t\tдля вывода этого списка на экран.");

            view.write("\texit");
            view.write("\t\tдля выхода из программы.");
    }
}
