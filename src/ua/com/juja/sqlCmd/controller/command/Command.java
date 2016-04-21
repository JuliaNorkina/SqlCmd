package ua.com.juja.sqlCmd.controller.command;

/**
 * Created by Юлия on 21.04.2016.
 */
public interface Command {

    boolean canProcess(String command);
    void process(String command);
}
