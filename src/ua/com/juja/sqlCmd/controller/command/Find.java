package ua.com.juja.sqlCmd.controller.command;

import ua.com.juja.sqlCmd.model.DataSet;
import ua.com.juja.sqlCmd.model.DatabaseManager;
import ua.com.juja.sqlCmd.view.View;

/**
 * Created by Юлия on 21.04.2016.
 */
public class Find implements Command {

    private DatabaseManager manager;
    private View view;

    public Find(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("find|");
    }

    @Override
    public void process(String command) {
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
}
